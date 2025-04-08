package br.lorenzo.edutech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CursoDTO(
        Long id,

        @NotBlank(message = "Título é obrigatório")
        @Size(min = 5, max = 100, message = "Título deve ter entre 5 e 100 caracteres")
        String titulo,

        @NotBlank(message = "Descrição é obrigatório")
        @Size(min = 10, message = "Descrição deve ter no mínimo 10 caracteres")
        String descricao,

        List<Long> categoriasIds
) {}