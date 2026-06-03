package br.com.fiap.firesearchlogic.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_sen_foco_calor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FocoCalor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_foco")
    @SequenceGenerator(name = "sq_foco", sequenceName = "SQ_T_SEN_FOCO_CALOR", allocationSize = 1)
    @Column(name = "id_foco")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_sen_regiao_id_regiao", nullable = false)
    private Regiao regiao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_sen_satelite_id_satelite", nullable = false)
    private Satelite satelite;

    @Column(name = "fc_latitude", nullable = false)
    private Double fcLatitude;

    @Column(name = "fc_longitude", nullable = false)
    private Double fcLongitude;

    @Column(name = "brightness", nullable = false)
    private Double brightness;

    @Column(name = "frp", nullable = false)
    private Double frp;

    @Column(name = "confidence", nullable = false, length = 10)
    private String confidence;

    @Column(name = "dt_aquisicao", nullable = false)
    private LocalDate dtAquisicao;

    @Column(name = "hr_aquisicao", nullable = false, length = 4)
    private String hrAquisicao;

    @Column(name = "nm_satelite_origem", nullable = false, length = 30)
    private String nmSateliteOrigem;

    @Column(name = "data_importacao", nullable = false)
    private LocalDate dataImportacao;
}