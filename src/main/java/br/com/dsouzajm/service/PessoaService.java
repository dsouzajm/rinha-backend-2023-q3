package br.com.dsouzajm.service;

import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.domain.Stack;
import br.com.dsouzajm.repository.PessoaProjection;
import br.com.dsouzajm.repository.PessoaRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PessoaService {
    public final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public Pessoa savePessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "pessoas", key = "#id")
    public Pessoa getPessoaById(UUID id) {
        return pessoaRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Pessoa> getByTermo(String termo) {
        List<PessoaProjection> pessoasProjection = pessoaRepository.findByTermoComLimite(termo);
        return pessoasProjection.stream()
                .map(proj -> {
                    List<Stack> stacks = proj.stacks() != null 
                        ? proj.stacks().stream()
                            .map(s -> new Stack(null, s))
                            .collect(Collectors.toList())
                        : Collections.emptyList();

                    return new Pessoa(
                        proj.id(),
                        proj.apelido(),
                        proj.nome(),
                        proj.nascimento(),
                        stacks
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long getContagemPessoas() {
        return pessoaRepository.count();
    }
}