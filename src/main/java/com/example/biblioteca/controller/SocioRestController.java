package com.example.biblioteca.controller;

import com.example.biblioteca.model.Socio;
import com.example.biblioteca.services.SocioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/socios")
@RequiredArgsConstructor
public class SocioRestController {

    private final SocioService socioService;

    // --- 1. GET: Listar todos los Socios (/api/socios) ---
    @GetMapping
    public ResponseEntity<List<Socio>> listarSocios() {
        List<Socio> socios = socioService.findAll();
        if (socios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        }
        return new ResponseEntity<>(socios, HttpStatus.OK); // Código 200
    }

    // --- 2. GET: Obtener Socio por ID (/api/socios/{id}) ---
    @GetMapping("/{id}")
    public ResponseEntity<Socio> obtenerSocioPorId(@PathVariable int id) {
        return socioService.findById(id)
                .map(socio -> new ResponseEntity<>(socio, HttpStatus.OK)) // Código 200
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Código 404
    }

    // --- 3. POST: Crear nuevo Socio (/api/socios) ---
    @PostMapping
    public ResponseEntity<Socio> crearSocio(@Valid @RequestBody Socio socio) {
        Socio nuevoSocio = socioService.save(socio);
        // Retorna 201 Created
        return new ResponseEntity<>(nuevoSocio, HttpStatus.CREATED);
    }

    // --- 4. PUT: Modificar Socio existente (/api/socios/{id}) ---
    @PutMapping("/{id}")
    public ResponseEntity<Socio> actualizarSocio(@PathVariable int id, @Valid @RequestBody Socio socioActualizado) {

        // El PUT requiere que el recurso exista
        return socioService.findById(id)
                .map(socioExistente -> {
                    // Transferir solo los campos que pueden ser modificados por un PUT
                    socioExistente.setNombre(socioActualizado.getNombre());
                    socioExistente.setApellidos(socioActualizado.getApellidos());
                    socioExistente.setEmail(socioActualizado.getEmail());
                    socioExistente.setFechaNacimiento(socioActualizado.getFechaNacimiento());

                    // Nota: 'estado' y 'fechaFinPenalizacion' son gestionados por el sistema (Préstamos),
                    // no se actualizan directamente desde aquí.

                    Socio actualizado = socioService.save(socioExistente);
                    return new ResponseEntity<>(actualizado, HttpStatus.OK); // Código 200
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Código 404
    }

    // --- 5. DELETE: Eliminar Socio (/api/socios/{id}) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarSocio(@PathVariable int id) {
        try {
            // Recordatorio: La lógica para borrar los préstamos asociados (cascade=ALL, orphanRemoval=true)
            // debe estar activa en la entidad Socio para que este DELETE funcione.
            socioService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        } catch (Exception e) {
            // Se usa el 409 Conflict si hay una restricción de clave foránea activa (aunque orphanRemoval debería prevenirlo)
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Código 409
        }
    }
}
