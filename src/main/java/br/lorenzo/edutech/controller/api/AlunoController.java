package br.lorenzo.edutech.controller.api;

import br.lorenzo.edutech.dto.AlunoDTO;
import br.lorenzo.edutech.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> register(@Valid @RequestBody AlunoDTO alunoDTO) {
        AlunoDTO alunoSalvo = alunoService.register(alunoDTO);
        return ResponseEntity
                .created(URI.create("/api/alunos/" + alunoSalvo.id()))
                .body(alunoSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> findAll() {
        return ResponseEntity.ok(alunoService.findAll());
    }
}
