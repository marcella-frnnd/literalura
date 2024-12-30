package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record Autores(@JsonAlias("authors") List<DadosAutor> autores) {
}
