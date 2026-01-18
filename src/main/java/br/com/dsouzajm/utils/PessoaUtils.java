package br.com.dsouzajm.utils;

import br.com.dsouzajm.controller.json.PessoaRequest;
import br.com.dsouzajm.controller.json.PessoaResponse;
import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.domain.Stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PessoaUtils {

    public static PessoaResponse toPessoaResponse(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }

        List<String> pessoaResponseStacks = new ArrayList<>();
        if (pessoa.getStacks() != null) {
            for (Stack stackItem : pessoa.getStacks()) {
                if (stackItem != null && stackItem.getStack() != null) {
                    pessoaResponseStacks.add(stackItem.getStack());
                }
            }
        }

        return new PessoaResponse(
            pessoa.getId(),
            pessoa.getApelido(),
            pessoa.getNome(),
            pessoa.getNascimento(),
            pessoaResponseStacks
        );
    }

    public static Pessoa toPessoa(PessoaRequest pessoaRequest) {
        if (pessoaRequest == null) {
            return null;
        }

        List<Stack> pessoaStacks = new ArrayList<>();
        if (pessoaRequest.stack() != null) {
            for (String stackItem : pessoaRequest.stack()) {
                if (stackItem != null) {
                    pessoaStacks.add(new Stack(null, stackItem));
                }
            }
        }

        return new Pessoa(
                null, // ID será gerado no repositório
                pessoaRequest.apelido(),
                pessoaRequest.nome(),
                pessoaRequest.nascimento(),
                pessoaStacks
        );
    }
}