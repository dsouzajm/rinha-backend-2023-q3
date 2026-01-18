package br.com.dsouzajm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PessoaProjection(
    UUID id,
    String apelido,
    String nome,
    LocalDate nascimento,
    List<String> stacks
) {}