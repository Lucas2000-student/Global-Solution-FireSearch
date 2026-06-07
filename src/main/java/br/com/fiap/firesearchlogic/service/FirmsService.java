package br.com.fiap.firesearchlogic.service;

import br.com.fiap.firesearchlogic.entity.FocoCalor;
import br.com.fiap.firesearchlogic.entity.Regiao;
import br.com.fiap.firesearchlogic.entity.Satelite;
import br.com.fiap.firesearchlogic.messaging.FirmsProducer;
import br.com.fiap.firesearchlogic.repository.FocoCalorRepository;
import br.com.fiap.firesearchlogic.repository.RegiaoRepository;
import br.com.fiap.firesearchlogic.repository.SateliteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirmsService {

    private final FocoCalorRepository focoCalorRepository;
    private final RegiaoRepository regiaoRepository;
    private final SateliteRepository sateliteRepository;
    private final FirmsProducer firmsProducer;
    private final RestTemplate restTemplate;

    @Value("${firms.api.key}")
    private String apiKey;

    @Value("${firms.api.url}")
    private String apiUrl;

    @Value("${firms.source}")
    private String fonte;

    @Value("${firms.area}")
    private String area;

    @Value("${firms.day-range}")
    private String dayRange;

    @Scheduled(cron = "0 0 6 * * *") // todo dia às 6h
    public void importarFocosNasa() {
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("[FIRMS] MAP_KEY não configurado, importação ignorada");
            return;
        }

        try {
            String url = String.format("%s%s/%s/%s/%s", apiUrl, apiKey, fonte, area, dayRange);
            log.info("[FIRMS] Consultando NASA FIRMS: {}", url);

            String csv = restTemplate.getForObject(url, String.class);
            if (csv == null || csv.isBlank()) {
                log.warn("[FIRMS] Resposta vazia da NASA");
                return;
            }

            List<Regiao> regioes = regiaoRepository.findAll();
            Satelite satelite = sateliteRepository.findByDsFonte(fonte)
                    .orElse(sateliteRepository.findAll()
                            .stream().findFirst().orElse(null));

            String[] linhas = csv.split("\n");
            int importados = 0;

            for (int i = 1; i < linhas.length; i++) {
                String[] campos = linhas[i].split(",");
                if (campos.length < 10) continue;

                try {
                    Double lat = Double.parseDouble(campos[0].trim());
                    Double lon = Double.parseDouble(campos[1].trim());
                    Double brightness = Double.parseDouble(campos[2].trim());
                    Double frp = Double.parseDouble(campos[12].trim());
                    String confidence = campos[8].trim();
                    String acqDate = campos[5].trim();
                    String acqTime = campos[6].trim();

                    Regiao regiao = regioes.stream()
                            .filter(r -> distanciaKm(lat, lon,
                                    r.getReLatitude(), r.getReLongitude()) <= 100)
                            .findFirst().orElse(null);

                    if (regiao == null) continue;

                    FocoCalor foco = FocoCalor.builder()
                            .regiao(regiao)
                            .satelite(satelite)
                            .fcLatitude(lat)
                            .fcLongitude(lon)
                            .brightness(brightness)
                            .frp(frp)
                            .confidence(confidence)
                            .dtAquisicao(LocalDate.parse(acqDate))
                            .hrAquisicao(acqTime)
                            .nmSateliteOrigem(fonte)
                            .dataImportacao(LocalDate.now())
                            .build();

                    foco = focoCalorRepository.save(foco);
                    firmsProducer.publicarFoco(foco);
                    importados++;

                } catch (Exception e) {
                    log.warn("[FIRMS] Erro ao parsear linha {}: {}", i, e.getMessage());
                }
            }

            log.info("[FIRMS] Importação concluída: {} focos salvos", importados);

        } catch (Exception e) {
            log.error("[FIRMS] Erro na importação NASA: {}", e.getMessage());
        }
    }

    private double distanciaKm(double lat1, double lon1, double lat2, double lon2) {
        double r = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return r * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}