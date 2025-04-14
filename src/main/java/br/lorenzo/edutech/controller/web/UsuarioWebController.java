package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.dto.UsuarioDTO;
import br.lorenzo.edutech.exception.EmailDuplicadoException;
import br.lorenzo.edutech.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registro")
@RequiredArgsConstructor
public class UsuarioWebController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new UsuarioDTO("", "", ""));
        return "registro";
    }

    @PostMapping
    public String registrar(@Valid @ModelAttribute("usuario") UsuarioDTO usuarioDTO,
                            BindingResult result,
                            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "registro";
        }

        try {
            usuarioService.registrar(usuarioDTO, "ALUNO");
            attributes.addFlashAttribute("success", "Registro realizado com sucesso! Faça login.");
            return "redirect:/login";
        } catch (EmailDuplicadoException e) {
            result.rejectValue("email", "error.usuario", e.getMessage());
            return "registro";
        }
    }
}

