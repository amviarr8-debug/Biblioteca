package com.example.biblioteca.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data // Proporciona Getters, Setters, toString, etc.
public class PrestamoAltaDTO {

    // Usamos Integer en lugar de int para poder usar @NotNull y manejar nulos
    @NotNull(message = "El ID del socio es obligatorio.")
    private Integer socioId;

    @NotNull(message = "El ID del libro es obligatorio.")
    private Integer libroId;
}