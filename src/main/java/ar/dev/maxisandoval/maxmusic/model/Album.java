package ar.dev.maxisandoval.maxmusic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data //toString, equals, hashcode, getters y setters
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "El titulo no puede estar en blanco")
    @Size(min = 1, max = 50)
    private String titulo;

    @NotBlank(message = "El genero no puede estar en blanco")
    @Size(min = 1, max = 20)
    private String genero;

    @NotNull(message = "La fecha de estreno no puede ser nula")
    private LocalDate fechaEstreno;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    @OneToMany(
            mappedBy = "album",//fk
            cascade = CascadeType.ALL,//todas las operaciones de album, se propagan a canciones
            orphanRemoval = true//si una canción deja de pertenecer al álbum, Hibernate la elimina
    )
    @ToString.Exclude
    private List<Cancion> canciones = new ArrayList<>();
}