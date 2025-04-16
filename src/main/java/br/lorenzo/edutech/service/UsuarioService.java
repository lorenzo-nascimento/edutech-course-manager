package br.lorenzo.edutech.service;

import br.lorenzo.edutech.dto.UsuarioDTO;
import br.lorenzo.edutech.exception.EmailDuplicadoException;
import br.lorenzo.edutech.exception.TokenInvalidoException;
import br.lorenzo.edutech.exception.UsuarioNaoEncontradoException;
import br.lorenzo.edutech.model.ConfirmacaoToken;
import br.lorenzo.edutech.model.Usuario;
import br.lorenzo.edutech.repository.ConfirmacaoTokenRepository;
import br.lorenzo.edutech.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final ConfirmacaoTokenRepository tokenRepository;

    public void registrar(UsuarioDTO usuarioDTO, String perfil) {
        if (usuarioRepository.existsByEmail(usuarioDTO.email())) {
            throw new EmailDuplicadoException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.email());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.senha()));
        usuario.setNome(usuarioDTO.nome());
        usuario.setEnabled(false);
        usuario.getPerfis().add(perfil);

        usuarioRepository.save(usuario);

        ConfirmacaoToken token = new ConfirmacaoToken(usuario);
        tokenRepository.save(token);

        enviarEmailConfirmacao(usuario.getEmail(), token.getToken());
    }

    private void enviarEmailConfirmacao(String email, String token) {
        String assunto = "Confirme seu cadastro no EduTech";
        String urlConfirmacao = "http://localhost:8080/confirmar?token=" + token;
        String mensagem = String.format(
                "Olá,\n\nPor favor, confirme seu email clicando no link abaixo:\n\n%s\n\nO link expira em 24 horas.",
                urlConfirmacao
        );

        SimpleMailMessage emailMsg = new SimpleMailMessage();
        emailMsg.setTo(email);
        emailMsg.setSubject(assunto);
        emailMsg.setText(mensagem);

        mailSender.send(emailMsg);
    }

    public void reenviarEmailConfirmacao(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Email não cadastrado"));

        if (usuario.isEnabled()) {
            throw new IllegalStateException("Email já confirmado");
        }

        tokenRepository.deleteAllByUsuario(usuario);

        ConfirmacaoToken token = new ConfirmacaoToken(usuario);
        tokenRepository.save(token);

        enviarEmailConfirmacao(usuario.getEmail(), token.getToken());
    }

    @Transactional
    public void confirmarEmail(String token) {
        ConfirmacaoToken confirmacaoToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenInvalidoException("Token inválido"));

        if (confirmacaoToken.isExpirado()) {
            throw new TokenInvalidoException("Token expirado");
        }

        Usuario usuario = confirmacaoToken.getUsuario();
        usuario.setEnabled(true);
        usuarioRepository.save(usuario);

        tokenRepository.delete(confirmacaoToken);
    }


}
