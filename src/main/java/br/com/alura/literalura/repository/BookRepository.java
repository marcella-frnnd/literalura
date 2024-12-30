package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l WHERE l.titulo ILIKE %:nomeBuscado%")
    Optional<Livro> findByTrechoDoTitulo(String nomeBuscado);

    @Query("SELECT DISTINCT idiomas FROM Livro")
    List<String> findByIdiomaRegistrado();
}
