package br.lorenzo.edutech.service;

import br.lorenzo.edutech.dto.CursoDTO;
import br.lorenzo.edutech.exception.CategoriaNaoEncontradaException;
import br.lorenzo.edutech.model.Categoria;
import br.lorenzo.edutech.model.Curso;
import br.lorenzo.edutech.repository.CategoriaRepository;
import br.lorenzo.edutech.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CategoriaRepository categoriaRepository;

    public CursoService(CursoRepository cursoRepository, CategoriaRepository categoriaRepository) {
        this.cursoRepository = cursoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CursoDTO createCourse(CursoDTO cursoDTO) {
        if (cursoDTO.categoriasIds() == null || cursoDTO.categoriasIds().isEmpty()) {
            throw new IllegalArgumentException("Curso deve ter pelo menos uma categoria");
        }

        Curso curso = new Curso();
        curso.setTitulo(cursoDTO.titulo());
        curso.setDescricao(cursoDTO.descricao());

        List<Categoria> categorias = cursoDTO.categoriasIds().stream()
                .map(id -> categoriaRepository.findById(id)
                        .orElseThrow(() -> new CategoriaNaoEncontradaException("Nenhuma categoria encontrada com os IDs fornecidos")))
                .collect(Collectors.toList());

        curso.setCategorias(categorias);

        Curso cursoSalvo = cursoRepository.save(curso);
        return new CursoDTO(
                cursoSalvo.getId(),
                cursoSalvo.getTitulo(),
                cursoSalvo.getDescricao(),
                cursoSalvo.getCategorias().stream().map(Categoria::getId).collect(Collectors.toList())
        );
    }


}