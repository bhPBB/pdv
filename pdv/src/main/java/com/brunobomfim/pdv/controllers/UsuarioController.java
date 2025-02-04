package com.brunobomfim.pdv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.brunobomfim.pdv.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService ur;

    // Endpoint para registrar um novo usuário
    @PostMapping("/registrar")
    public String registrarUsuario(@RequestParam String email,
                               @RequestParam String nome,
                               @RequestParam String senha) {
        try {
            ur.registrarUsuario(email, nome, senha);
            return "Usuário registrado com sucesso!";
        } catch (Exception e) {
            return "Erro ao registrar o usuário: " + e.getMessage();
        }
    }

    // Endpoint para autenticar um usuário
    @PostMapping("/login")
    public String autenticarUsuario(@RequestParam String email, @RequestParam String senha) {
        try {
            boolean autenticado = ur.autenticarUsuario(email, senha);
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
