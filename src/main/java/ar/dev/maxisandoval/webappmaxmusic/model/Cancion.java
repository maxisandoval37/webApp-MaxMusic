package ar.dev.maxisandoval.webappmaxmusic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data //toString, equals, hashcode, getters y setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "El nommbre no puede estar en blanco")
    @Size(min = 2, max = 30)
    private String nombre;

    @NotNull(message = "La duración no puede ser nula")
    private BigDecimal duracion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    @ToString.Exclude
    @NotNull(message = "El album no puede ser nulo")
    private Album album;
}