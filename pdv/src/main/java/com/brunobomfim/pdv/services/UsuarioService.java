package com.brunobomfim.pdv.services;

import com.brunobomfim.pdv.models.Usuario;
import com.brunobomfim.pdv.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository ur;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para registrar um novo usuário
    public void registrarUsuario(String email, String nome, String senha) {
        // Codificando a senha
        String senhaCodificada = passwordEncoder.encode(senha);

        // Criando o usuário
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(senhaCodificada);

        // Salvando o usuário no banco de dados
        ur.save(usuario);
    }

    // Método para verificar as credenciais de um usuário
    public boolean autenticarUsuario(String email, String senha) {
        Usuario usuario = ur.findById(email)
            .orElseThrow(() -> new RuntimeException("Email não cadastrado."));

        // Verificando se a senha fornecida bate com a senha armazenada
        return passwordEncoder.matches(senha, usuario.getSenha());
    }
}


