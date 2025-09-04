package br.com.dsouzajm.controller;

import br.com.dsouzajm.controller.json.PessoaRequest;
import br.com.dsouzajm.controller.json.PessoaResponse;
import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.service.PessoaService;
import br.com.dsouzajm.utils.PessoaUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/pessoas")
public class PessoaController {
    public final PessoaService pessoaService;

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<PessoaResponse> getPessoa(@PathVariable UUID id) {
        PessoaResponse pessoaResponse = PessoaUtils.toPessoaResponse(pessoaService.getPessoaById(id));
        if(pessoaResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pessoaResponse);
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaResponse>> getPessoasByTermo(@RequestParam("t") @NotBlank String termo) {
        List<Pessoa> pessoasEncontradas = pessoaService.getByTermo(termo);
        List<PessoaResponse> responseList = pessoasEncontradas.stream()
                .map(PessoaUtils::toPessoaResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    /*@GetMapping
    public ResponseEntity<Page<PessoaResponse>> getPessoasByTermo(
            @RequestParam(name = "t") @NotBlank String termo) {
        Page<Pessoa> paginaDePessoas = pessoaService.getByTermo(termo);
        Page<PessoaResponse> paginaDeResponse = paginaDePessoas.map(PessoaUtils::toPessoaResponse);
        return ResponseEntity.ok(paginaDeResponse);
    }*/

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<Long> getContagem(){
        return ResponseEntity.ok(pessoaService.getContagemPessoas());
    }

    @PostMapping("/pessoas")
    public ResponseEntity<PessoaResponse> savePessoa(@Valid @RequestBody PessoaRequest request) {
        Pessoa pessoa = pessoaService.savePessoa(PessoaUtils.toPessoa(request));
        URI locationUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoa.getId())
            .toUri();
        return ResponseEntity.created(locationUri).body(PessoaUtils.toPessoaResponse(pessoa));
    }
}