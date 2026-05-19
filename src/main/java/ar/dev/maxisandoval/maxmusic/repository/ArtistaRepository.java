package ar.dev.maxisandoval.maxmusic.repository;

import ar.dev.maxisandoval.maxmusic.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}