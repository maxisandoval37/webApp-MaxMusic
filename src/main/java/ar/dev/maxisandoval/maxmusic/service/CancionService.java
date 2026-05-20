package ar.dev.maxisandoval.maxmusic.service;

import ar.dev.maxisandoval.maxmusic.model.*;
import ar.dev.maxisandoval.maxmusic.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CancionService {

    private CancionRepository cancionRepository;
    private AlbumRepository albumRepository;

    public Cancion obtenerCancionPorId(Long id) {
        return cancionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la canción con el id: "+id));
    }

    public List<Cancion> listarCanciones() {
        return cancionRepository.findAll();
    }

    public Cancion guardarCancion(Cancion cancion, Long albumId) {
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new EntityNotFoundException("No se encontró el album con el id: "+albumId));
        cancion.setAlbum(album);

        return cancionRepository.save(cancion);
    }

    @Transactional
    @Modifying
    public void eliminarCancion(Long id) {
        Cancion cancion = cancionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la canción con el id: "+id));
        cancionRepository.delete(cancion);
    }

}