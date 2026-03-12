package ar.dev.maxisandoval.webappmaxmusic.service;

import ar.dev.maxisandoval.webappmaxmusic.model.Rol;
import ar.dev.maxisandoval.webappmaxmusic.model.Usuario;
import ar.dev.maxisandoval.webappmaxmusic.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("loadUserByUsername: Usuario no encontrado: "+username);
        }

        return User.withUsername(usuario.getUsername())
                .password(usuario.getContrasena())
                .authorities(List.of(new SimpleGrantedAuthority(usuario.getRol().getDeclaringClass().getName())))
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

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}