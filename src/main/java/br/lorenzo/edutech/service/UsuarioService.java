package br.lorenzo.edutech.service;

import br.lorenzo.edutech.dto.UsuarioDTO;
import br.lorenzo.edutech.exception.EmailDuplicadoException;
import br.lorenzo.edutech.model.Usuario;
import br.lorenzo.edutech.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void registrar(UsuarioDTO usuarioDTO, String perfil) {
        if (usuarioRepository.existsByEmail(usuarioDTO.email())) {
            throw new EmailDuplicadoException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.email());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.senha()));
        usuario.setNome(usuarioDTO.nome());
        usuario.getPerfis().add(perfil);

        usuarioRepository.save(usuario);
    }
}
