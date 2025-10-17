package br.edu.infnet.wady.biblioteca.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApiApplication.class, args);
        System.out.println("Biblioteca API iniciada com sucesso!\n");
        System.out.println("Consulte http://localhost:8080/bibliotecarios para acessar os bibliotecários.");
        System.out.println("Consulte http://localhost:8080/leitores para acessar os leitores.");
        System.out.println("Consulte http://localhost:8080/livros para acessar os livros.\n");
    }

}
