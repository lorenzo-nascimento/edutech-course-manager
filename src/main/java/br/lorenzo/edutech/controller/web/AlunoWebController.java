package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.service.AlunoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/alunos")
public class AlunoWebController {

    private final AlunoService alunoService;

    public AlunoWebController (AlunoService alunoService) {
        this.alunoService = alunoService;
    }


    public String listAlunos(Model model) {
        model.addAttribute("alunos", alunoService.findAll());
        return "alunos/listar-alunos";
    }


}
