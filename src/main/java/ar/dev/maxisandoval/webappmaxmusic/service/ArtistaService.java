package ar.dev.maxisandoval.webappmaxmusic.service;

import ar.dev.maxisandoval.webappmaxmusic.model.Artista;
import ar.dev.maxisandoval.webappmaxmusic.repository.ArtistaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ArtistaService {

    private ArtistaRepository artistaRepository;

    public Artista obtenerArtistaPorId(Long id) {
        return artistaRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró el artista con el id: "+id));
    }

    public List<Artista> listarArtistas() {
        return artistaRepository.findAll();
    }

    public Artista guardarArtista(Artista artista) {
        return artistaRepository.save(artista);
    }
}