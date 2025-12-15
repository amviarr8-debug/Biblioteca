package com.example.biblioteca.controller;

import com.example.biblioteca.model.Socio;
import com.example.biblioteca.services.SocioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

// Nota: Asegúrate de tener SocioService inyectable.
@Controller
@RequestMapping("/web/socios")
@RequiredArgsConstructor
public class SocioWebController {

    private final SocioService socioService;

    @GetMapping
    public String listarSocios(Model model) {
        model.addAttribute("socios", socioService.findAll());
        // Retorna la vista: /templates/listado-socios.html
        return "listado-socios";
    }

    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("socio", new Socio());
        return "formulario-socio"; // Asume que tienes un formulario-socio.html
    }

    @PostMapping("/alta")
    public String guardarSocio(@Valid @ModelAttribute("socio") Socio socio, BindingResult result) {
        if (result.hasErrors()) {
            return "formulario-socio";
        }
        socioService.save(socio);
        return "redirect:/web/socios";
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificacion(@PathVariable int id, Model model) {
        Socio socio = socioService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de socio inválido:" + id));
        model.addAttribute("socio", socio);
        return "formulario-socio";
    }

    @PostMapping("/modificar/{id}")
    public String actualizarSocio(@PathVariable int id, @Valid @ModelAttribute("socio") Socio socioDesdeFormulario, BindingResult result) {
        if (result.hasErrors()) {
            return "formulario-socio";
        }
        Socio socioExistente = socioService.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de socio inválido:" + id));
        // campos editables
        socioExistente.setNombre(socioDesdeFormulario.getNombre());
        socioExistente.setApellidos(socioDesdeFormulario.getApellidos());
        socioExistente.setEmail(socioDesdeFormulario.getEmail());
        socioExistente.setFechaNacimiento(socioDesdeFormulario.getFechaNacimiento());
        socioService.save(socioExistente);

        return "redirect:/web/socios";
    }

    @GetMapping("/baja/{id}")
    public String eliminarSocio(@PathVariable int id) {
        socioService.deleteById(id);
        return "redirect:/web/socios";
    }
}
