package br.com.dsouzajm.service;

import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.entities.PessoaEntity;
import br.com.dsouzajm.repository.PessoaRepository;
import br.com.dsouzajm.utils.PessoaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PessoaService {
    public final PessoaRepository pessoaRepository;

    public void savePessoa(Pessoa pessoa) {
        PessoaEntity pessoaEntity = PessoaEntity.builder()
                .apelido(pessoa.getApelido())
                .nome(pessoa.getNome())
                .nascimento(pessoa.getNascimento())
                .stack(pessoa.getStack())
            .build();
        pessoaRepository.save(pessoaEntity);
    }

    public Pessoa getPessoaById(UUID id) {
        PessoaEntity pessoaEntity = pessoaRepository.findById(id).orElse(null);
        return PessoaUtils.toPessoa(pessoaEntity);
    }
}
