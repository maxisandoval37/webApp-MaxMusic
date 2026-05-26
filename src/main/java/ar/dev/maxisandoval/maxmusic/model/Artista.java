package ar.dev.maxisandoval.maxmusic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Entity
@Data //toString, equals, hashcode, getters y setters
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