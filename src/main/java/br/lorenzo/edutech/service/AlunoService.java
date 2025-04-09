package br.lorenzo.edutech.service;

import br.lorenzo.edutech.dto.AlunoDTO;
import br.lorenzo.edutech.exception.EmailDuplicadoException;
import br.lorenzo.edutech.model.Aluno;
import br.lorenzo.edutech.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public AlunoDTO cadastrar(AlunoDTO alunoDTO) {
        // Valida e-mail único
        if (alunoRepository.existsByEmail(alunoDTO.email())) {
            throw new EmailDuplicadoException("O e-mail " + alunoDTO.email() + " já está cadastrado");
        }

        Aluno aluno = new Aluno();
        aluno.setNome(alunoDTO.nome());
        aluno.setEmail(alunoDTO.email());

        Aluno alunoSalvo = alunoRepository.save(aluno);
        return new AlunoDTO(alunoSalvo.getId(), alunoSalvo.getNome(), alunoSalvo.getEmail());
    }
}
