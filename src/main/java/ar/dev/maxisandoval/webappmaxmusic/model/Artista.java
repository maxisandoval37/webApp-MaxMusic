package ar.dev.maxisandoval.webappmaxmusic.model;

import jakarta.persistence.*;
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

    private String nacionalidad;

    private LocalDate fechaNacimiento;

    private String email;

    @OneToMany(mappedBy = "artista", fetch = FetchType.EAGER)
    private List<Album> albumesPublicados = new ArrayList<>();
}