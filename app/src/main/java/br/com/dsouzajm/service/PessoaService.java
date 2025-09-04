package br.com.dsouzajm.service;

import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.entity.PessoaEntity;
import br.com.dsouzajm.entity.StackEntity;
import br.com.dsouzajm.repository.PessoaRepository;
import br.com.dsouzajm.utils.PessoaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PessoaService {
    public final PessoaRepository pessoaRepository;

    @Transactional
    public Pessoa savePessoa(Pessoa pessoa) {
        PessoaEntity pessoaEntity = PessoaUtils.toPessoaEntity(pessoa);
        PessoaEntity pessoaSaved = pessoaRepository.save(pessoaEntity);
        return PessoaUtils.toPessoa(pessoaSaved);
    }

    public Pessoa getPessoaById(UUID id) {
        PessoaEntity pessoaEntity = pessoaRepository.findById(id).orElse(null);
        return PessoaUtils.toPessoa(pessoaEntity);
    }

    public List<Pessoa> getByTermo(String termo) {
        List<PessoaEntity> pessoasEntity = pessoaRepository.findByTermoComLimite(termo);
        return pessoasEntity.stream()
                .map(PessoaUtils::toPessoa)
                .collect(Collectors.toList());
    }

    public long getContagemPessoas() {
        return pessoaRepository.count();
    }
}