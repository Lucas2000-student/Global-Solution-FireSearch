package br.com.fiap.firesearchlogic.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_sen_regiao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Regiao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_regiao")
    @SequenceGenerator(name = "sq_regiao", sequenceName = "SQ_T_SEN_REGIAO", allocationSize = 1)
    @Column(name = "id_regiao")
    private Long id;

    @Column(name = "nm_regiao", nullable = false, length = 100)
    private String nmRegiao;

    @Column(name = "nm_estado", nullable = false, length = 50)
    private String nmEstado;

    @Column(name = "nm_pais", nullable = false, length = 50)
    private String nmPais;

    @Column(name = "re_latitude", nullable = false)
    private Double reLatitude;

    @Column(name = "re_longitude", nullable = false)
    private Double reLongitude;
}