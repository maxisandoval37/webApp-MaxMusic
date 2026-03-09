package ar.dev.maxisandoval.webappmaxmusic.controller;

import ar.dev.maxisandoval.webappmaxmusic.model.Cancion;
import ar.dev.maxisandoval.webappmaxmusic.service.AlbumService;
import ar.dev.maxisandoval.webappmaxmusic.service.CancionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class CancionViewController {

    private final CancionService cancionService;
    private final AlbumService albumService;

    @GetMapping("/canciones")
    public String listarCanciones(Model model) {
        model.addAttribute("canciones", cancionService.listarCanciones());

        return "listaCanciones";
    }

    @GetMapping("/agregarCancion")
    public String mostrarFormularioNuevaCancion(Model model) {
        model.addAttribute("cancion", new Cancion());
        model.addAttribute("albumes", albumService.listarAlbumes());

        return "agregarCancionForm";
    }

    @PostMapping("/guardarCancion")
    public String guardarCancion(@ModelAttribute Cancion cancion, @RequestParam Long albumId) {
        cancionService.guardarCancion(cancion, albumId);
        return "redirect:/canciones";
    }
}