package com.example.biblioteca.controller;

import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.services.PrestamoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrestamoWebController {


    @Autowired
    private PrestamoServiceImpl prestamoService;


    @GetMapping("/menu")
    public String showMenu(Model model) {
        return "menu";
    }



}
