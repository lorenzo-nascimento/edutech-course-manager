package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.exception.TokenInvalidoException;
import br.lorenzo.edutech.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ConfirmacaoController {

    private final UsuarioService usuarioService;

    @GetMapping("/confirmar")
    public String confirmarEmail(@RequestParam String token, Model model) {
        try {
            usuarioService.confirmarEmail(token);
            model.addAttribute("sucesso", "Email confirmado com sucesso! Você já pode fazer login.");
        } catch (TokenInvalidoException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "confirmacao";
    }

    @GetMapping("/resend-confirmation")
    public String reenviarConfirmacao(@RequestParam String email, RedirectAttributes attributes) {
        try {
            usuarioService.reenviarEmailConfirmacao(email);
            attributes.addFlashAttribute("sucesso", "Email de confirmação reenviado para " + email);
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/login?email=" + email;
    }

}
