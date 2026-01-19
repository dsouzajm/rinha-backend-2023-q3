package br.com.dsouzajm;

import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.domain.Stack;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@RestController
@RegisterReflectionForBinding({Pessoa.class, Stack.class})
public class RinhaBackend2023Q3Application {

	@RequestMapping("/")
	public String home() {
		return "Hello Docker World1";
	}

	public static void main(String[] args) {
		SpringApplication.run(RinhaBackend2023Q3Application.class, args);
	}
}