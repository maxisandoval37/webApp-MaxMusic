package ar.dev.maxisandoval.webappmaxmusic.service;

import ar.dev.maxisandoval.webappmaxmusic.model.Album;
import ar.dev.maxisandoval.webappmaxmusic.model.Cancion;
import ar.dev.maxisandoval.webappmaxmusic.repository.AlbumRepository;
import ar.dev.maxisandoval.webappmaxmusic.repository.CancionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CancionService {

    CancionRepository cancionRepository;
    AlbumRepository albumRepository;

    public Cancion obtenerCancionPorId(Long id) {
        return cancionRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró la canción con el id: "+id));
    }

    public List<Cancion> listarCanciones() {
        return cancionRepository.findAll();
    }

    public Cancion guardarCancion(Cancion cancion, Long albumId) {
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new EntityNotFoundException("Cancion no encontrada"));
        cancion.setAlbum(album);

        return cancionRepository.save(cancion);
    }

    @Transactional
    @Modifying
    public void eliminarCancion(Long id) {
        Cancion cancion = cancionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cancion no encontrada"));
        cancionRepository.delete(cancion);
    }

}