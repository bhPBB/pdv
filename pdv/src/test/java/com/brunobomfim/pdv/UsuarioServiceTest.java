package com.brunobomfim.pdv;

import com.brunobomfim.pdv.services.UsuarioService;
import com.brunobomfim.pdv.models.Usuario;
import com.brunobomfim.pdv.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private final String email = "teste@email.com";
    private final String nome = "Usuário Teste";
    private final String senha = "senha123";
    private final String senhaCodificada = "senhaCodificada";

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(senhaCodificada);
    }

    @Test
    void registrarUsuario_DeveSalvarUsuarioComSenhaCodificada() {
        // Simula o comportamento do passwordEncoder
        when(passwordEncoder.encode(senha)).thenReturn(senhaCodificada);

        // Chama o método que estamos testando
        usuarioService.registrarUsuario(email, nome, senha);

        // Verifica se o método save foi chamado com um usuário contendo a senha codificada
        verify(usuarioRepository).save(argThat(user ->
            user.getEmail().equals(email) &&
            user.getNome().equals(nome) &&
            user.getSenha().equals(senhaCodificada)
        ));
    }

    @Test
    void autenticarUsuario_DeveRetornarTrue_QuandoSenhaCorreta() {
        // Simula a busca pelo usuário no repositório
        when(usuarioRepository.findById(email)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(senha, senhaCodificada)).thenReturn(true);

        // Chama o método que estamos testando
        boolean resultado = usuarioService.autenticarUsuario(email, senha);

        // Verifica se o retorno foi true
        assertTrue(resultado);
    }

    @Test
    void autenticarUsuario_DeveRetornarFalse_QuandoSenhaIncorreta() {
        // Simula a busca pelo usuário no repositório
        when(usuarioRepository.findById(email)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaErrada", senhaCodificada)).thenReturn(false);

        // Chama o método que estamos testando
        boolean resultado = usuarioService.autenticarUsuario(email, "senhaErrada");

        // Verifica se o retorno foi false
        assertFalse(resultado);
    }

    @Test
    void autenticarUsuario_DeveLancarExcecao_QuandoEmailNaoCadastrado() {
        // Simula um usuário não encontrado
        when(usuarioRepository.findById(email)).thenReturn(Optional.empty());

        // Verifica se a exceção correta é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            usuarioService.autenticarUsuario(email, senha)
        );

        assertEquals("Email não cadastrado.", exception.getMessage());
    }
}