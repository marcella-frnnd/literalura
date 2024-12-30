package br.com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int dataDeNascimento;
    private int dataDeFalecimento;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){}

    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome();
        this.dataDeNascimento = dadosAutor.dataDeNascimento();
        this.dataDeFalecimento = dadosAutor.dataDeFalecimento();
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

    public int getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(int dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public int getDataDeFalecimento() {
        return dataDeFalecimento;
    }

    public void setDataDeFalecimento(int dataDeFalecimento) {
        this.dataDeFalecimento = dataDeFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = new ArrayList<>();
        livros.forEach(livro -> {
            livro.setAutores((List<Autor>) this);
            this.livros.add(livro);
        });
    }


    @Override
    public String toString() {
        List<String> livros = this.getLivros().stream().map(Livro::getTitulo).toList();
        return "Nome: '" + nome + '\'' +
                " | Data de Nascimento: " + dataDeNascimento +
                " | Data de Falecimento: " + dataDeFalecimento +
                " | Livros: " + livros;
    }
}
