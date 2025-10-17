package br.edu.infnet.wady.biblioteca.api.model;

public abstract class Pessoa {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    public Pessoa() {
    }

    public Pessoa(String nome, String email, String cpf, String telefone) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return String.format(
                "%5d | Nome: %-30s | Email: %-30s | CPF: %-15s | Telefone: %-15s",
                id,
                nome,
                email,
                cpf,
                telefone);
    }
}
