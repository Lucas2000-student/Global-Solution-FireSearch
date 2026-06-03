package br.com.fiap.firesearchlogic.repository;

import br.com.fiap.firesearchlogic.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findAllByOrderByDataEmissaoDesc();
    List<Alerta> findByDsNivelOrderByDataEmissaoDesc(String dsNivel);
    List<Alerta> findByFlNotificado(String flNotificado);
}