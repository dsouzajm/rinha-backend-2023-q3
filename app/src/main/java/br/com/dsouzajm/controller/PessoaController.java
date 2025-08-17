package br.com.dsouzajm.controller;

import br.com.dsouzajm.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/pessoas")
public class PessoaController {
    public final PessoaService pessoaService;

    @GetMapping("/{id}")
    public String getPessoas(@PathVariable String id) {
        return "pessoas";
    }

    @GetMapping("/contagem-pessoas")
    public String getContagem(){
        return "1234";
    }
}