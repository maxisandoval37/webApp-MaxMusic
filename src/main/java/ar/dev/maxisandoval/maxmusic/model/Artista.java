package ar.dev.maxisandoval.maxmusic.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private String email;

    @OneToMany
    private List<Album> albumesPublicados = new ArrayList<>();
}