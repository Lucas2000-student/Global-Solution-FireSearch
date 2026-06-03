package br.com.fiap.firesearchlogic.repository;

import br.com.fiap.firesearchlogic.entity.FocoCalor;
import br.com.fiap.firesearchlogic.entity.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface FocoCalorRepository extends JpaRepository<FocoCalor, Long> {

    List<FocoCalor> findByRegiaoAndDtAquisicaoAfter(Regiao regiao, LocalDate data);

    @Query("SELECT f FROM FocoCalor f WHERE f.regiao = :regiao AND f.dtAquisicao >= :dataMinima ORDER BY f.dtAquisicao DESC")
    List<FocoCalor> findFocosRecentesPorRegiao(@Param("regiao") Regiao regiao,
                                               @Param("dataMinima") LocalDate dataMinima);
}