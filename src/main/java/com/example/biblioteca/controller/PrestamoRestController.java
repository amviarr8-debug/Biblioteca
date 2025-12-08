package com.example.biblioteca.controller;
import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.services.PrestamoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
public class PrestamoRestController {

    private final PrestamoServiceImpl prestamoServiceImpl;


    // --- 1. Listado de Préstamos ---
    @GetMapping
    public List<Prestamo> listarPrestamos() {
        return prestamoServiceImpl.findAll();
    }

    // --- 2. Alta Básica de Préstamo (Crear) ---
    @PostMapping
    public ResponseEntity<Prestamo> crearPrestamo(@RequestBody Prestamo prestamo) {

        // Lógica Básica de Alta (sin la regla de los 2 días)
        prestamo.setFecha_prestamo(LocalDateTime.now());

        Prestamo nuevoPrestamo = prestamoServiceImpl.save(prestamo);
        // Devuelve el objeto creado con código de estado 201 (Created)
        return ResponseEntity.status(201).body(nuevoPrestamo);
    }

    // --- 3. Baja de Préstamo (Eliminar) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable int id) {
        prestamoServiceImpl.deleteById(id);
        // Devuelve código de estado 204 (No Content) para una eliminación exitosa
        return ResponseEntity.noContent().build();
    }
}
