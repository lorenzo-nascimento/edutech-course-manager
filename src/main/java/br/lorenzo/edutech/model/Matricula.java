package br.lorenzo.edutech.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "matricula")
public class Matricula {

    @EmbeddedId
    private MatriculaId id;

    @ManyToOne
    @MapsId("alunoId")
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @MapsId("cursoId")
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(name = "data_inscricao")
    private LocalDateTime dataInscricao = LocalDateTime.now();

    public Matricula(Aluno aluno, Curso curso) {
        this.id = new MatriculaId(aluno.getId(), curso.getId());
        this.aluno = aluno;
        this.curso = curso;
    }

    public Matricula() {

    }

}
