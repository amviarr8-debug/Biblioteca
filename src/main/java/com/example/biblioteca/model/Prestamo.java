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
        private int presatmoId;

        @Column(nullable = false)
        private LocalDateTime fechaPrestamo;
        @Column(nullable = false)
        private LocalDateTime fechaLimite;
        @Column(nullable = true)
        private LocalDateTime fechaDevolucion;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        private Socio socio;


        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        private Libro libro;
    }