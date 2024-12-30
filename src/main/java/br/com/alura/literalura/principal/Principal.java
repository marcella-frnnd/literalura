package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AuthorRepository;
import br.com.alura.literalura.repository.BookRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConverteDados conversor = new ConverteDados();
    private ConsumoApi consumo = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private BookRepository repositorioLivro;

    private AuthorRepository repositorioAutor;

    public Principal(BookRepository repositorioLivro, AuthorRepository repositorioAutor){
        this.repositorioLivro = repositorioLivro;
        this.repositorioAutor = repositorioAutor;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ___________________
                        LITERALURA
                    ___________________
                    Escolha o número de sua uma opção:

                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado período
                    5 - Listar livros em um determinado idioma

                    0 - Sair
                    
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 1:
                    buscarLivrosWeb();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosDeterminadoAno();
                    break;
                case 5:
                    listarLivrosPeloIdioma();
                    break;
                case 0:
                    System.out.println("Finalizando a aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

    }


    private Resultados getLivros() {
        System.out.println("Digite o nome do livro que deseja buscar:");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(URL_BASE + nomeLivro.replace(" ", "%20"));
        Resultados dados = conversor.obterDados(json, Resultados.class);
        return dados;
    }

    private void buscarLivrosWeb() {
        Resultados dados = getLivros();
        Optional<DadosLivro> buscaLivro = dados.resultados()
                .stream()
                .findFirst();

        if (buscaLivro.isPresent()) {
            DadosLivro dadosLivro = buscaLivro.get();
            Optional<Livro> livrosRegistrados = repositorioLivro.findByTrechoDoTitulo(dadosLivro.titulo());
            if (livrosRegistrados.isPresent()) {
                System.out.println("\nLivro já registrado no Banco de Dados!\n");
                System.out.println(dadosLivro + "\n");
            } else {
                Livro livro = new Livro(dadosLivro);
                repositorioLivro.save(livro);
                System.out.println(livro);
            }
        }
    }

    private void listarLivrosRegistrados(){
        List<Livro> livros = repositorioLivro.findAll();
        if (!livros.isEmpty()){
            livros.forEach(System.out::println);
        } else {
            System.out.println("Nenhum livro registrado no Banco de Dados!");
        }
    }

    private void listarAutoresRegistrados(){
        List<Autor> autores = repositorioAutor.findAll();
        if (!autores.isEmpty()){
            autores.forEach(System.out::println);
        } else {
            System.out.println("Nenhum autor registrado no Banco de Dados!");
        }
    }

    private void listarAutoresVivosDeterminadoAno(){
        System.out.println("Digite o ano que você deseja buscar:");
        var ano = leitura.nextInt();
        leitura.nextLine();

        if (ano != 0){

            List<Autor> autores = repositorioAutor.encontrarAutorVivoEmDeterminadoAno(ano);
            if (autores.isEmpty()){
                System.out.println("Nenhum autor encontrado vivo no período informado!");
            } else {
                autores.stream().forEach(System.out::println);
            }

        } else {
            System.out.println("Opção inválida");
        }
    }

    private void listarLivrosPeloIdioma(){
        List<String> idiomasRegistrados = repositorioLivro.findByIdiomaRegistrado();
        if (!idiomasRegistrados.isEmpty()) {
            System.out.println("--- Idiomas registrados no banco de dados ---");
            idiomasRegistrados.forEach(System.out::println);
        } else {
            System.out.println("Nenhum livro registrado no banco de dados!");
        }

        System.out.println("Digite o idioma que deseja buscar:");
        var idiomaBuscado = leitura.nextLine();

        List<Livro> livrosPorIdioma = repositorioLivro.findAll();
        Set<Livro> livrosRegistrados = livrosPorIdioma.stream()
                .filter(livro -> livro.getIdiomas().contains(idiomaBuscado))
                .collect(Collectors.toSet());

        if(livrosRegistrados.isEmpty()){
            System.out.println("Nenhum livro encontrado nesse idioma!");
        } else {
            System.out.println("Livros encontrados no idioma (" + idiomaBuscado.toUpperCase() + ")");
            livrosRegistrados.forEach(System.out::println);
        }



    }


}
