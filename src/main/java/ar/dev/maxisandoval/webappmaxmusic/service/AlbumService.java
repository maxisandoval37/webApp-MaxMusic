package ar.dev.maxisandoval.webappmaxmusic.service;

import ar.dev.maxisandoval.webappmaxmusic.model.Album;
import ar.dev.maxisandoval.webappmaxmusic.model.Artista;
import ar.dev.maxisandoval.webappmaxmusic.model.Cancion;
import ar.dev.maxisandoval.webappmaxmusic.repository.AlbumRepository;
import ar.dev.maxisandoval.webappmaxmusic.repository.ArtistaRepository;
import ar.dev.maxisandoval.webappmaxmusic.repository.CancionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AlbumService {

    private AlbumRepository albumRepository;
    private ArtistaRepository artistaRepository;
    private CancionRepository cancionRepository;


    public Album obtenerAlbum(Long id) {
        return albumRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró el album con el id: "+id));
    }

    public void eliminarAlbum(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Album no encontrado"));
        albumRepository.delete(album);
        //albumRepository.deleteById(id);
    }

    public Album guardarAlbum(Album album, Long idArtista, List<Long> idCanciones) {
        Artista artista = artistaRepository.findById(idArtista).orElseThrow(() ->
                new RuntimeException("No se encontró el artista con el id: "+idArtista+" al momento de guardar el album"));

        album.setArtista(artista);

        if (idCanciones != null) {
            album.setCanciones(cancionRepository.findAllById(idCanciones));
        }

        return albumRepository.save(album);
    }

    public void actualizarAlbum(Long idAlbum, Album albumActualizado, Long idArtista, List<Long> idCanciones) {
        Optional<Album> albumOptional = albumRepository.findById(idAlbum);

        Artista artista = artistaRepository.findById(idArtista).orElseThrow(() ->
                new RuntimeException("No se encontró el artista con el id: "+idArtista+" al momento de actualizar el album"));

        albumActualizado.setArtista(artista);

        if (idCanciones != null) {
            albumActualizado.setCanciones(cancionRepository.findAllById(idCanciones));
        }

        Album albumAcoplado = construirAlbumActualizacion(albumActualizado, albumOptional);
        albumRepository.save(albumAcoplado);
    }

    private Album construirAlbumActualizacion(Album albumActualizado, Optional<Album> albumOptional) {
        Album.AlbumBuilder albumBuilder = Album.builder();

        albumOptional.ifPresent(albumExistente -> {
            albumBuilder
                    .id(albumExistente.getId())
                    .titulo(albumActualizado.getTitulo())
                    .genero(albumActualizado.getGenero())
                    .fechaEstreno(albumActualizado.getFechaEstreno())
                    .artista(albumActualizado.getArtista())
                    .canciones(albumActualizado.getCanciones());
        });

        return albumBuilder.build();
    }
}