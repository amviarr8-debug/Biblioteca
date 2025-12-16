package com.example.biblioteca.controller.rest;

import com.example.biblioteca.model.Libro;
import com.example.biblioteca.services.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Usamos @RestController para indicar que todos los métodos devuelven datos (JSON/XML)
@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroRestController {

    private final LibroService libroService;

    // --- 1. GET: Listar todos los Libros (/api/libros) ---
    @GetMapping
    public ResponseEntity<List<Libro>> listarLibros() {
        List<Libro> libros = libroService.findAll();
        if (libros.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        }
        return new ResponseEntity<>(libros, HttpStatus.OK); // Código 200
    }

    // --- 2. GET: Obtener Libro por ID (/api/libros/{id}) ---
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable int id) {
        return libroService.findById(id)
                .map(libro -> new ResponseEntity<>(libro, HttpStatus.OK)) // Código 200
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Código 404
    }

    // --- 3. POST: Crear nuevo Libro (/api/libros) ---
    @PostMapping
    public ResponseEntity<Libro> crearLibro(@Valid @RequestBody Libro libro) {
        Libro nuevoLibro = libroService.save(libro);
        // Retorna 201 Created y la ubicación del nuevo recurso
        return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);
    }

    // --- 4. PUT: Modificar Libro existente (/api/libros/{id}) ---
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable int id, @Valid @RequestBody Libro libroActualizado) {

        // El PUT requiere que el recurso exista
        return libroService.findById(id)
                .map(libroExistente -> {
                    // Transferir solo los campos que pueden ser modificados
                    libroExistente.setTitulo(libroActualizado.getTitulo());
                    libroExistente.setAutor(libroActualizado.getAutor());
                    libroExistente.setCategoria(libroActualizado.getCategoria());
                    libroExistente.setIsbn(libroActualizado.getIsbn());

                    Libro actualizado = libroService.save(libroExistente);
                    return new ResponseEntity<>(actualizado, HttpStatus.OK); // Código 200
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Código 404
    }

    // --- 5. DELETE: Eliminar Libro (/api/libros/{id}) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarLibro(@PathVariable int id) {
        try {
            libroService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        } catch (IllegalArgumentException e) {
            // Manejo básico si el ID no existe (aunque el service debería manejarlo)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Código 404
        } catch (Exception e) {
            // Manejar posible error de clave foránea si el libro tiene préstamos activos
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Código 409
        }
    }
}
