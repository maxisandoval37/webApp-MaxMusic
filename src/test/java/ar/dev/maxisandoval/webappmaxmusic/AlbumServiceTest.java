package ar.dev.maxisandoval.webappmaxmusic;

import ar.dev.maxisandoval.webappmaxmusic.model.*;
import ar.dev.maxisandoval.webappmaxmusic.service.*;
import jakarta.transaction.Transactional;
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
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AlbumServiceTest {
    private final AlbumService albumService;
    private final ArtistaService artistaService;
    private final CancionService cancionService;
    private Faker faker;
    private Artista artistaGuardado;
    private Album albumBase;

    @BeforeEach
    void setup() {
        faker = new Faker();

        artistaGuardado = artistaService.guardarArtista(
                Artista.builder()
                        .nacionalidad(faker.country().name().toLowerCase())
                        .fechaNacimiento(LocalDate.now().minusYears(faker.number().numberBetween(18, 70)))
                        .email(faker.internet().emailAddress())
                        .build()
        );

        albumBase = Album.builder()
                .titulo(faker.book().title())
                .genero(faker.music().genre().toLowerCase())
                .fechaEstreno(LocalDate.now().minusDays(faker.number().numberBetween(1, 5000)))
                .build(); // artista lo setea el service
    }

    @Test
    void guardarAlbum_sinCanciones_ok() {
        Album albumGuardado = albumService.guardarAlbum(albumBase, artistaGuardado.getId(), null);

        assertNotNull(albumGuardado.getId());
        assertEquals(albumBase.getTitulo(), albumGuardado.getTitulo());
        assertEquals(albumBase.getGenero(), albumGuardado.getGenero());
        assertEquals(artistaGuardado.getId(), albumGuardado.getArtista().getId());
    }

    @Test
    void listarAlbumes_ok() {
        albumService.guardarAlbum(albumBase, artistaGuardado.getId(), null);
        assertFalse(albumService.listarAlbumes().isEmpty());
    }

    @Test
    void obtenerAlbumPorId_ok() {
        Album guardado = albumService.guardarAlbum(albumBase, artistaGuardado.getId(), null);
        Album album = albumService.obtenerAlbumPorId(guardado.getId());

        assertNotNull(album);
        assertEquals(guardado.getId(), album.getId());
        assertEquals(guardado.getTitulo(), album.getTitulo());
    }

    @Test
    void actualizarAlbum_sinCanciones_ok() {
        Album guardado = albumService.guardarAlbum(albumBase, artistaGuardado.getId(), null);

        Album albumActualizada = Album.builder()
                .titulo(faker.book().title())
                .genero(faker.music().genre().toLowerCase())
                .fechaEstreno(LocalDate.now().minusDays(faker.number().numberBetween(1, 5000)))
                .build(); // artista lo setea el service

        albumService.actualizarAlbum(guardado.getId(), albumActualizada, artistaGuardado.getId(), null);

        Album despues = albumService.obtenerAlbumPorId(guardado.getId());

        assertEquals(albumActualizada.getGenero(), despues.getGenero());
        assertEquals(albumActualizada.getTitulo(), despues.getTitulo());
        assertEquals(artistaGuardado.getId(), despues.getArtista().getId());
    }

    @Test
    void actualizarAlbum_conCanciones_ok() {
        Album guardado = albumService.guardarAlbum(albumBase, artistaGuardado.getId(), null);

        Cancion c1 = cancionService.guardarCancion(crearCancionValida(guardado), 1L);
        Cancion c2 = cancionService.guardarCancion(crearCancionValida(guardado), 1L);

        Album albumActualizada = Album.builder()
                .titulo(guardado.getTitulo())
                .genero(guardado.getGenero())
                .fechaEstreno(guardado.getFechaEstreno())
                .build();

        albumService.actualizarAlbum(
                guardado.getId(),
                albumActualizada,
                artistaGuardado.getId(),
                List.of(c1.getId(), c2.getId())
        );

        Album despues = albumService.obtenerAlbumPorId(guardado.getId());

        assertNotNull(despues.getCanciones());
        assertTrue(despues.getCanciones().size() >= 2);
    }

    @Test
    void eliminarAlbum_ok_y_luego_no_existe() {
        Album guardado = albumService.guardarAlbum(albumBase, artistaGuardado.getId(), null);
        Long idEliminar = guardado.getId();

        albumService.eliminarAlbum(idEliminar);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> albumService.obtenerAlbumPorId(idEliminar)
        );

        assertTrue(ex.getMessage().contains("No se encontró el album con el id: " + idEliminar));
    }

    private Cancion crearCancionValida(Album album) {
        return new Cancion(
                null,
                faker.music().instrument() + " " + faker.number().digits(2),
                BigDecimal.valueOf(faker.number().numberBetween(1, 1000)),
                album
        );
    }
}
