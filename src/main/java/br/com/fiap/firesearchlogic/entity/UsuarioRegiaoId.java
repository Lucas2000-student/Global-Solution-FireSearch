package br.com.fiap.firesearchlogic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegiaoId implements Serializable {

    @Column(name = "t_sen_usuario_id_usuario")
    private Long usuarioId;

    @Column(name = "t_sen_regiao_id_regiao")
    private Long regiaoId;
}