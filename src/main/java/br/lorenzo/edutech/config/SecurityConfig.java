package br.lorenzo.edutech.config;

import br.lorenzo.edutech.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/webjars/**", "/css/**", "/login", "/registro",
                                "/confirmar", "/recuperar-senha/**", "/resend-confirmation").permitAll()
                        .requestMatchers("/web/alunos/**", "/web/cursos/**", "/web/categorias/**").hasRole("ADMIN")
                        .requestMatchers("/web/matriculas/nova").hasAnyRole("ADMIN", "ALUNO")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureHandler((request, response, exception) -> {
                            String email = request.getParameter("username");
                            if (exception instanceof DisabledException) {
                                request.getSession().setAttribute("emailNaoConfirmado", email);
                                response.sendRedirect("/login?error=nao_confirmado&email=" + email);
                            } else if (exception instanceof BadCredentialsException) {
                                try {
                                    UserDetails user = userDetailsService.loadUserByUsername(email);
                                    if (!user.isEnabled()) {
                                        response.sendRedirect("/login?error=nao_confirmado&email=" + email);
                                        return;
                                    }
                                } catch (UsernameNotFoundException ignored) {}
                                response.sendRedirect("/login?error=credenciais_invalidas");
                            } else {
                                response.sendRedirect("/login?error=erro_desconhecido");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/login?expired")
                )
                .rememberMe(remember -> remember
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400) // 1 dia
                        .userDetailsService(userDetailsService)
                )
                .userDetailsService(userDetailsService)
                .csrf(CsrfConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

