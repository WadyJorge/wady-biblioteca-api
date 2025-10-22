package br.edu.infnet.wady.biblioteca.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false, length = 200)
    private String titulo;

    @NotBlank(message = "Autor é obrigatório")
    @Column(nullable = false, length = 100)
    private String autor;

    @NotBlank(message = "Editora é obrigatória")
    @Column(nullable = false, length = 100)
    private String editora;

    @NotBlank(message = "Categoria é obrigatória")
    @Column(nullable = false, length = 50)
    private String categoria;

    @NotNull(message = "Ano de publicação é obrigatório")
    @Min(value = 1000, message = "Ano de publicação inválido")
    @Max(value = 9999, message = "Ano de publicação inválido")
    @Column(nullable = false)
    private Integer anoPublicacao;

    @NotNull(message = "Disponibilidade é obrigatória")
    @Column(nullable = false)
    private Boolean disponivel;

    public Livro() {
    }

    public Livro(String titulo, String autor, String editora, String categoria, Integer anoPublicacao, Boolean disponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.categoria = categoria;
        this.anoPublicacao = anoPublicacao;
        this.disponivel = disponivel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return String.format(
                "%5d | Título: %-50s | Autor: %-25s | Editora: %-25s | Categoria: %-25s | Ano: %4d | Disponível: %-3s",
                id,
                titulo,
                autor,
                editora,
                categoria,
                anoPublicacao,
                disponivel ? "Sim" : "Não"
        );
    }
}
