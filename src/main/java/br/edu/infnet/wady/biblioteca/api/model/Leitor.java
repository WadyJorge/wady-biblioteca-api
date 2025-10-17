package br.edu.infnet.wady.biblioteca.api.model;

import java.time.LocalDate;

public class Leitor extends Pessoa {

    private String matricula;
    private LocalDate dataInscricao;
    private Boolean ativo;

    public Leitor() {
    }

    public Leitor(String nome, String email, String cpf, String telefone,
                  String matricula, Boolean ativo) {

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
                "%5d | Nome: %-30s | CPF: %-14s | Matrícula: %-10s | Ativo: %-3s",
                super.getId(),
                super.getNome(),
                super.getCpf(),
                matricula,
                ativo ? "Sim" : "Não");
    }
}
