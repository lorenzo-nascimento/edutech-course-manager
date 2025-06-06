package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.dto.CursoDTO;
import br.lorenzo.edutech.service.CategoriaService;
import br.lorenzo.edutech.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        if (!model.containsAttribute("curso")) {
            model.addAttribute("curso", new CursoDTO(null, "", "", new ArrayList<>()));
        }
        model.addAttribute("todasCategorias", categoriaService.findAll());
        return "cursos/novo-curso-form";
    }

    @PostMapping("/novo")
    public String cadastrarCurso(@Valid @ModelAttribute("curso") CursoDTO cursoDTO,
                                 BindingResult result,
                                 RedirectAttributes attributes) {

        if (cursoDTO.categoriasIds() == null || cursoDTO.categoriasIds().isEmpty()) {
            result.rejectValue("categoriasIds", "error.curso", "Selecione pelo menos uma categoria");
        }

        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.curso", result);
            attributes.addFlashAttribute("curso", cursoDTO);
            attributes.addFlashAttribute("todasCategorias", categoriaService.findAll());
            return "redirect:/web/cursos/novo";
        }

        try {
            cursoService.createCourse(cursoDTO);
            attributes.addFlashAttribute("success", "Curso cadastrado com sucesso!");
            return "redirect:/web/cursos";
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("curso", cursoDTO);
            attributes.addFlashAttribute("todasCategorias", categoriaService.findAll());
            return "redirect:/web/cursos/novo";
        }
    }

    @GetMapping
    public String listCursos(Model model) {
        model.addAttribute("cursos", cursoService.findAll());
        return "cursos/listar-cursos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        CursoDTO cursoDTO = cursoService.findById(id);
        model.addAttribute("curso", cursoDTO);
        model.addAttribute("todasCategorias", categoriaService.findAll());
        return "cursos/editar-curso-form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarCurso(@PathVariable Long id,
                                 @Valid @ModelAttribute("curso") CursoDTO cursoDTO,
                                 BindingResult result,
                                 RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.curso", result);
            attributes.addFlashAttribute("curso", cursoDTO);
            attributes.addFlashAttribute("todasCategorias", categoriaService.findAll());
            return "redirect:/web/cursos/editar/" + id;
        }

        try {
            cursoService.update(id, cursoDTO);
            attributes.addFlashAttribute("success", "Curso atualizado com sucesso!");
            return "redirect:/web/cursos";
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/cursos/editar/" + id;
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirCurso(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            cursoService.delete(id);
            attributes.addFlashAttribute("success", "Curso excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/cursos";
    }

}
