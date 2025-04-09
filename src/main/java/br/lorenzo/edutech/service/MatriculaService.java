package br.lorenzo.edutech.service;

import br.lorenzo.edutech.dto.MatriculaDTO;
import br.lorenzo.edutech.exception.AlunoNaoEncontradoException;
import br.lorenzo.edutech.exception.CursoNaoEncontradoException;
import br.lorenzo.edutech.exception.MatriculaDuplicadaException;
import br.lorenzo.edutech.model.Aluno;
import br.lorenzo.edutech.model.Curso;
import br.lorenzo.edutech.model.Matricula;
import br.lorenzo.edutech.repository.AlunoRepository;
import br.lorenzo.edutech.repository.CursoRepository;
import br.lorenzo.edutech.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            AlunoRepository alunoRepository,
                            CursoRepository cursoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public MatriculaDTO enrollAluno(MatriculaDTO matriculaDTO) {
        Aluno aluno = alunoRepository.findById(matriculaDTO.alunoId())
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado com ID: " + matriculaDTO.alunoId()));

        Curso curso = cursoRepository.findById(matriculaDTO.cursoId())
                .orElseThrow(() -> new CursoNaoEncontradoException("Curso não encontrado com ID: " + matriculaDTO.cursoId()));

        if (matriculaRepository.existsByAlunoAndCurso(aluno, curso)) {
            throw new MatriculaDuplicadaException(
                    "O aluno ID " + aluno.getId() +
                            " já está matriculado no curso ID " + curso.getId()
            );
        }

        Matricula matricula = new Matricula(aluno, curso);
        matriculaRepository.save(matricula);

        return new MatriculaDTO(
                matricula.getId().getAlunoId(),
                matricula.getId().getCursoId(),
                matricula.getDataInscricao()
        );
    }

    public List<MatriculaDTO> findByAluno(Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado"));

        return matriculaRepository.findByAluno(aluno).stream()
                .map(mat -> new MatriculaDTO(
                        mat.getId().getAlunoId(),
                        mat.getId().getCursoId(),
                        mat.getDataInscricao()))
                .collect(Collectors.toList());
    }

}