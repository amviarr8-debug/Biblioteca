package com.example.biblioteca.services;

import com.example.biblioteca.model.EstadoSocio;
import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.model.Socio;
import com.example.biblioteca.repository.PrestamoRepositorio;
import com.example.biblioteca.repository.SocioRespositorio;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service // Marca la clase como un componente de servicio de Spring
@RequiredArgsConstructor // Al tener ya Lombok genera el constructor para la inyección de dependencias
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepositorio prestamoRepository;
    private final SocioRespositorio socioRepository; // Necesario para penalizaciones
    private static final int MAX_PRESTAMOS_ACTIVOS = 3;
    private static final int DURACION_PRESTAMO_DIAS = 2;

    @Override
    public Prestamo save(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public Optional<Prestamo> findById(int id) {
        return prestamoRepository.findById(id);
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

        // Recuperamos el socio completo de la BD para asegurar que tenemos sus fechas de penalización reales
        Socio socio = socioRepository.findById(prestamo.getSocio().getSocioId())
                .orElseThrow(() -> new IllegalArgumentException("Socio no encontrado"));

        // Bloqueo por Penalización ---
        if (estaPenalizado(socio)) {
            // !!!!!! Crearemos en siguientes versiones una excepción personalizada aquí.
            // Tambien que si la lanza abra un cuadro de alerta.
            throw new IllegalStateException("El socio está penalizado hasta: " + socio.getFechaFinPenalizacion());
        }

        // Bloqueo por Máximo de Préstamos ---
        int prestamosActivos = prestamoRepository.countBySocioSocioIdAndFechaDevolucionIsNull(socio.getSocioId());

        if (prestamosActivos >= MAX_PRESTAMOS_ACTIVOS) {
            // !!!!!!!Crearemos en siguientes versiones una excepción personalizada aquí.
            throw new IllegalStateException("El socio ya tiene el máximo de " + MAX_PRESTAMOS_ACTIVOS + " préstamos activos.");
        }

        //Cálculo de Fecha Límite y Guardado ---

        LocalDateTime fechaPrestamo = LocalDateTime.now();

        prestamo.setFechaPrestamo(fechaPrestamo);
        // Fecha limite de 2 días
        prestamo.setFechaLimite(fechaPrestamo.plusDays(DURACION_PRESTAMO_DIAS));
        prestamo.setSocio(socio); // Asignamos el socio completo y gestionado

        return prestamoRepository.save(prestamo);
    }

    // Metodo auxiliar para comprobar la penalización

    private boolean estaPenalizado(Socio socio) {
        LocalDate finPenalizacion = socio.getFechaFinPenalizacion();

        // Si no hay fecha de fin o la fecha ya pasó, NO está penalizado.
        if (finPenalizacion == null) {
            return false;
        }

        // Comprueba si la fecha de fin de penalización.
        // //Si la fecha es posterior a la fecha actual si está penalizado true sino devolverá false.
        return finPenalizacion.isAfter(LocalDate.now());
    }

    // -------------- Logica de devolucion  o calculo de penalizacion-------------

    public Prestamo devolverPrestamo(int prestamoId) {

        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        // Marca la devolución
        LocalDateTime fechaDevolucion = LocalDateTime.now();
        prestamo.setFechaDevolucion(fechaDevolucion);

        // Calcula el Retraso y Penalización
        if (fechaDevolucion.isAfter(prestamo.getFechaLimite())) {

            // Cálculo de días de retraso sin tener en cuenta hora ni nada
             long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaLimite().toLocalDate(), fechaDevolucion.toLocalDate());

            // Penalizacion del socio durante 2 días por cada dia de retraso y lo aplicamos
            long diasPenalizacion = diasRetraso * 2;

            aplicarPenalizacion(prestamo.getSocio(), diasPenalizacion);
        }

        return prestamoRepository.save(prestamo);
    }

    // Metodo auxiliar para aplicar la penalización y actualizar el Socio
    private void aplicarPenalizacion(Socio socio, long diasPenalizacion) {

        // La penalización se añade a la fecha actual o a la penalización existente
        LocalDate inicioPenalizacion = socio.getFechaFinPenalizacion() != null
                && socio.getFechaFinPenalizacion().isAfter(LocalDate.now())
                ? socio.getFechaFinPenalizacion()
                : LocalDate.now();

        LocalDate nuevaFechaFin = inicioPenalizacion.plusDays(diasPenalizacion);

        socio.setEstado(EstadoSocio.SANCIONADO);
        socio.setFechaFinPenalizacion(nuevaFechaFin);

        socioRepository.save(socio); // Actualiza el socio en la BD
    }

}
