package br.com.fiap.firesearchlogic.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_sen_usuario_regiao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRegiao {

    @EmbeddedId
    private UsuarioRegiaoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "t_sen_usuario_id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("regiaoId")
    @JoinColumn(name = "t_sen_regiao_id_regiao")
    private Regiao regiao;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDate dataInscricao;

    @Column(name = "us_re_ativo", nullable = false, length = 1)
    private String ativo;
}