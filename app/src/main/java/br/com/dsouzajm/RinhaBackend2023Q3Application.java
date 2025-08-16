package br.com.dsouzajm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RinhaBackend2023Q3Application {

	@RequestMapping("/")
	public String home() {
		return "Hello Docker World1";
	}

	public static void main(String[] args) {
		SpringApplication.run(RinhaBackend2023Q3Application.class, args);
	}
}
