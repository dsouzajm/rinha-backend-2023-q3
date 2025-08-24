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
@Table(name = "stacks")
public class StackEntity {

    @Id // Garante que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Garante que o ID é gerado
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    /*@JoinColumn(name = "pessoaEntity_id")*/
    @JoinColumn(name = "pessoa_id")
    private PessoaEntity pessoaEntity;

    @Column(name = "stack_item")
    private String stackItem;
}
