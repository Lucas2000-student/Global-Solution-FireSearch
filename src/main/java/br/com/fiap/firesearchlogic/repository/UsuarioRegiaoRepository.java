package br.com.fiap.firesearchlogic.repository;

import br.com.fiap.firesearchlogic.entity.UsuarioRegiao;
import br.com.fiap.firesearchlogic.entity.UsuarioRegiaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRegiaoRepository extends JpaRepository<UsuarioRegiao, UsuarioRegiaoId> {
    List<UsuarioRegiao> findByUsuarioIdAndAtivo(Long usuarioId, String ativo);
}