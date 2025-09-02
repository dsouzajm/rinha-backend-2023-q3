package br.com.dsouzajm.service;

import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.entity.PessoaEntity;
import br.com.dsouzajm.entity.StackEntity;
import br.com.dsouzajm.repository.PessoaRepository;
import br.com.dsouzajm.repository.StackRepository;
import br.com.dsouzajm.utils.PessoaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

    /*public List<Pessoa> getByTermo(String termo) {
        List<PessoaEntity> pessoasEntity = pessoaRepository.findByTermo(termo, PageRequest.of(0, 50));
        return pessoasEntity.stream()
                .map(PessoaUtils::toPessoa)
                .collect(Collectors.toList());
    }*/

    public List<Pessoa> getByTermo(String termo) {
        // Cria um objeto de paginação para buscar a primeira página com 50 resultados
        //Pageable pageable = PageRequest.of(0, 50);

        // Chama o repositório com o termo e a paginação
        //List<PessoaEntity> pessoasEntity = pessoaRepository.findByTermoNativo(termo);
        List<PessoaEntity> pessoasEntity = pessoaRepository.findByTermoComLimite(termo);


        // Converte a lista de entidades para o domínio
        return pessoasEntity.stream()
                .map(PessoaUtils::toPessoa)
                .collect(Collectors.toList());
    }

    public long getContagemPessoas() {
        return pessoaRepository.count();
    }
}
