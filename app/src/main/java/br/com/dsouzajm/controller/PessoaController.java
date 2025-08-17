package br.com.dsouzajm.controller;

import br.com.dsouzajm.controller.json.PessoaRequest;
import br.com.dsouzajm.controller.json.PessoaResponse;
import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.service.PessoaService;
import br.com.dsouzajm.utils.PessoaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    public final PessoaService pessoaService;

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> getPessoa(@PathVariable UUID id) {
        return ResponseEntity.ok(PessoaUtils.toPessoaResponse(pessoaService.getPessoaById(id)));
    }

    @GetMapping("/contagem-pessoas")
    public String getContagem(){
        return "1234";
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> savePessoa(@RequestBody PessoaRequest request) {
        Pessoa pessoa = pessoaService.savePessoa(PessoaUtils.toPessoa(request));
        return ResponseEntity.ok(PessoaUtils.toPessoaResponse(pessoa));
    }
}