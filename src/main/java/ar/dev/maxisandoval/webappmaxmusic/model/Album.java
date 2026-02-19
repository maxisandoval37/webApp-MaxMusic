package ar.dev.maxisandoval.webappmaxmusic.model;

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

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    @OneToMany(
            mappedBy = "album",
            cascade = CascadeType.ALL,
            orphanRemoval = true
            )
    private List<Cancion> canciones = new ArrayList<>();
}