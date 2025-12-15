package com.example.biblioteca.controller;

import com.example.biblioteca.model.Libro;
import com.example.biblioteca.services.LibroService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

// Asumimos que has creado LibroService
@Controller
@RequestMapping("/web/libros")
@RequiredArgsConstructor
public class LibroWebController {

    private final LibroService libroService; // Inyectamos el servicio
    
    @GetMapping
    public String listarLibros(Model model) {
        // Obtenemos la lista de libros del servicio
        model.addAttribute("libros", libroService.findAll());

        // Retorna la vista que se encuentra en /templates/libros-listado.html
        return "listado-libros";
    }

    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("libro", new Libro());
        return "formulario-libro"; // Asume que tienes un formulario-libro.html
    }

    @PostMapping("/alta")
    public String guardarLibro(@Valid @ModelAttribute("libro") Libro libro, BindingResult result) {
        if (result.hasErrors()) {
            return "formulario-libro";
        }
        libroService.save(libro);
        return "redirect:/web/libros";
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificacion(@PathVariable int id, Model model) {
        Libro libro = libroService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de libro inválido:" + id));
        model.addAttribute("libro", libro);
        return "formulario-libro";
    }

    @PostMapping("/modificar/{id}")
    public String actualizarLibro(@PathVariable int id, @Valid @ModelAttribute("libro") Libro libroDesdeFormulario, BindingResult result) {
        if (result.hasErrors()) {
            return "formulario-libro";
        }
        Libro libroExistente = libroService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de libro inválido:" + id));

        //Actualizar solo los campos modificables
        libroExistente.setTitulo(libroDesdeFormulario.getTitulo());
        libroExistente.setAutor(libroDesdeFormulario.getAutor());
        libroExistente.setCategoria(libroDesdeFormulario.getCategoria());
        libroExistente.setIsbn(libroDesdeFormulario.getIsbn());

        libroService.save(libroExistente);

        return "redirect:/web/libros";
    }

    @GetMapping("/baja/{id}")
    public String eliminarLibro(@PathVariable int id) {
        libroService.deleteById(id);
        return "redirect:/web/libros";
    }
}
