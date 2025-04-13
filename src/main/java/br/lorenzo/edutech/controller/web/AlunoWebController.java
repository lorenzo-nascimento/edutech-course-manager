package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.dto.AlunoDTO;
import br.lorenzo.edutech.exception.EmailDuplicadoException;
import br.lorenzo.edutech.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/alunos")
public class AlunoWebController {

    private final AlunoService alunoService;

    public AlunoWebController (AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping("/novo")
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("aluno")) {
            model.addAttribute("aluno", new AlunoDTO(null, "", ""));
        }
        return "alunos/novo-aluno-form";
    }

    @PostMapping("/novo")
    public String cadastrarAluno(@Valid @ModelAttribute("aluno") AlunoDTO alunoDTO,
                                 BindingResult result,
                                 RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "alunos/novo-aluno-form";
        }

        try {
            alunoService.register(alunoDTO);
            attributes.addFlashAttribute("success", "Aluno cadastrado com sucesso!");
            return "redirect:/web/alunos";
        } catch (EmailDuplicadoException e) {
            result.rejectValue("email", "error.aluno", e.getMessage());
            return "alunos/novo-aluno-form";
        }
    }

    @GetMapping
    public String listAlunos(Model model) {
        model.addAttribute("alunos", alunoService.findAll());
        return "alunos/listar-alunos";
    }


}
