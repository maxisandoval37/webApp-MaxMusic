package ar.dev.maxisandoval.maxmusic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data //toString, equals, hashcode, getters y setters
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(min = 2, max = 30)
    private String nombre;

    @NotNull(message = "La duración no puede ser nula")
    private BigDecimal duracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    @ToString.Exclude
    private Album album;
}