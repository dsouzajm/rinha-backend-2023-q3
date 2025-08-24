package br.com.dsouzajm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "pessoas")
public class PessoaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
