package ar.dev.maxisandoval.maxmusic;

import ar.dev.maxisandoval.maxmusic.model.Artista;
import ar.dev.maxisandoval.maxmusic.service.ArtistaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import net.datafaker.Faker;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ArtistaServiceTest {

    private final ArtistaService artistaService;
    private Artista artistaGuardado;

    @BeforeEach
    void setup() {
        Faker faker = new Faker();

        Artista artista = new Artista();
        artista.setNacionalidad(faker.country().name().toLowerCase());
        artista.setFechaNacimiento(LocalDate.now().minusYears(faker.number().numberBetween(18,60)));
        artista.setEmail(faker.internet().emailAddress());

        artistaGuardado = artistaService.guardarArtista(artista);
        System.out.println(artista.getNacionalidad());
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

    @Test
    void testObtenerArtistaPorIdInexistente() {
        Long idInexistente = Long.MAX_VALUE;

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> artistaService.obtenerArtistaPorId(idInexistente));
        assertTrue(ex.getMessage().contains("No se encontró el artista con el id:"));
    }

}