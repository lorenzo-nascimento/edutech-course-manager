package br.lorenzo.edutech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public record CursoDTO(
        Long id,
        @NotBlank(message = "Titulo é obrigatório") @Size(min = 5, max = 100) String titulo,
        @NotBlank(message = "Descrição é obrigatória") @Size(min = 10) String descricao,
        List<Long> categoriasIds,
        List<String> categoriasNomes
) {
        public CursoDTO(Long id, String titulo, String descricao, List<Long> categoriasIds) {
                this(id, titulo, descricao, categoriasIds, new ArrayList<>());
        }
}