package br.com.dsouzajm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "pessoas")
public class PessoaEntity {
    @Id
    private UUID id;

    @Column(name = "apelido", unique = true, nullable = false, length = 32)
    private String apelido;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "nascimento", nullable = false)
    private LocalDate nascimento;

    @Column(name = "stack_item")
    private List<String> stack;
}
