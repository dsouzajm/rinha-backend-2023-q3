package br.com.dsouzajm.controller;

import br.com.dsouzajm.controller.json.PessoaRequest;
import br.com.dsouzajm.controller.json.PessoaResponse;
import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.service.PessoaService;
import br.com.dsouzajm.utils.PessoaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    public final PessoaService pessoaService;

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> getPessoa(@PathVariable UUID id) {
        return ResponseEntity.ok(PessoaUtils.toPessoaResponse(pessoaService.getPessoaById(id)));
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> getPessoasByTermo(@RequestParam("t") String termo) {
        List<Pessoa> pessoasEncontradas = pessoaService.getByTermo(termo);
        List<PessoaResponse> responseList = pessoasEncontradas.stream()
                .map(PessoaUtils::toPessoaResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<Long> getContagem(){
        return ResponseEntity.ok(pessoaService.getContagemPessoas());
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> savePessoa(@RequestBody PessoaRequest request) {
        Pessoa pessoa = pessoaService.savePessoa(PessoaUtils.toPessoa(request));
        URI locationUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoa.getId())
            .toUri();
        return ResponseEntity.created(locationUri).body(PessoaUtils.toPessoaResponse(pessoa));
    }
}