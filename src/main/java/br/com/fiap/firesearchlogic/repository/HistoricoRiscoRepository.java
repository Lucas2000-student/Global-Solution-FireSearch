package br.com.fiap.firesearchlogic.repository;

import br.com.fiap.firesearchlogic.entity.HistoricoRisco;
import br.com.fiap.firesearchlogic.entity.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoricoRiscoRepository extends JpaRepository<HistoricoRisco, Long> {
    List<HistoricoRisco> findByRegiaoOrderByDataCalculoDesc(Regiao regiao);
    List<HistoricoRisco> findByScoreGreaterThanOrderByDataCalculoDesc(Double score);
}