package ar.dev.maxisandoval.webappmaxmusic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data //toString, equals, hashcode, getters y setter
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
    @Size(min = 3, max = 20)
    private String genero;

    @NotNull(message = "La fecha de estreno no puede ser nula")
    private LocalDate fechaEstreno;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    @NotNull(message = "El artista no puede ser nulo")
    private Artista artista;

    @OneToMany(
            mappedBy = "album",
            cascade = CascadeType.ALL,
            orphanRemoval = true
            )
    @ToString.Exclude
    @Builder.Default
    private List<Cancion> canciones = new ArrayList<>();
}