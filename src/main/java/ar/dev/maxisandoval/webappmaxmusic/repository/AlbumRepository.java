package ar.dev.maxisandoval.webappmaxmusic.repository;

import ar.dev.maxisandoval.webappmaxmusic.model.Album;
import ar.dev.maxisandoval.webappmaxmusic.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByGenero(String genero);

    @Query("SELECT a FROM Album a ORDER BY LOWER(a.titulo) ASC")
    List<Album> findByAllByOrderByTituloIgnoreCaseAsc();

    void deleteByArtista(Artista artista);

    void deleteByGenero(String genero);
}