package ar.dev.maxisandoval.webappmaxmusic;

import ar.dev.maxisandoval.webappmaxmusic.model.Artista;
import ar.dev.maxisandoval.webappmaxmusic.service.ArtistaService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ArtistaServiceTest extends BaseTest {

    private final ArtistaService artistaService;
    private Artista artistaGuardado;

    @BeforeEach
    void setup() {
        Faker faker = new Faker();

        Artista artista = new Artista();
        artista.setNacionalidad(faker.country().name().toLowerCase());
        artista.setFechaNacimiento(LocalDate.now().minusYears(faker.number().numberBetween(18, 60)));

        artista.setEmail(faker.internet().emailAddress());
        artistaGuardado = artistaService.guardarArtista(artista);
    }

    /*@Test TODO arreglar
    void testGuardarArtista() {
        assertNotNull(artistaGuardado.getId());
        assertNotNull(artistaGuardado.getEmail());
        assertNotNull(artistaGuardado.getFechaNacimiento());
        assertNotNull(artistaGuardado.getNacionalidad());
    }*/

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
    void testObtenerArtistaPorId_inexistente() {
        Long idInexistente = Long.MAX_VALUE;

        RuntimeException ex = assertThrows(RuntimeException.class, () -> artistaService.obtenerArtistaPorId(idInexistente));
        assertTrue(ex.getMessage().contains("No se encontró el artista con el id: "));
    }

}
