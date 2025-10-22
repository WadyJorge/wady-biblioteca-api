package br.edu.infnet.wady.biblioteca.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bibliotecarios")
public class Bibliotecario extends Pessoa {

    @NotBlank(message = "Matrícula é obrigatória")
    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @NotNull(message = "Salário é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salário deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    @NotNull(message = "Endereço é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    public Bibliotecario() {
    }

    public Bibliotecario(String nome, String email, String cpf, String telefone,
                         String matricula, Double salario, Endereco endereco) {

        super(nome, email, cpf, telefone);

        this.matricula = matricula;
        this.salario = BigDecimal.valueOf(salario);
        this.endereco = endereco;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
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
                "%5d | Nome: %-30s | CPF: %-14s | Matrícula: %-10s | Salário: R$ %-10.2f | Logradouro: %-30s | Número: %-6s",
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
