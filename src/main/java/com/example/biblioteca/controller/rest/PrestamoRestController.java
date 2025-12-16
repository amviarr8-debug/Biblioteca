package com.example.biblioteca.controller.rest;

import com.example.biblioteca.dto.PrestamoAltaDTO;
import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.services.PrestamoService; // Usar la interfaz, no la implementación concreta
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
public class PrestamoRestController {


    private final PrestamoService prestamoService;


    // --- 1. GET: Listar todos los Préstamos (/api/prestamos) ---
    @GetMapping
    public ResponseEntity<List<Prestamo>> listarPrestamos() {
        List<Prestamo> prestamos = prestamoService.findAll();
        if (prestamos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        }
        return new ResponseEntity<>(prestamos, HttpStatus.OK); // Código 200
    }

    // --- 2. GET: Obtener Préstamo por ID (/api/prestamos/{id}) ---
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPrestamoPorId(@PathVariable int id) {
        return prestamoService.findById(id)
                .map(prestamo -> new ResponseEntity<>(prestamo, HttpStatus.OK)) // Código 200
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Código 404
    }

    // --- 3. POST: Dar de Alta Préstamo (APLICANDO LÓGICA DE NEGOCIO V3) ---
    // Endpoint para CREAR un nuevo préstamo
    @PostMapping
    public ResponseEntity<?> darAltaPrestamo(@Valid @RequestBody PrestamoAltaDTO prestamoDto) {
        try {
            Prestamo nuevoPrestamo = prestamoService.darAltaPrestamo(
                    prestamoDto.getSocioId(),
                    prestamoDto.getLibroId()
            );
            // Retorna 201 Created
            return new ResponseEntity<>(nuevoPrestamo, HttpStatus.CREATED);

        } catch (IllegalStateException e) {
            // Captura errores de negocio (Ej: Socio penalizado, Máximo de préstamos)
            // Retorna 409 Conflict con el mensaje de error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            // Captura errores de datos (Ej: Socio o Libro no encontrado)
            // Retorna 400 Bad Request
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // --- 4. PUT/PATCH: Devolución de Préstamo (Operación de Negocio) ---
    // Usamos PATCH ya que solo estamos modificando el estado de devolución de un recurso.
    @PatchMapping("/devolver/{id}")
    public ResponseEntity<?> devolverPrestamo(@PathVariable int id) {
        try {
            Prestamo prestamoDevuelto = prestamoService.devolverPrestamo(id);
            // Retorna 200 OK
            return new ResponseEntity<>(prestamoDevuelto, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Captura si el Préstamo no existe
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // Código 404
        }
        // No se incluye un PUT tradicional ya que no se espera editar un préstamo, solo devolverlo.
    }

    // --- 5. DELETE: Eliminar Préstamo (/api/prestamos/{id}) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable int id) {
        try {
            prestamoService.deleteById(id);
            // Devuelve código de estado 204 (No Content)
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Podría ser útil un logging aquí, pero se devuelve 404 si el ID no existe.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}