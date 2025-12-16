package com.example.biblioteca.controller.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalWebController {

    @GetMapping("/menu")
    public String showMenu(Model model) {
        return "menu";
    }

}
