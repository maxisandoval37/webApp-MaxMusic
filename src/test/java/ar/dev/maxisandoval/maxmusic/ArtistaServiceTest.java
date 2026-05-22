package ar.dev.maxisandoval.maxmusic;

import ar.dev.maxisandoval.maxmusic.model.Artista;
import ar.dev.maxisandoval.maxmusic.service.ArtistaService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ArtistaServiceTest {

    private final ArtistaService artistaService;
    private Artista artistaGuardado;


    @BeforeEach
    void setup() {
        //TODO DATA FAKER

        Artista artista = new Artista();
        artista.setNacionalidad("Argentina");
        artista.setFechaNacimiento(LocalDate.now());
        artista.setEmail("hola@email.com");

        artistaGuardado = artistaService.guardarArtista(artista);
    }

    @Test
    void testListarArtistas() {
        assertFalse(artistaService.listarArtistas().isEmpty());
    }

    @Test
    void testObtenerArtistaPorId() {
        Artista artista = artistaService.obtenerArtistaPorId(artistaGuardado.getId());

        assertNotNull(artista);
        assertEquals(artistaGuardado.getId(), artista.getId());
    }

}