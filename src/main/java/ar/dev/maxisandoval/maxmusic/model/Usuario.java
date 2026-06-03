package ar.dev.maxisandoval.maxmusic.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data //toString, equals, hashcode, getters y setters
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String contrasena;
    private String nombre;
    private String apellido;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Artista artista;
}