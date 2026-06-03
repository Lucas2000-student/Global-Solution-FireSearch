package br.com.fiap.firesearchlogic.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_sen_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_usuario")
    @SequenceGenerator(name = "sq_usuario", sequenceName = "SQ_T_SEN_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 150)
    private String senhaHash;

    @Column(name = "fcm_token", length = 500)
    private String fcmToken;

    @Column(name = "latitude", precision = 10)
    private Double latitude;

    @Column(name = "longitude", precision = 10)
    private Double longitude;

    @Column(name = "raio_km")
    private Integer raioKm;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "ativo", nullable = false, length = 1)
    private String ativo;
}