package br.edu.infnet.wady.biblioteca.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Leitor é obrigatório")
    @ManyToOne
    @JoinColumn(name = "leitor_id", nullable = false)
    private Leitor leitor;

    @NotNull(message = "Livro é obrigatório")
    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @NotNull(message = "Data de empréstimo é obrigatória")
    @PastOrPresent(message = "A data de empréstimo não pode ser futura")
    @Column(nullable = false)
    private LocalDate dataEmprestimo;

    @NotNull(message = "Data de devolução prevista é obrigatória")
    @Future(message = "A data de devolução prevista deve ser futura")
    @Column(nullable = false)
    private LocalDate dataDevolucaoPrevista;

    @PastOrPresent(message = "A data de devolução real não pode ser futura")
    @Column
    private LocalDate dataDevolucaoReal;

    @NotNull(message = "Status ativo é obrigatório")
    @Column(nullable = false)
    private Boolean ativo;

    public Emprestimo() {
    }
    public Emprestimo(
            Leitor leitor,
            Livro livro,
            LocalDate dataEmprestimo,
            LocalDate dataDevolucaoPrevista
    ) {
        this.leitor = leitor;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.ativo = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format(
                "%5d | Leitor: %-30s | Livro: %-50s | Empréstimo: %s | Devolução Prevista: %s | Status: %-3s",
                id,
                leitor != null ? leitor.getNome() : "N/A",
                livro != null ? livro.getTitulo() : "N/A",
                dataEmprestimo.format(formatter),
                dataDevolucaoPrevista.format(formatter),
                ativo ? "Ativo" : "Devolvido"
        );
    }
}
