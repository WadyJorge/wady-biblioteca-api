package br.edu.infnet.wady.biblioteca.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "leitores")
public class Leitor extends Pessoa {

    @NotBlank(message = "Matrícula é obrigatória")
    @Size(min = 5, max = 20, message = "A matrícula deve ter entre 5 e 20 caracteres")
    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @NotNull(message = "Data de inscrição é obrigatória")
    @PastOrPresent(message = "A data de inscrição não pode ser futura")
    private LocalDate dataInscricao;

    @NotNull(message = "Status ativo é obrigatório")
    @Column(nullable = false)
    private Boolean ativo;

    public Leitor() {
    }

    public Leitor(
            String nome,
            String email,
            String cpf,
            String telefone,
            String matricula,
            Boolean ativo
    ) {
        super(nome, email, cpf, telefone);

        this.matricula = matricula;
        this.dataInscricao = LocalDate.now();
        this.ativo = ativo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDate dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return String.format(
                "%5d | Nome: %-30s | CPF: %-14s | Matrícula: %-10s | Status: %-3s",
                super.getId(),
                super.getNome(),
                super.getCpf(),
                matricula,
                ativo ? "Ativo" : "Suspenso"
        );
    }
}
