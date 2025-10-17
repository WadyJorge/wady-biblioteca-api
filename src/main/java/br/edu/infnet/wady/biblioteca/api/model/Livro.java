package br.edu.infnet.wady.biblioteca.api.model;

public class Livro {

    private Long id;
    private String titulo;
    private String autor;
    private String editora;
    private String categoria;
    private Integer anoPublicacao;
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
