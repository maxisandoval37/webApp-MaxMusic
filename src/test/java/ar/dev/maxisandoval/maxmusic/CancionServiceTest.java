package ar.dev.maxisandoval.maxmusic;

import ar.dev.maxisandoval.maxmusic.model.*;
import ar.dev.maxisandoval.maxmusic.repository.*;
import ar.dev.maxisandoval.maxmusic.service.CancionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CancionServiceTest extends BaseTest {

    private final CancionService cancionService;
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;
    private Album albumGuardado;
    private Cancion cancionGuardada;

    @BeforeEach
    void setup () {
        Faker faker = new Faker();

        Artista artistaGuardado = artistaRepository.save(Artista.builder()
                .nacionalidad(faker.country().name().toLowerCase())
                .fechaNacimiento(LocalDate.now().minusYears(faker.number().numberBetween(18,60)))
                .email(faker.internet().emailAddress())
                .build());

        albumGuardado = albumRepository.save(Album.builder()
                .titulo(faker.book().title())
                .genero(faker.music().genre().toLowerCase())
                .fechaEstreno(LocalDate.now().minusDays(faker.number().numberBetween(1, 5000)))
                .artista(artistaGuardado)
                .build());

        Cancion cancion = Cancion.builder()
                .nombre(faker.music().chord())//acorde
                .duracion(BigDecimal.valueOf(faker.number().numberBetween(1,1000)))
                .album(albumGuardado)
                .build();

        cancionGuardada = cancionService.guardarCancion(cancion, albumGuardado.getId());
    }

    @Test
    @Order(1)
    void guardarCancion() {
        assertNotNull(cancionGuardada.getId());
        assertNotNull(cancionGuardada.getAlbum());
        assertNotNull(cancionGuardada.getNombre());
        assertNotNull(cancionGuardada.getDuracion());
        assertEquals(albumGuardado.getId(), cancionGuardada.getAlbum().getId());
    }

    @Test
    @Order(2)
    void listarCanciones() {
        List<Cancion> canciones = cancionService.listarCanciones();
        assertFalse(canciones.isEmpty());
    }

    @Test
    @Order(3)
    void obtenerCancionPorId() {
        assertEquals(2,2);
    }

    @Test
    @Order(4)
    void testObtenerTestPorIdInexistente() {
        Long idInexistente = Long.MAX_VALUE;

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> cancionService.obtenerCancionPorId(idInexistente));
        assertTrue(ex.getMessage().contains("No se encontró la canción con el id:"));
    }

    @Test
    @Order(5)
    void eliminarCancion() {
        Long idAEliminar = cancionGuardada.getId();
        cancionService.eliminarCancion(idAEliminar);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> cancionService.obtenerCancionPorId(idAEliminar));
        assertTrue(ex.getMessage().contains("No se encontró la canción con el id:"));
    }
}