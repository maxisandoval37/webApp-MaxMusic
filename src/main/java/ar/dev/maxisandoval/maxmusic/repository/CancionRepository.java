package ar.dev.maxisandoval.maxmusic.repository;

import ar.dev.maxisandoval.maxmusic.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {
}