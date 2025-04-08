package br.lorenzo.edutech.dto;

import java.time.LocalDateTime;

public record MatriculaDTO(
        Long alunoId,
        Long cursoId,
        LocalDateTime dataInscricao
) {

    public MatriculaDTO(Long alunoId, Long cursoId) {
        this(alunoId, cursoId, LocalDateTime.now());
    }
}
