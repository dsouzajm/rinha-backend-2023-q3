package br.com.dsouzajm.controller;

import br.com.dsouzajm.controller.json.PessoaResponse;
import br.com.dsouzajm.service.PessoaService;
import br.com.dsouzajm.utils.PessoaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    public final PessoaService pessoaService;

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> getPessoas(@PathVariable UUID id) {
        return ResponseEntity.ok(PessoaUtils.toPessoaResponse(pessoaService.getPessoaById(id)));
    }

    @GetMapping("/contagem-pessoas")
    public String getContagem(){
        return "1234";
    }
}