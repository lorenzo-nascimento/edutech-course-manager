package br.lorenzo.edutech.config;

import br.lorenzo.edutech.model.Usuario;
import br.lorenzo.edutech.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setEmail("admin@edutech.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setNome("Administrador");
            admin.getPerfis().add("ADMIN");

            usuarioRepository.save(admin);
        }
    }
}
