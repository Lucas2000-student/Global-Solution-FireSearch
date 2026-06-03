package br.com.fiap.firesearchlogic.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_sen_alerta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_alerta")
    @SequenceGenerator(name = "sq_alerta", sequenceName = "SQ_T_SEN_ALERTA", allocationSize = 1)
    @Column(name = "id_alerta")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_sen_historico_risco_id_historico", nullable = false)
    private HistoricoRisco historicoRisco;

    @Column(name = "ds_nivel", nullable = false, length = 10)
    private String dsNivel;

    @Column(name = "ds_mensagem", nullable = false, length = 500)
    private String dsMensagem;

    @Column(name = "data_emissao", nullable = false)
    private LocalDateTime dataEmissao;

    @Column(name = "fl_notificado", nullable = false, length = 1)
    private String flNotificado;
}