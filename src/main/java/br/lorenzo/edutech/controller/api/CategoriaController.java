package br.lorenzo.edutech.controller.api;

import br.lorenzo.edutech.dto.CategoriaDTO;
import br.lorenzo.edutech.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> create(
            @Valid @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaSalva = categoriaService.create(categoriaDTO);
        return ResponseEntity
                .created(URI.create("/api/categorias/" + categoriaSalva.id()))
                .body(categoriaSalva);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }
}
