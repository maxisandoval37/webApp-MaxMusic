package ar.dev.maxisandoval.maxmusic.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String titulo;
    private String genero;
    private LocalDate fechaEstreno;
    private Date fecha;

    @ManyToOne
    private Artista artista;

    @OneToMany
    private List<Cancion> canciones = new ArrayList<>();
}