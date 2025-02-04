package com.brunobomfim.pdv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint para registrar um novo usuário
    @PostMapping("/registrar")
    public String registerUser(@RequestParam String email,
                               @RequestParam String nome,
                               @RequestParam String senha) {
        try {
            userService.registerUser(email, nome, senha);
            return "Usuário registrado com sucesso!";
        } catch (Exception e) {
            return "Erro ao registrar o usuário: " + e.getMessage();
        }
    }

    // Endpoint para autenticar um usuário
    @PostMapping("/login")
    public String authenticateUser(@RequestParam String email, @RequestParam String senha) {
        try {
            boolean autenticado = userService.authenticateUser(email, senha);
            if (autenticado) {
                return "Login bem-sucedido!";
            } else {
                return "Credenciais inválidas!";
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
}
