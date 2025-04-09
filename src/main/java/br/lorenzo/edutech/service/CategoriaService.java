package br.lorenzo.edutech.service;

import br.lorenzo.edutech.dto.CategoriaDTO;
import br.lorenzo.edutech.exception.CategoriaDuplicadaException;
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

    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(cat -> new CategoriaDTO(cat.getId(), cat.getNome()))
                .collect(Collectors.toList());
    }
}
