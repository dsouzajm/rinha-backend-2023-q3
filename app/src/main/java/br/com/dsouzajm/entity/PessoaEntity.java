package br.com.dsouzajm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "apelido", unique = true, nullable = false, length = 32)
    private String apelido;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "nascimento", nullable = false)
    private LocalDate nascimento;

    // CORREÇÃO: O valor de 'mappedBy' deve ser "pessoa", o nome do campo na StackEntity
    @OneToMany(mappedBy = "pessoaEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<StackEntity> stacks = new ArrayList<>();

    // Método auxiliar para sincronizar os dois lados da relação
//    public void setStacks(List<StackEntity> stacks) {
//        if (stacks != null) {
//            this.stacks.clear();
//            for (StackEntity stack : stacks) {
//                stack.setPessoaEntity(this); // Define a referência de volta
//                this.stacks.add(stack);
//            }
//        }
//    }
}
