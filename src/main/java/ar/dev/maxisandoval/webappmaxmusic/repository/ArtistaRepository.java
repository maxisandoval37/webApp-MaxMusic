package ar.dev.maxisandoval.webappmaxmusic.repository;

import ar.dev.maxisandoval.webappmaxmusic.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}