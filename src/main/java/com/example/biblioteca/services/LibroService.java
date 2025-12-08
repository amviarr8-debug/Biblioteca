package com.example.biblioteca.services;

import com.example.biblioteca.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {

    // Create / Update
    Libro save(Libro libro);

    // Read
    Optional<Libro> findById(int id);
    List<Libro> findAll();

    // Delete
    void deleteById(int id);
}
