package br.com.dsouzajm.utils;

import br.com.dsouzajm.controller.json.PessoaRequest;
import br.com.dsouzajm.controller.json.PessoaResponse;
import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.domain.Stack;
import br.com.dsouzajm.entity.PessoaEntity;
import br.com.dsouzajm.entity.StackEntity;

import java.util.ArrayList;
import java.util.List;

public class PessoaUtils {
    public static Pessoa toPessoa(PessoaEntity pessoaEntity) {
        ArrayList<Stack> pessoaStacks = new ArrayList<>();
        if(pessoaEntity != null && pessoaEntity.getStacks() != null) {
            for (StackEntity stackItem : pessoaEntity.getStacks()) {
                Stack stack = new Stack(pessoaEntity.getId(), stackItem.getStackItem());
                pessoaStacks.add(stack);
            }
            return new Pessoa(
                pessoaEntity.getId(),
                pessoaEntity.getApelido(),
                pessoaEntity.getNome(),
                pessoaEntity.getNascimento(),
                pessoaStacks
            );
        }
        return null;
    }

    public static PessoaResponse toPessoaResponse(Pessoa pessoa) {
        ArrayList<String> pessoaResponseStacks = new ArrayList<>();
        if(pessoa != null && pessoa.getStacks()!= null) {
            pessoaResponseStacks = new ArrayList<>();
            List<Stack> stacks = pessoa.getStacks();
            for (Stack stackItem : stacks) {
                pessoaResponseStacks.add(stackItem.getStack());
            }
            return new PessoaResponse(
                pessoa.getId(),
                pessoa.getApelido(),
                pessoa.getNome(),
                pessoa.getNascimento(),
                pessoaResponseStacks
            );
        }
        return null;
    }

    public static Pessoa toPessoa(PessoaRequest pessoaRequest) {
        ArrayList<Stack> pessoaStacks = new ArrayList<>();
        if(pessoaRequest != null && pessoaRequest.stack() != null) {
            List<String> pessoaRequestStacks = pessoaRequest.stack();
            for (String stackItem : pessoaRequestStacks) {
                Stack stack = new Stack(null, stackItem);
                pessoaStacks.add(stack);
            }
            return new Pessoa(
                    null, // Assuming id is generated elsewhere
                    pessoaRequest.apelido(),
                    pessoaRequest.nome(),
                    pessoaRequest.nascimento(),
                    pessoaStacks
            );
        }
        return null;
    }

    public static PessoaEntity toPessoaEntity(Pessoa pessoa) {
        ArrayList<StackEntity> pessoaEntityStacks = new ArrayList<>();
        if(pessoa != null) {
            PessoaEntity pessoaEntity = PessoaEntity.builder()
                    .id(pessoa.getId())
                    .apelido(pessoa.getApelido())
                    .nome(pessoa.getNome())
                    .nascimento(pessoa.getNascimento())
                    .build();
            if (pessoa.getStacks() != null) {
                List<Stack> pessoaStacks = pessoa.getStacks();
                for (Stack stackItem : pessoaStacks) {
                    StackEntity stackEntity = StackEntity.builder()
                            .stackItem(stackItem.getStack())
                            .pessoaEntity(pessoaEntity)
                            .build();
                    pessoaEntityStacks.add(stackEntity);
                }
            }
            if (!pessoaEntityStacks.isEmpty()) {
                pessoaEntity.setStacks(pessoaEntityStacks);
            }
            return pessoaEntity;
        }
        return null;
    }
}
