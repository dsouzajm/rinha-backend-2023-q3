package br.com.dsouzajm.controller.json;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PessoaResponse(
    UUID id,
    String apelido,
    String nome,
    LocalDate nascimento,
    List<String> stack
) {
}
