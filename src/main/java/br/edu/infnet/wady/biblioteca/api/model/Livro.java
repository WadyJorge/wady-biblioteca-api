package br.edu.infnet.wady.biblioteca.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título do livro é obrigatório")
    @Size(min = 2, max = 200, message = "O título deve ter entre 2 e 200 caracteres")
    private String titulo;

    @NotBlank(message = "Autor do livro é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do autor deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String autor;

    @NotBlank(message = "Editora é obrigatória")
    @Size(max = 100, message = "O nome da editora não pode exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String editora;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 50, message = "A categoria não pode exceder 50 caracteres")
    @Column(nullable = false, length = 50)
    private String categoria;

    @NotNull(message = "Ano de publicação é obrigatório")
    @Min(value = 1000, message = "O ano de publicação deve ser maior ou igual a 1000")
    @Max(value = 9999, message = "O ano de publicação deve ser menor ou igual a 9999")
    @Column(nullable = false)
    private Integer anoPublicacao;

    @NotNull(message = "Status de disponibilidade é obrigatório")
    @Column(nullable = false)
    private Boolean disponivel;

    public Livro() {
    }

    public Livro(
            String titulo,
            String autor,
            String editora,
            String categoria,
            Integer anoPublicacao,
            Boolean disponivel
    ) {
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
