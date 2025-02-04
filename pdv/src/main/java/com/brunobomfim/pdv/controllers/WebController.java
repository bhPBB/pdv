package com.brunobomfim.pdv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // Exibe a página de login
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Exibe a página de registro
    @GetMapping("/registrar")
    public String registerPage() {
        return "registrar";
    }

    // Redireciona para a página inicial após login bem-sucedido
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("message", "Bem-vindo ao PDV!");
        return "home"; // Você pode criar essa página posteriormente se quiser uma página inicial
    }
}
