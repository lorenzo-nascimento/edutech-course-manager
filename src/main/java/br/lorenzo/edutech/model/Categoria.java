package br.lorenzo.edutech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da categoria é obrigatório")
    @Column(unique = true)
    private String nome;

    @ManyToMany(mappedBy = "categorias")
    private List<Curso> cursos = new ArrayList<>();
}
