package br.edu.infnet.wady.biblioteca.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApiApplication.class, args);
        System.out.println("API de Biblioteca iniciada com sucesso!");
        System.out.println("Consulte http://localhost:8080/livros para acessar os livros.\n");
	}

}
