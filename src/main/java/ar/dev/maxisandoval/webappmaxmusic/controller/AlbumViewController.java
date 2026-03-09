package ar.dev.maxisandoval.webappmaxmusic.controller;

import ar.dev.maxisandoval.webappmaxmusic.model.Album;
import ar.dev.maxisandoval.webappmaxmusic.service.AlbumService;
import ar.dev.maxisandoval.webappmaxmusic.service.ArtistaService;
import ar.dev.maxisandoval.webappmaxmusic.service.CancionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@AllArgsConstructor
public class AlbumViewController {

    private final AlbumService albumService;
    private final CancionService cancionService;
    ArtistaService artistaService;

    @GetMapping("/albumes")
    public String listarAlbumes(Model model) {
        model.addAttribute("albumes", albumService.listarAlbumes());

        return "listaAlbumes";
    }

    @GetMapping("/agregarAlbum")
    public String mostrarFormularioNuevoAlbum(Model model) {
        model.addAttribute("canciones", cancionService.listarCanciones());
        model.addAttribute("artistas", artistaService.listarArtistas());
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
        model.addAttribute("artistas", artistaService.listarArtistas());

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