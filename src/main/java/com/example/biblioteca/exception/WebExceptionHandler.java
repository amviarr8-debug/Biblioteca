package com.example.biblioteca.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Gestiona las excepciones lanzadas desde los Web Controllers (MVC)
 * y redirige a una página de error amigable (custom-error.html).
 * Este manejador captura errores de negocio (409), no encontrados (404),
 * datos inválidos (400) y fallos internos inesperados (500).
 */
@ControllerAdvice
public class WebExceptionHandler {


    @ExceptionHandler(Exception.class)
    public String handleGlobalMvcExceptions(Exception ex, Model model) {

        String errorMessage = ex.getMessage();
// 1. Determinar el código de error basado en el tipo de excepción
        String errorCode;
        String errorTitle;

        if (ex instanceof ResourceNotFoundException) {
            errorCode = "404";
            errorTitle = "Recurso No Encontrado";
        } else if (ex instanceof IllegalStateException) {
            errorCode = "409";
            errorTitle = "Conflicto de Regla de Negocio";
        } else if (ex instanceof IllegalArgumentException) {
            errorCode = "400";
            errorTitle = "Petición Inválida";
        } else {
            errorCode = "500";
            errorTitle = "Error Interno del Servidor";
            errorMessage = "Ha ocurrido un fallo inesperado en el sistema. Por favor, inténtelo de nuevo más tarde.";

        }

        // 2. Pasar los datos a la vista
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorCode", errorCode);
        model.addAttribute("errorTitle", errorTitle);

        // 3. Redirige a la plantilla de error
        return "error/custom-error";
    }
}