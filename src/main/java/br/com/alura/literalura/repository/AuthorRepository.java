package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.nome ILIKE %:nomes%")
    Optional<Autor> findByNome(String nomes);

    @Query("SELECT a FROM Autor a WHERE :ano BETWEEN a.dataDeNascimento AND a.dataDeFalecimento")
    List<Autor> encontrarAutorVivoEmDeterminadoAno(int ano);
}
