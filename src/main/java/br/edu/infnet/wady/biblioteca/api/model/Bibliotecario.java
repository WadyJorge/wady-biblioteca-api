package br.edu.infnet.wady.biblioteca.api.model;

public class Bibliotecario extends Pessoa {

    private String matricula;
    private Double salario;
    private Endereco endereco;

    public Bibliotecario() {
    }

    public Bibliotecario(String nome, String email, String cpf, String telefone,
                         String matricula, Double salario, Endereco endereco) {

        super(nome, email, cpf, telefone);

        this.matricula = matricula;
        this.salario = salario;
        this.endereco = endereco;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return String.format(
                "%5d | Nome: %-30s | CPF: %-15s | Matrícula: %-15s | Salário: R$ %-10.2f | Logradouro: %-30s | Número: %-6s",
                super.getId(),
                super.getNome(),
                super.getCpf(),
                matricula,
                salario,
                endereco.getLogradouro(),
                endereco.getNumero()
        );
    }
}
