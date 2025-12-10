package com.example.biblioteca.repository;

import com.example.biblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Integer> {

    int countBySocioSocioIdAndFechaDevolucionIsNull(int socioId);
}
