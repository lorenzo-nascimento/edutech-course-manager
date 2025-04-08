package br.lorenzo.edutech.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MatriculaId implements Serializable {
    private Long alunoId;
    private Long cursoId;

    public MatriculaId() {}

    public MatriculaId(Long alunoId, Long cursoId) {
        this.alunoId = alunoId;
        this.cursoId = cursoId;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatriculaId that = (MatriculaId) o;
        return Objects.equals(alunoId, that.alunoId) &&
                Objects.equals(cursoId, that.cursoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alunoId, cursoId);
    }
}
