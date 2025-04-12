package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.service.CursoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/cursos")
public class CursoWebController {

    private final CursoService cursoService;

    public CursoWebController (CursoService cursoService) {
        this.cursoService = cursoService;
    }

    public String listCursos (Model model) {
        model.addAttribute("cursos", cursoService.findAll());
        return "cursos/listar-cursos";
    }

}
