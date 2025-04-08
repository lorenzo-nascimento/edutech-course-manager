package br.lorenzo.edutech.repository;

import br.lorenzo.edutech.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("SELECT c FROM Curso c JOIN c.categorias cat WHERE cat.nome = :nomeCategoria")
    List<Curso> findByCategoria(@Param("nomeCategoria") String nomeCategoria);
}
