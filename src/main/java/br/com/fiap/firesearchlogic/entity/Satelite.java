package br.com.fiap.firesearchlogic.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_sen_satelite")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Satelite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_satelite")
    @SequenceGenerator(name = "sq_satelite", sequenceName = "seq_satelite", allocationSize = 1)
    @Column(name = "id_satelite")
    private Long id;

    @Column(name = "nm_satelite", nullable = false, length = 50)
    private String nmSatelite;

    @Column(name = "ds_fonte", nullable = false, length = 50)
    private String dsFonte;

    @Column(name = "ds_descricao", length = 200)
    private String dsDescricao;
}