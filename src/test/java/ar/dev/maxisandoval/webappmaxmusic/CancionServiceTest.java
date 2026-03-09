package ar.dev.maxisandoval.webappmaxmusic;

import ar.dev.maxisandoval.webappmaxmusic.model.*;
import ar.dev.maxisandoval.webappmaxmusic.repository.*;
import ar.dev.maxisandoval.webappmaxmusic.service.CancionService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CancionServiceTest {
    private final CancionService cancionService;
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;
    private Album albumGuardado;
    private Cancion cancionGuardada;

    @BeforeEach
    void setup() {
        Faker faker = new Faker();
        Artista artistaGuardado = artistaRepository.save(Artista.builder()
                .nacionalidad(faker.country().name().toLowerCase())
                .fechaNacimiento(LocalDate.now().minusYears(faker.number().numberBetween(18, 70)))
                .email(faker.internet().emailAddress())
                .build()
        );

        albumGuardado = albumRepository.save(Album.builder()
                .titulo(faker.book().title())
                .genero(faker.music().genre().toLowerCase())
                .fechaEstreno(LocalDate.now().minusDays(faker.number().numberBetween(1, 5000)))
                .artista(artistaGuardado)
                .build()
        );

        Cancion cancion = new Cancion();
        cancion.setNombre(faker.music().chord());//acorde
        cancion.setDuracion(BigDecimal.valueOf(faker.number().numberBetween(1, 1000)));
        cancion.setAlbum(albumGuardado);

        cancionGuardada = cancionService.guardarCancion(cancion, albumGuardado.getId());
    }

    @Test
    void guardarCancion_ok() {
        assertNotNull(cancionGuardada.getId());
        assertNotNull(cancionGuardada.getAlbum());
        assertEquals(albumGuardado.getId(), cancionGuardada.getAlbum().getId());
        assertNotNull(cancionGuardada.getNombre());
        assertNotNull(cancionGuardada.getDuracion());
    }

    @Test
    void listarCanciones_ok() {
        List<Cancion> canciones = cancionService.listarCanciones();
        assertFalse(canciones.isEmpty());
    }

    @Test
    void obtenerCancionPorId_ok() {
        Cancion cancion = cancionService.obtenerCancionPorId(cancionGuardada.getId());

        assertNotNull(cancion);
        assertEquals(cancionGuardada.getId(), cancion.getId());
        assertEquals(cancionGuardada.getNombre(), cancion.getNombre());
    }

    @Test
    void obtenerCancionPorId_inexistente_lanzaRuntime() {
        Long idInexistente = Long.MAX_VALUE;

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> cancionService.obtenerCancionPorId(idInexistente)
        );

        assertTrue(ex.getMessage().contains("No se encontró la canción con el id: " + idInexistente));
    }

    @Test
    void eliminarCancion_ok_y_luego_no_existe() {
        Long idAEliminar = cancionGuardada.getId();

        cancionService.eliminarCancion(idAEliminar);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> cancionService.obtenerCancionPorId(idAEliminar)
        );

        assertTrue(ex.getMessage().contains("No se encontró la canción con el id: " + idAEliminar));
    }
}