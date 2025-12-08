package com.example.biblioteca.services;

import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.repository.PrestamoRepositorio;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // Marca la clase como un componente de servicio de Spring
@RequiredArgsConstructor // Al tener ya Lombok genera el constructor para la inyección de dependencias
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepositorio prestamoRepository;

    @Override
    public Prestamo save(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public Optional<Prestamo> findById(int id) {
        return Optional.empty();
    }


    @Override
    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        prestamoRepository.deleteById(id);
    }

    // --- Implementación de la Lógica de Negocio ---

    @Override
    public Prestamo darAltaPrestamo(Prestamo prestamo) {

        // 1. Establecer la fecha de préstamo como la hora actual
        LocalDateTime fechaPrestamo = LocalDateTime.now();
        prestamo.setFecha_prestamo(fechaPrestamo);

        // 2. Aplicar la regla de negocio: Fecha límite = Fecha préstamo + 2 días
        LocalDateTime fechaLimite = fechaPrestamo.plusDays(2);
        prestamo.setFecha_limite(fechaLimite);

        // 3. Guardar el préstamo en la base de datos

        return prestamoRepository.save(prestamo);
    }
}
