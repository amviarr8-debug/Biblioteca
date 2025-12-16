package com.example.biblioteca.services;

import com.example.biblioteca.model.Prestamo;

import java.util.List;
import java.util.Optional;

public interface PrestamoService {
    // Operaciones básicas CRUD
    Prestamo save(Prestamo prestamo);
    Optional<Prestamo> findById(int id);
    List<Prestamo> findAll();
    void deleteById(int id);
    Prestamo darAltaPrestamo(Prestamo prestamo);
    Prestamo devolverPrestamo(int prestamoId);
    Prestamo darAltaPrestamo(Integer socioId, Integer libroId);

}
