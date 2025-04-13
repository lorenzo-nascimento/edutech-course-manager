package br.lorenzo.edutech.service;

import br.lorenzo.edutech.dto.CategoriaDTO;
import br.lorenzo.edutech.exception.CategoriaDuplicadaException;
import br.lorenzo.edutech.exception.CategoriaNaoEncontradaException;
import br.lorenzo.edutech.model.Categoria;
import br.lorenzo.edutech.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CategoriaDTO create(CategoriaDTO categoriaDTO) {
        if (categoriaRepository.existsByNome(categoriaDTO.nome())) {
            throw new CategoriaDuplicadaException("Categoria já existente: " + categoriaDTO.nome());
        }

        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.nome());

        Categoria saved = categoriaRepository.save(categoria);
        return new CategoriaDTO(saved.getId(), saved.getNome());
    }

    public CategoriaDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com o ID: " + id));

        return new CategoriaDTO(categoria.getId(), categoria.getNome());
    }


    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(cat -> new CategoriaDTO(cat.getId(), cat.getNome()))
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada"));

        if (!categoria.getNome().equals(categoriaDTO.nome()) &&
                categoriaRepository.existsByNome(categoriaDTO.nome())) {
            throw new CategoriaDuplicadaException("Categoria " + categoriaDTO.nome() + " já existe");
        }

        categoria.setNome(categoriaDTO.nome());
        categoriaRepository.save(categoria);

        return new CategoriaDTO(categoria.getId(), categoria.getNome());
    }

    @Transactional
    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada"));

        if (!categoria.getCursos().isEmpty()) {
            int quantidadeCursos = categoria.getCursos().size();
            throw new IllegalStateException(
                    "Esta categoria está vinculada a " + quantidadeCursos +
                            " curso(s). Remova os vínculos antes de excluir."
            );
        }

        categoriaRepository.delete(categoria);
    }


}
