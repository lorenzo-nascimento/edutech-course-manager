package br.lorenzo.edutech.controller.api;

import br.lorenzo.edutech.dto.CursoDTO;
import br.lorenzo.edutech.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<CursoDTO> create(@Valid @RequestBody CursoDTO cursoDTO) {
        CursoDTO cursoSalvo = cursoService.createCourse(cursoDTO);
        return ResponseEntity
                .created(URI.create("/api/cursos/" + cursoSalvo.id()))
                .body(cursoSalvo);
    }

    @GetMapping("/por-categoria")
    public ResponseEntity<List<CursoDTO>> findByCategoria(
            @RequestParam String categoria) {
        return ResponseEntity.ok(cursoService.findByCategoria(categoria));
    }

}
