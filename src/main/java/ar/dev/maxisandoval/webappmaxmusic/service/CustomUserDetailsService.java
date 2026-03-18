package ar.dev.maxisandoval.webappmaxmusic.service;

import ar.dev.maxisandoval.webappmaxmusic.model.Artista;
import ar.dev.maxisandoval.webappmaxmusic.model.Rol;
import ar.dev.maxisandoval.webappmaxmusic.model.Usuario;
import ar.dev.maxisandoval.webappmaxmusic.repository.AlbumRepository;
import ar.dev.maxisandoval.webappmaxmusic.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final AlbumRepository albumRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("loadUserByUsername: Usuario no encontrado: "+username);
        }

        return User.withUsername(usuario.getUsername())
                .password(usuario.getContrasena())
                .authorities(List.of(new SimpleGrantedAuthority(usuario.getRol().name())))
                .build();
    }

    public Usuario guardarUsuario(Usuario usuario) {

        Usuario usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername());

        if (usuarioExistente != null) {
            throw new DataIntegrityViolationException("El usuario ya se encuentra registrado!");
        }

        usuario.setRol(Rol.ROL_LECTURA);
        usuario.setContrasena(passwordEncoder().encode(usuario.getContrasena()));

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        Optional<Usuario> usuarioActual = usuarioRepository.findById(id);
        String usernameActual = SecurityContextHolder.getContext().getAuthentication().getName();

        if (usuarioActual.isPresent()) {
            //Sesión activa: No permitimos eliminar el usuario que tiene la sesión actual abierta
            if (usernameActual.equals(usuarioActual.get().getUsername())) {
                throw new IllegalArgumentException("Se se puede eliminar el usuario actualmente autenticado");
            }

            //Es artista: Borramos los albumes asociados
            if (usuarioActual.get().getArtista() != null) {
                albumRepository.deleteByArtista(usuarioActual.get().getArtista());
            }
        }

        usuarioRepository.deleteById(id);
    }

    public List<Usuario> listarUsuariosRegistrados() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarUsuariosRegistradosConArtistas() {
        return usuarioRepository.findAllByArtistaIsNotNull();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(()
                -> new UsernameNotFoundException("No se encontró el usuario con id: "+id));
    }

    public Usuario obtenerUsuarioPorArtista(Artista artista) {
        return usuarioRepository.findByArtista(artista);
    }

    public void actualizarRolUsuario(Long id, String nuevoRol) {
        Usuario usuario = obtenerUsuarioPorId(id);

        if (usuario == null) {
            throw new UsernameNotFoundException("actualizarRolUsuario: Usuario no encontrado");
        }

        usuario.setRol(Rol.valueOf(nuevoRol));
        usuarioRepository.save(usuario);
    }

    public void actualizarRolUsuarioArtista(Long id, Artista artista) {
        Usuario usuario = obtenerUsuarioPorId(id);

        if (usuario == null) {
            throw new UsernameNotFoundException("actualizarRolUsuario: Usuario no encontrado");
        }

        usuario.setRol(Rol.ROL_ARTISTA);
        usuario.setArtista(artista);
        usuarioRepository.save(usuario);
    }

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}