package br.com.fiap.firesearchlogic.repository;

import br.com.fiap.firesearchlogic.entity.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RegiaoRepository extends JpaRepository<Regiao, Long> {

    // Busca regiões dentro de um raio usando fórmula Haversine
    @Query(value = """
        SELECT * FROM t_sen_regiao r
        WHERE (6371 * ACOS(
            COS((:lat * 3.14159265358979 / 180)) *
            COS((r.re_latitude * 3.14159265358979 / 180)) *
            COS((r.re_longitude * 3.14159265358979 / 180) - (:lon * 3.14159265358979 / 180)) +
            SIN((:lat * 3.14159265358979 / 180)) *
            SIN((r.re_latitude * 3.14159265358979 / 180))
        )) <= :raioKm
        """, nativeQuery = true)
    List<Regiao> findRegioesDentroDoRaio(@Param("lat") Double lat,
                                         @Param("lon") Double lon,
                                         @Param("raioKm") Integer raioKm);
}