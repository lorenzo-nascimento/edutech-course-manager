package br.lorenzo.edutech.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class ConfirmacaoToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiraEm;

    @OneToOne
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

    public ConfirmacaoToken() {
        this.token = UUID.randomUUID().toString();
        this.expiraEm = LocalDateTime.now().plusHours(24);
    }

    public ConfirmacaoToken(Usuario usuario) {
        this();
        this.usuario = usuario;
    }

    public boolean isExpirado() {
        return LocalDateTime.now().isAfter(expiraEm);
    }
}
