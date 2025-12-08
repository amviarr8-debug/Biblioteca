package com.example.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int libro_id;

    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String titulo;

    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String categoria;


    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String autor;


    @Size(max = 13)
    @Column(length = 13, unique = true, nullable = false)
    private String isbn;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Prestamo> prestamos = new HashSet<>();

    public Set<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Set<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }



    public int getLibro_id() {
        return libro_id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
