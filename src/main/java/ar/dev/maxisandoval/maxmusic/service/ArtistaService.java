package ar.dev.maxisandoval.maxmusic.service;

import ar.dev.maxisandoval.maxmusic.model.Artista;
import ar.dev.maxisandoval.maxmusic.repository.ArtistaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ArtistaService {

    private ArtistaRepository artistaRepository;

    public Artista obtenerArtistaPorId(Long id) {
        return artistaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el artista con el id: "+id));
    }

    public List<Artista> listarArtistas() {
        return artistaRepository.findAll();
    }

    public Artista guardarArtista(Artista artista) {
        return artistaRepository.save(artista);
    }

}