package br.lorenzo.edutech.repository;

import br.lorenzo.edutech.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    boolean existsByEmail(String email);
    Optional<Aluno> findByEmail(String email);
}
