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

    public Pessoa savePessoa(Pessoa pessoa) {
        PessoaEntity pessoaEntity = PessoaUtils.toPessoaEntity(pessoa);
        PessoaEntity pessoaSaved = pessoaRepository.save(pessoaEntity);
        return PessoaUtils.toPessoa(pessoaSaved);
    }

    public Pessoa getPessoaById(UUID id) {
        PessoaEntity pessoaEntity = pessoaRepository.findById(id).orElse(null);
        return PessoaUtils.toPessoa(pessoaEntity);
    }
}
