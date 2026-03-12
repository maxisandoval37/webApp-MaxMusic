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
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "La nacionalidad no puede estar en blanco")
    @Size(min = 1, max = 40)
    private String nacionalidad;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El email no puede estar en blanco")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "El formato del email no es válido")
    private String email;

    @OneToMany(mappedBy = "artista", fetch = FetchType.EAGER)
    private List<Album> albumesPublicados = new ArrayList<>();

    @OneToOne(mappedBy = "artista", cascade = CascadeType.ALL)
    private Usuario usuario;
}