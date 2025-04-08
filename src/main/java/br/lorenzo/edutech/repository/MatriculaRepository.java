package br.lorenzo.edutech.repository;

import br.lorenzo.edutech.model.Aluno;
import br.lorenzo.edutech.model.Curso;
import br.lorenzo.edutech.model.Matricula;
import br.lorenzo.edutech.model.MatriculaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, MatriculaId> {


    List<Matricula> findByAluno(Aluno aluno);


    List<Matricula> findByCurso(Curso curso);

    boolean existsByAlunoAndCurso(Aluno aluno, Curso curso);
}
