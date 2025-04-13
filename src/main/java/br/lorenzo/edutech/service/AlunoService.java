package br.lorenzo.edutech.service;

import br.lorenzo.edutech.dto.AlunoDTO;
import br.lorenzo.edutech.exception.AlunoNaoEncontradoException;
import br.lorenzo.edutech.exception.EmailDuplicadoException;
import br.lorenzo.edutech.model.Aluno;
import br.lorenzo.edutech.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public AlunoDTO register(AlunoDTO alunoDTO) {
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

    public AlunoDTO findById(Long id) {
        return alunoRepository.findById(id)
                .map(aluno -> new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail()))
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado"));
    }

    public List<AlunoDTO> findAll() {
        return alunoRepository.findAll().stream()
                .map(aluno -> new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail()))
                .collect(Collectors.toList());
    }

    @Transactional
    public AlunoDTO update(Long id, AlunoDTO alunoDTO) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado"));

        if (!aluno.getEmail().equals(alunoDTO.email()) &&
                alunoRepository.existsByEmail(alunoDTO.email())) {
            throw new EmailDuplicadoException("O e-mail " + alunoDTO.email() + " já está cadastrado");
        }

        aluno.setNome(alunoDTO.nome());
        aluno.setEmail(alunoDTO.email());

        alunoRepository.save(aluno);
        return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail());
    }

    @Transactional
    public void delete(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new AlunoNaoEncontradoException("Aluno não encontrado");
        }
        alunoRepository.deleteById(id);
    }

}
