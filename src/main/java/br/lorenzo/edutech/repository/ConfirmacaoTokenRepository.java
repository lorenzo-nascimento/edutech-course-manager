package br.lorenzo.edutech.repository;

import br.lorenzo.edutech.model.ConfirmacaoToken;
import br.lorenzo.edutech.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConfirmacaoTokenRepository extends JpaRepository<ConfirmacaoToken, Long> {
    Optional<ConfirmacaoToken> findByToken(String token);

    @Modifying
    @Query("DELETE FROM ConfirmacaoToken t WHERE t.usuario = :usuario")
    void deleteAllByUsuario(@Param("usuario") Usuario usuario);
}
