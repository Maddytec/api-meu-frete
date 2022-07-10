package br.com.meu.frete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MeuFreteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeuFreteApplication.class, args);
	}

}
