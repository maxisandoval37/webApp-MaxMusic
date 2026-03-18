package ar.dev.maxisandoval.webappmaxmusic.controller;

import ar.dev.maxisandoval.webappmaxmusic.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ArtistaViewController {

    private final ArtistaService artistaService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/artistas")
    public String listarArtistas(Model model) {
        model.addAttribute("artistas", artistaService.listarArtistas());
        model.addAttribute("userService", customUserDetailsService);

        return "listaArtistas";
    }
}