package br.com.dsouzajm.controller.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record PessoaRequest(
    @NotBlank
    @Size
    String apelido,

    @NotBlank
    @Size
    String nome,
    LocalDate nascimento,
    List<String> stack
) {
}