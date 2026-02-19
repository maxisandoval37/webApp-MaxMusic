package ar.dev.maxisandoval.webappmaxmusic.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;

    private BigDecimal duracion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;
}