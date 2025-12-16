package com.example.biblioteca.controller.web;

import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.services.LibroService;
import com.example.biblioteca.services.PrestamoService;
import com.example.biblioteca.services.SocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation; // Para documentar métodos
import io.swagger.v3.oas.annotations.Parameter; // Para documentar parámetros
import io.swagger.v3.oas.annotations.tags.Tag; // Para agrupar endpoints

@Controller
@RequestMapping("/web/prestamos")
@Tag(name = "Web - Préstamos (Thymeleaf)", description = "EndPoints para la interfaz de usuario web de Préstamos.")
public class PrestamoWebController {

    private final PrestamoService prestamoService;
    private final SocioService socioService;
    private final LibroService libroService;

    @Autowired
    public PrestamoWebController(PrestamoService prestamoService, SocioService socioService, LibroService libroService) {
        this.prestamoService = prestamoService;
        this.socioService = socioService;
        this.libroService = libroService;
    }

    // --- 1. Listado de Préstamos ---
    @Operation(summary = "Muestra el listado de todos los préstamos.",
            description = "Devuelve la plantilla HTML con la lista completa de préstamos para el frontend web.")
    @GetMapping
    public String listarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.findAll());
        return "listado-prestamos";
    }

    // --- 2. Mostrar Formulario de Alta ---
    @Operation(summary = "Muestra el formulario para registrar un nuevo préstamo.",
            description = "Carga las listas de socios y libros activos para rellenar el formulario de alta (vista HTML).")
    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("socios", socioService.findAll());
        model.addAttribute("libros", libroService.findAll());
        return "alta-prestamo";
    }

    // --- 3. Procesar el Alta de Préstamo (V3: Incluye Lógica de Bloqueo) ---
    @Operation(summary = "Procesa el formulario y registra un nuevo préstamo.",
            description = "Aplica las reglas de negocio (Máx. 3 activos, sin penalización) y calcula la fecha límite (+2 días).")
    @PostMapping("/alta")
    public String darAltaPrestamo(
            @Parameter(description = "Objeto Préstamo con los IDs de socio y libro seleccionados en el formulario.")
            @ModelAttribute Prestamo prestamo) {

        try {
            prestamoService.darAltaPrestamo(prestamo);
        } catch (IllegalStateException e) {
            // Manejo básico de errores para el web controller (debería mostrar un mensaje en la vista)
            // Aquí se redirigiría con un error:
            // return "redirect:/web/prestamos/alta?error=" + e.getMessage();
        }

        return "redirect:/web/prestamos";
    }

    // --- 4. Baja de Préstamo (Eliminación) ---
    @Operation(summary = "Elimina un préstamo (Baja Lógica o Eliminación).",
            description = "Elimina el registro del préstamo por su ID. Útil para corregir errores, pero no para la devolución.")
    @GetMapping("/baja/{id}")
    public String darBajaPrestamo(
            @Parameter(description = "ID del préstamo a eliminar.")
            @PathVariable int id) {
        prestamoService.deleteById(id);
        return "redirect:/web/prestamos";
    }

   //  --- 5. Procesar Devolución (V3: Con Cálculo de Penalización) ---
    @Operation(summary = "Procesa la devolución de un libro.",
            description = "Registra la fecha de devolución, calcula el retraso e impone la penalización (2 días por día de retraso) si aplica.")
    @GetMapping("/devolver/{id}")
    public String devolverPrestamo(
            @Parameter(description = "ID del préstamo a devolver.")
            @PathVariable int id) {
        prestamoService.devolverPrestamo(id);
        return "redirect:/web/prestamos";
    }
}
