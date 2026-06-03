package br.com.fiap.firesearchlogic.repository;

import br.com.fiap.firesearchlogic.entity.Satelite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SateliteRepository extends JpaRepository<Satelite, Long> {
    Optional<Satelite> findByDsFonte(String dsFonte);
}