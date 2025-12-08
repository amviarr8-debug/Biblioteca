package com.example.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int socio_id;

    //propiedades de la columna
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String nombre;

    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String apellidos;

    //email (UK - Unique Key)
    @Size(max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fecha_nacimiento; // Usar LocalDate para tipos DATE de SQL

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fecha_alta;

    @Enumerated(EnumType.STRING) // Guarda el nombre del ENUM como String en la BD
    @Column(length = 15, nullable = false) // 'ACTIVO' o 'SANCIONADO'
    private EstadoSocio estado;

    @Column(name = "fecha_fin_penalizacion", nullable = true) // 'nullable = true' refleja el requisito 'nullable'
    private LocalDate fecha_fin_penalizacion;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Prestamo> prestamos = new HashSet<>();


    @PrePersist
    public void prePersist() {
        this.fecha_alta = LocalDate.now();
    } //se asigna la fecha actual cuando se crea el socio

}
