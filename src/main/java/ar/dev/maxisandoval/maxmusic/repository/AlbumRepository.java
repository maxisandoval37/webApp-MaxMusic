package ar.dev.maxisandoval.maxmusic.repository;

import ar.dev.maxisandoval.maxmusic.model.Album;
import ar.dev.maxisandoval.maxmusic.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByGenero(String genero);

    @Query("SELECT a FROM Album a ORDER BY LOWER(a.titulo) ASC")
    List<Album> findByAllOrderByTituloIgnoreCaseAsc();

    void deleteByArtista(Artista artista);
    void deleteByGenero(String genero);
}