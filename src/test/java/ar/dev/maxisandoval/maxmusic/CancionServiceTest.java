package ar.dev.maxisandoval.maxmusic;

import ar.dev.maxisandoval.maxmusic.model.*;
import ar.dev.maxisandoval.maxmusic.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CancionServiceTest extends BaseTest {

    private final CancionService cancionService;
    private final ArtistaService artistaService;
    private final AlbumService albumService;
    private Album albumGuardado;
    private Cancion cancionGuardada;

    @BeforeEach
    void setup() {
        Faker faker = new Faker();

        Artista artistaGuardado = artistaService.guardarArtista(
                Artista.builder()
                        .nacionalidad(faker.country().name().toLowerCase())
                        .fechaNacimiento(LocalDate.now().minusYears(faker.number().numberBetween(18,60)))
                        .email(faker.internet().emailAddress())
                        .build()
        );

        albumGuardado = albumService.guardarAlbum(
                Album.builder()
                        .titulo(faker.book().title())
                        .genero(faker.music().genre().toLowerCase())
                        .fechaEstreno(LocalDate.now().minusDays(faker.number().numberBetween(1, 5000)))
                        .build(),
                artistaGuardado.getId(),
                null
        );

        Cancion cancion = Cancion.builder()
                .nombre("Cancion " + faker.number().numberBetween(10, 99))
                .duracion(BigDecimal.valueOf(faker.number().numberBetween(1,1000)))
                .build();

        cancionGuardada = cancionService.guardarCancion(cancion, albumGuardado.getId());

        log.info("CancionServiceTest -> "+cancionGuardada);
    }

    @Test
    void guardarCancion() {
        assertNotNull(cancionGuardada.getId());
        assertNotNull(cancionGuardada.getAlbum());
        assertNotNull(cancionGuardada.getNombre());
        assertNotNull(cancionGuardada.getDuracion());
        assertEquals(albumGuardado.getId(), cancionGuardada.getAlbum().getId());
    }

    @Test
    void listarCanciones() {
        List<Cancion> canciones = cancionService.listarCanciones();
        assertFalse(canciones.isEmpty());
    }

    @Test
    void obtenerCancionPorId_ok() {
        Cancion cancion = cancionService.obtenerCancionPorId(cancionGuardada.getId());

        assertNotNull(cancion);
        assertEquals(cancionGuardada.getId(), cancion.getId());
        assertEquals(albumGuardado.getId(), cancion.getAlbum().getId());
    }

    @Test
    void testObtenerTestPorIdInexistente() {
        Long idInexistente = Long.MAX_VALUE;

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> cancionService.obtenerCancionPorId(idInexistente));
        assertTrue(ex.getMessage().contains("No se encontró la canción con el id:"));
    }

    @Test
    void eliminarCancion() {
        Long idAEliminar = cancionGuardada.getId();
        cancionService.eliminarCancion(idAEliminar);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> cancionService.obtenerCancionPorId(idAEliminar));
        assertTrue(ex.getMessage().contains("No se encontró la canción con el id:"));
    }
}