package ar.dev.maxisandoval.maxmusic.service;

import ar.dev.maxisandoval.maxmusic.model.*;
import ar.dev.maxisandoval.maxmusic.repository.AlbumRepository;
import ar.dev.maxisandoval.maxmusic.repository.ArtistaRepository;
import ar.dev.maxisandoval.maxmusic.repository.CancionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlbumService {

    private AlbumRepository albumRepository;
    private ArtistaRepository artistaRepository;
    private CancionRepository cancionRepository;

    public Album obtenerAlbumPorId(Long id) {
        return albumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el album con el id: "+id));
    }

    public List<Album> listarAlbumes() {
        return albumRepository.findByAllOrderByTituloIgnoreCaseAsc();
    }

    public void eliminarAlbum(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No se encontró el album con el id:"+id+" al momento de eliminar"));

        albumRepository.delete(album);
    }

    //-un Album tiene muchas canciones
    //-una cancion tiene un solo Album
    //Cuando guardamos un album, seleccionando una canción de otro album, se reemplaza
    @Transactional
    public Album guardarAlbum(Album album, Long idArtista, List<Long> idCanciones) {
        Artista artista = artistaRepository.findById(idArtista).orElseThrow(() ->
                new EntityNotFoundException("No se encontró el artista con el id: "+idArtista+" al momento de guardar un album"));

        album.setArtista(artista);

        if (idCanciones != null && !idCanciones.isEmpty()) {
            List<Cancion> canciones = cancionRepository.findAllById(idCanciones);

            for (Cancion cancion : canciones) {
                cancion.setAlbum(album);
                album.getCanciones().add(cancion);
            }
        }

        return albumRepository.save(album);
    }

    //Conserva las canciones que tenia, y agrega las nuevas seleccionadas. Las canciones seleccionadas,
    //si estan en uso por otro album, desaparecen del segundo album (las "mueve")
    public void actualizarAlbum(Long idAlbum, Album albumActualizado, Long idArtista, List<Long> idCanciones){
        Album albumExistente = albumRepository.findById(idAlbum).orElseThrow(() ->
                new EntityNotFoundException("No se encontró el álbum con el id: "+idAlbum+" al momento de actualizar"));

        Artista artista = artistaRepository.findById(idAlbum).orElseThrow(() ->
                new EntityNotFoundException("No se encontró el artista con el id: "+idArtista+" al momento de actualizar"));

        albumExistente.setTitulo(albumActualizado.getTitulo());
        albumExistente.setGenero(albumActualizado.getGenero());
        albumExistente.setFechaEstreno(albumActualizado.getFechaEstreno());
        albumExistente.setArtista(artista);

        if (idCanciones != null && !idCanciones.isEmpty()) {
            List<Cancion> canciones = cancionRepository.findAllById(idCanciones);
            for (Cancion cancion : canciones) {
                if (!albumExistente.getCanciones().contains(cancion)) {
                    cancion.setAlbum(albumExistente);
                    albumExistente.getCanciones().add(cancion);
                }
            }
        }

        albumRepository.save(albumExistente);
    }
}