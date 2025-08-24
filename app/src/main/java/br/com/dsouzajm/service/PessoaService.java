package br.com.dsouzajm.service;

import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.entity.PessoaEntity;
import br.com.dsouzajm.entity.StackEntity;
import br.com.dsouzajm.repository.PessoaRepository;
import br.com.dsouzajm.repository.StackRepository;
import br.com.dsouzajm.utils.PessoaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PessoaService {
    public final PessoaRepository pessoaRepository;
    public final StackRepository stackRepository;

    public Pessoa savePessoa(Pessoa pessoa) {
        PessoaEntity pessoaEntity = PessoaUtils.toPessoaEntity(pessoa);
        PessoaEntity pessoaSaved = pessoaRepository.save(pessoaEntity);
        List<StackEntity> stackEntitiesSaved = new ArrayList<>();
        for(int i = 0; i < pessoa.getStacks().size(); i++) {
            StackEntity stackEntity = new StackEntity(null, pessoaEntity, pessoa.getStacks().get(i).getStack());
            StackEntity stackEntitySaved = stackRepository.save(stackEntity);
            stackEntitiesSaved.add(stackEntitySaved);
        }
        pessoaSaved.setStacks(stackEntitiesSaved);
        return PessoaUtils.toPessoa(pessoaSaved);
    }

    public Pessoa getPessoaById(UUID id) {
        PessoaEntity pessoaEntity = pessoaRepository.findById(id).orElse(null);
        return PessoaUtils.toPessoa(pessoaEntity);
    }

    public List<Pessoa> getByTermo(String termo) {
        List<PessoaEntity> pessoasEntity = pessoaRepository.findByTermo(termo);
        List<StackEntity> stacks = stackRepository.findByTermo(termo);
        for(StackEntity stack : stacks) {
            Optional<PessoaEntity> pessoaEntity = pessoaRepository.findById(stack.getPessoaEntity().getId());
            if(pessoaEntity.isPresent()){
                pessoasEntity.add(pessoaEntity.get());
            }
        }
        return pessoasEntity.stream()
                .map(PessoaUtils::toPessoa)
                .collect(Collectors.toList());
    }

    public long getContagemPessoas() {
        return pessoaRepository.count();
    }
}
