package br.lorenzo.edutech.controller.api;

import br.lorenzo.edutech.dto.MatriculaDTO;
import br.lorenzo.edutech.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    public ResponseEntity<MatriculaDTO> enroll(
            @Valid @RequestBody MatriculaDTO matriculaDTO) {
        MatriculaDTO matricula = matriculaService.enrollAluno(matriculaDTO);
        return ResponseEntity
                .created(URI.create("/api/matriculas/" + matricula.alunoId()))
                .body(matricula);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<MatriculaDTO>> listarPorAluno(
            @PathVariable Long alunoId) {
        return ResponseEntity.ok(matriculaService.findByAluno(alunoId));
    }
}
