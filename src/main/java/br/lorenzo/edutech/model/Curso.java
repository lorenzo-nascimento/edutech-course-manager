package br.lorenzo.edutech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 5, max = 100, message = "Título deve ter entre 5 e 100 caracteres")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatório")
    @Size(min = 10, message = "Descrição deve ter no mínimo 10 caracteres")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToMany
    @JoinTable(
            name = "curso_categoria",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();
}