package br.com.fiap.firesearchlogic.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_sen_historico_risco")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricoRisco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_historico")
    @SequenceGenerator(name = "sq_historico", sequenceName = "seq_historico_risco", allocationSize = 1)
    @Column(name = "id_historico")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_sen_regiao_id_regiao", nullable = false)
    private Regiao regiao;

    @Column(name = "score", nullable = false)
    private Double score;

    @Column(name = "ds_nivel", nullable = false, length = 100)
    private String dsNivel;

    @Column(name = "data_calculo", nullable = false)
    private LocalDate dataCalculo;
}