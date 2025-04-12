package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.dto.CursoDTO;
import br.lorenzo.edutech.service.CategoriaService;
import br.lorenzo.edutech.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/web/cursos")
public class CursoWebController {

    private final CursoService cursoService;
    private final CategoriaService categoriaService;

    public CursoWebController(CursoService cursoService, CategoriaService categoriaService) {
        this.cursoService = cursoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/novo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("curso", new CursoDTO(null, "", "", new ArrayList<>()));
        model.addAttribute("todasCategorias", categoriaService.findAll());
        return "cursos/novo-curso-form";
    }

    @PostMapping("/novo")
    public String cadastrarCurso(@Valid CursoDTO cursoDTO,
                                 BindingResult result,
                                 RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "cursos/novo-curso-form";
        }

        cursoService.createCourse(cursoDTO);
        attributes.addFlashAttribute("success", "Curso cadastrado com sucesso!");
        return "redirect:/cursos";
    }

    public String listCursos(Model model) {
        model.addAttribute("cursos", cursoService.findAll());
        return "cursos/listar-cursos";
    }

}
