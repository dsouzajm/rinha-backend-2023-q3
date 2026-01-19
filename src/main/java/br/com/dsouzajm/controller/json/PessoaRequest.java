package br.com.dsouzajm.controller.json;

import br.com.dsouzajm.config.StrictStringDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record PessoaRequest(
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank
    @Size(max = 255)
    String apelido,

    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank
    @Size(max = 255)
    String nome,
    LocalDate nascimento,

    @JsonDeserialize(contentUsing = StrictStringDeserializer.class)
    List<@Size(max = 255) String> stack
) {
}