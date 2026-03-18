package ar.dev.maxisandoval.webappmaxmusic.controller;

import ar.dev.maxisandoval.webappmaxmusic.model.*;
import ar.dev.maxisandoval.webappmaxmusic.repository.UsuarioRepository;
import ar.dev.maxisandoval.webappmaxmusic.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class AlbumViewController {

    private final UsuarioRepository usuarioRepository;
    private final AlbumService albumService;
    private final ArtistaService artistaService;
    private final CancionService cancionService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/albumes")
    public String listarAlbumes(Model model) {
        List<Album> albumes;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info(username);
        Artista artista = usuarioRepository.findByUsername(username).getArtista();

        if (artista != null) {
            albumes = artista.getAlbumesPublicados();
        }
        else {
            albumes = albumService.listarAlbumes();
        }

        mostrarRolesUsuarioActual();

        model.addAttribute("albumes", albumes);
        model.addAttribute("userService", customUserDetailsService);

        return "listaAlbumes";
    }

    private void mostrarRolesUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            log.info("Rol actual: " + authority.getAuthority());
        }
    }

    @GetMapping("/agregarAlbum")
    public String mostrarFormularioNuevoAlbum(Model model) {
        model.addAttribute("canciones", cancionService.listarCanciones());
        model.addAttribute("usuariosConArtista", customUserDetailsService.listarUsuariosRegistradosConArtistas());
        model.addAttribute("album", new Album());

        return "agregarAlbumForm";
    }

    @PostMapping("/guardarAlbum")
    public String guardarAlbum(@ModelAttribute Album album, @RequestParam Long idArtista, @RequestParam(required = false) List<Long> idCanciones) {
        albumService.guardarAlbum(album, idArtista, idCanciones);

        return "redirect:/albumes";
    }

    @GetMapping("/actualizarAlbum/{id}")//actualizarAlbum/123
    public String mostrarFormularioActualizarAlbum(@PathVariable Long id, Model model) {
        model.addAttribute("album", albumService.obtenerAlbumPorId(id));
        model.addAttribute("canciones", cancionService.listarCanciones());
        model.addAttribute("usuariosConArtista", customUserDetailsService.listarUsuariosRegistradosConArtistas());

        return "actualizarAlbumForm";
    }

    @PostMapping("/actualizarAlbum/{idAlbum}")
    public String actualizarAlbum(@PathVariable long idAlbum, @ModelAttribute Album albumActualizado,  @RequestParam Long idArtista, @RequestParam(required = false) List<Long> idCanciones) {
        albumService.actualizarAlbum(idAlbum, albumActualizado, idArtista, idCanciones);

        return "redirect:/albumes";
    }

    @GetMapping("/eliminarAlbum/{id}")
    public String eliminarAlbum(@PathVariable Long id) {
        albumService.eliminarAlbum(id);

        return "redirect:/albumes";
    }

}