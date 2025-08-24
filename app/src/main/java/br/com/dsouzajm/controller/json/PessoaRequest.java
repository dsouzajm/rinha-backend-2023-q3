package br.com.dsouzajm.controller.json;

import java.time.LocalDate;
import java.util.List;

public record PessoaRequest(
    String apelido,
    String nome,
    LocalDate nascimento,
    List<String> stacks
) {
}