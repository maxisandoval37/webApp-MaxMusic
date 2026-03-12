package ar.dev.maxisandoval.webappmaxmusic.repository;

import ar.dev.maxisandoval.webappmaxmusic.model.Artista;
import ar.dev.maxisandoval.webappmaxmusic.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);
    Usuario findByArtista(Artista artista);
    List<Usuario> findAllByArtistaIsNotNull();
}