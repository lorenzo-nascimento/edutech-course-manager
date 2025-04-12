package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.service.MatriculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/matriculas")
public class MatriculaWebController {

    private final MatriculaService matriculaService;

    public MatriculaWebController (MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }


    public String listMatriculas (Model model) {
        model.addAttribute("matriculas", matriculaService.findAll());
        return "matriculas/listar-matriculas";
    }

}
