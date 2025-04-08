package br.lorenzo.edutech.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(
        Long id,

        @NotBlank(message = "Nome da categoria é obrigatório")
        String nome
) {}
