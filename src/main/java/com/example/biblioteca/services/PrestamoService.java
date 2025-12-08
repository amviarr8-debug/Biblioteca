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

    // Lógica de negocio: Dar de alta un préstamo (con la regla de los días)
    // Recibe el objeto con los IDs de socio y libro, pero SIN fechas.
    Prestamo darAltaPrestamo(Prestamo prestamo);
}
