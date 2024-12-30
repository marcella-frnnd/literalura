package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "livros_autores",
            joinColumns = @JoinColumn(name = "livros_id"),
            inverseJoinColumns = @JoinColumn(name = "autores_id")
    )
    private List<Autor> autores = new ArrayList<>();

    private List<String> idiomas = new ArrayList<>();

    private Integer numeroDeDownloads;

    public Livro(){}

    public Livro(DadosLivro dadosLivro){
        this.titulo = dadosLivro.titulo();
        this.idiomas = dadosLivro.idiomas();
        for (DadosAutor dadosAutor : dadosLivro.autores()){
            this.autores.add(new Autor(dadosAutor));
        }
        this.numeroDeDownloads = dadosLivro.numeroDeDownloads();
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


    public Integer getNumeroDeDownloads() {
        return numeroDeDownloads;
    }

    public void setNumeroDeDownloads(Integer numeroDeDownloads) {
        this.numeroDeDownloads = numeroDeDownloads;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    @Override
    public String toString() {
        return "----- Livro -----\n" +
                "Título: '" + titulo + "'\n" +
                "Autores: " + autores + "\n" +
                "Idiomas: " + idiomas + "\n" +
                "Número de Downloads: " + numeroDeDownloads;
    }
}
