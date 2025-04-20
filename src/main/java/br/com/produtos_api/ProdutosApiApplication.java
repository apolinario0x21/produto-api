package br.com.produtos_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class ProdutosApiApplication {
	public static void main(String[] args) {

		SpringApplication.run(ProdutosApiApplication.class, args);
	}
}
