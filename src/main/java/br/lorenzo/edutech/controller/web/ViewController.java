package br.lorenzo.edutech.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/alunos")
    public String alunos() {
        return "alunos/listar-alunos";
    }

    @GetMapping("/cursos")
    public String cursos() {
        return "cursos/listar-cursos";
    }

    @GetMapping("/matriculas")
    public String matriculas() {
        return "matriculas/listar-matriculas";
    }

 
}
