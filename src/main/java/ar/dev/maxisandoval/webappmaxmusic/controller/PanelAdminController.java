package ar.dev.maxisandoval.webappmaxmusic.controller;

import ar.dev.maxisandoval.webappmaxmusic.model.Artista;
import ar.dev.maxisandoval.webappmaxmusic.model.Rol;
import ar.dev.maxisandoval.webappmaxmusic.service.ArtistaService;
import ar.dev.maxisandoval.webappmaxmusic.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class PanelAdminController {

    private final CustomUserDetailsService customUserDetailsService;
    private final ArtistaService artistaService;

    @GetMapping("/gestorRoles")
    public String gestorRoles(Model model) {
        model.addAttribute("usuarios", customUserDetailsService.listarUsuariosRegistrados());

        return "gestorRoles";
    }

    @GetMapping("/actualizarRolUsuario/{id}")
    public String mostrarFormularioActualizarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", customUserDetailsService.obtenerUsuarioPorId(id));

        return "actualizarRolUsuarioForm";
    }

    @PostMapping("/actualizarRolUsuario/{id}")
    public String actualizaRolUsuario(@PathVariable Long id,
                                      @RequestParam(required = false) String rol,
                                      @RequestParam(required = false) String nacionalidad,
                                      @RequestParam(required = false) String email,
                                      @RequestParam(required = false) LocalDate fechaNacimiento) {

        if (rol.equals(Rol.ROL_ARTISTA.name()) && nacionalidad != null && email != null){
            Artista artistaNuevo = new Artista();
            artistaNuevo.setNacionalidad(nacionalidad);
            artistaNuevo.setEmail(email);
            artistaNuevo.setFechaNacimiento(fechaNacimiento);
            artistaNuevo.setUsuario(customUserDetailsService.obtenerUsuarioPorId(id));

            Artista artistaGuardado = artistaService.guardarArtista(artistaNuevo);
            customUserDetailsService.actualizarRolUsuarioArtista(id, artistaGuardado);
        }


        customUserDetailsService.actualizarRolUsuario(id, rol);
        return "redirect:/gestorRoles";
    }

    @GetMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        customUserDetailsService.eliminarUsuario(id);
        return "redirect:/gestorRoles";
    }
}