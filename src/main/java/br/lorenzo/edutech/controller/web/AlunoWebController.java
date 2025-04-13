package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.dto.AlunoDTO;
import br.lorenzo.edutech.exception.EmailDuplicadoException;
import br.lorenzo.edutech.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/alunos")
public class AlunoWebController {

    private final AlunoService alunoService;

    public AlunoWebController (AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public String listAlunos(Model model) {
        model.addAttribute("alunos", alunoService.findAll());
        return "alunos/listar-alunos";
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

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        AlunoDTO alunoDTO = alunoService.findById(id);
        model.addAttribute("aluno", alunoDTO);
        return "alunos/editar-aluno-form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarAluno(@PathVariable Long id,
                                 @Valid @ModelAttribute("aluno") AlunoDTO alunoDTO,
                                 BindingResult result,
                                 RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "alunos/editar-aluno-form";
        }

        try {
            alunoService.update(id, alunoDTO);
            attributes.addFlashAttribute("success", "Aluno atualizado com sucesso!");
            return "redirect:/web/alunos";
        } catch (EmailDuplicadoException e) {
            result.rejectValue("email", "error.aluno", e.getMessage());
            return "alunos/editar-aluno-form";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirAluno(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            alunoService.delete(id);
            attributes.addFlashAttribute("success", "Aluno excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/alunos";
    }


}
