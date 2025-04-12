package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.dto.MatriculaDTO;
import br.lorenzo.edutech.service.AlunoService;
import br.lorenzo.edutech.service.CursoService;
import br.lorenzo.edutech.service.MatriculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/matriculas")
public class MatriculaWebController {

    private final MatriculaService matriculaService;
    private final AlunoService alunoService;
    private final CursoService cursoService;

    public MatriculaWebController(MatriculaService matriculaService,
                                  AlunoService alunoService,
                                  CursoService cursoService) {
        this.matriculaService = matriculaService;
        this.alunoService = alunoService;
        this.cursoService = cursoService;
    }

    @GetMapping("/nova")
    public String mostrarFormulario(Model model) {
        model.addAttribute("todosAlunos", alunoService.findAll());
        model.addAttribute("todosCursos", cursoService.findAll());
        return "matriculas/nova-matricula-form";
    }

    @PostMapping("/nova")
    public String cadastrarMatricula(@RequestParam Long alunoId,
                                     @RequestParam Long cursoId,
                                     RedirectAttributes attributes) {
        try {
            matriculaService.enrollAluno(new MatriculaDTO(alunoId, cursoId, null));
            attributes.addFlashAttribute("success", "Matrícula realizada com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/matriculas";
    }
}
