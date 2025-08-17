package br.com.dsouzajm.utils;

import br.com.dsouzajm.controller.json.PessoaResponse;
import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.entities.PessoaEntity;

public class PessoaUtils {
    public static Pessoa toPessoa(PessoaEntity pessoaEntity) {
        return new Pessoa(
            pessoaEntity.getId(),
            pessoaEntity.getApelido(),
            pessoaEntity.getNome(),
            pessoaEntity.getNascimento(),
            pessoaEntity.getStack()
        );
    }

    public static PessoaResponse toPessoaResponse(Pessoa pessoa) {
        return new PessoaResponse(
            pessoa.getId(),
            pessoa.getApelido(),
            pessoa.getNome(),
            pessoa.getNascimento(),
            pessoa.getStack()
        );
    }
}
