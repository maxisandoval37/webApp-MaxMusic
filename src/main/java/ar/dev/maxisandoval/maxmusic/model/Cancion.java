package ar.dev.maxisandoval.maxmusic.model;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;
}