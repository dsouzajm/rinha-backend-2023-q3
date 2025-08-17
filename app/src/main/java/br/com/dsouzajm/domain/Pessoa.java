package br.com.dsouzajm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Pessoa {
    private UUID id;
    private String apelido;
    private String nome;
    private LocalDate nascimento;
    private List<String> stack;
}
