package com.example.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int presatmo_id;

    @Column(nullable = false)
    private LocalDateTime fecha_prestamo;
    @Column(nullable = false)
    private LocalDateTime fecha_limite;
    @Column(nullable = true)
    private LocalDateTime fecha_devolucion;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Socio socio;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Libro libro;
}