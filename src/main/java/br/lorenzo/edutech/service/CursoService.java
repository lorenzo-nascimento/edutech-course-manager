package br.lorenzo.edutech.service;

import br.lorenzo.edutech.dto.CursoDTO;
import br.lorenzo.edutech.exception.CategoriaNaoEncontradaException;
import br.lorenzo.edutech.exception.CursoNaoEncontradoException;
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
                cursoSalvo.getCategorias().stream().map(Categoria::getId).collect(Collectors.toList()),
                cursoSalvo.getCategorias().stream().map(Categoria::getNome).collect(Collectors.toList())
        );
    }

    public CursoDTO findById(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNaoEncontradoException("Curso não encontrado com o ID: " + id));

        return new CursoDTO(
                curso.getId(),
                curso.getTitulo(),
                curso.getDescricao(),
                curso.getCategorias().stream().map(Categoria::getId).collect(Collectors.toList()),
                curso.getCategorias().stream().map(Categoria::getNome).collect(Collectors.toList())
        );
    }


    public List<CursoDTO> findByCategoria(String nomeCategoria) {
        return cursoRepository.findByCategoria(nomeCategoria).stream()
                .map(curso -> new CursoDTO(
                        curso.getId(),
                        curso.getTitulo(),
                        curso.getDescricao(),
                        curso.getCategorias().stream().map(Categoria::getId).collect(Collectors.toList()),
                        curso.getCategorias().stream().map(Categoria::getNome).collect(Collectors.toList())
                        ))
                .collect(Collectors.toList());
    }

    public List<CursoDTO> findAll() {
        return cursoRepository.findAll().stream()
                .map(curso -> new CursoDTO(
                        curso.getId(),
                        curso.getTitulo(),
                        curso.getDescricao(),
                        curso.getCategorias().stream().map(Categoria::getId).collect(Collectors.toList()),
                        curso.getCategorias().stream().map(Categoria::getNome).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public CursoDTO update(Long id, CursoDTO cursoDTO) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNaoEncontradoException("Curso não encontrado"));

        if (cursoDTO.categoriasIds() == null || cursoDTO.categoriasIds().isEmpty()) {
            throw new IllegalArgumentException("Curso deve ter pelo menos uma categoria");
        }

        curso.setTitulo(cursoDTO.titulo());
        curso.setDescricao(cursoDTO.descricao());

        List<Categoria> categorias = cursoDTO.categoriasIds().stream()
                .map(catId -> categoriaRepository.findById(catId)
                        .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada")))
                .collect(Collectors.toList());

        curso.getCategorias().clear();
        curso.getCategorias().addAll(categorias);

        cursoRepository.save(curso);
        return new CursoDTO(
                curso.getId(),
                curso.getTitulo(),
                curso.getDescricao(),
                curso.getCategorias().stream().map(Categoria::getId).collect(Collectors.toList()),
                curso.getCategorias().stream().map(Categoria::getNome).collect(Collectors.toList())
        );
    }

    @Transactional
    public void delete(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new CursoNaoEncontradoException("Curso não encontrado");
        }
        cursoRepository.deleteById(id);
    }

}