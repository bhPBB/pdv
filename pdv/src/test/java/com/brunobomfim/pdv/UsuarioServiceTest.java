package com.brunobomfim.pdv;

import com.brunobomfim.pdv.services.UsuarioService;
import com.brunobomfim.pdv.models.Usuario;
import com.brunobomfim.pdv.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UsuarioServiceTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    private String email = "teste@email.com";
    private String nome = "Usuário Teste";
    private String senha = "senha123";
    private String senhaCodificada = "senhaCodificada";

    @BeforeEach
    void setUp() {
        // Limpar o banco antes de cada teste
        itemRepository.deleteAll(); 
        usuarioRepository.deleteAll();

        // Criar e salvar o usuário no banco de dados H2
        usuarioService.registrarUsuario(email, nome, senha);       
    }

    @Test
    void excluirUsuario_DeveExcluirPeloEmail() {
        //excluindo o usuário
        usuarioService.excluirUsuario(email);

        // Verificando se o usuário foi excluído
        assertFalse(usuarioRepository.findById(email).isPresent());
    }


    @Test
    void registrarUsuario_DeveSalvarUsuarioComSenhaCodificada() {
        // Usuário para registrar
        email = "email2@teste.com";
        nome = "user2";
        senha = "senha";

        // Chamando o método que estamos testando
        usuarioService.registrarUsuario(email, nome, senha);

        // Recuperando o usuário do banco para verificar
        Usuario usuarioSalvo = usuarioRepository.findById(email).orElse(null);
        
        // Verificando se o usuário foi salvo com a senha codificada
        assertNotNull(usuarioSalvo);
        assertEquals(email, usuarioSalvo.getEmail());
        assertEquals(nome, usuarioSalvo.getNome());
        assertTrue(passwordEncoder.matches(senha, usuarioSalvo.getSenha())); // Verifica se a senha foi codificada corretamente
    }

    @Test
    void autenticarUsuario_DeveRetornarTrue_QuandoSenhaCorreta() {
        // Chamando o método que estamos testando
        boolean resultado = usuarioService.autenticarUsuario(email, senha);

        // Verifica se o retorno foi true
        assertTrue(resultado);
    }

    @Test
    void autenticarUsuario_DeveRetornarFalse_QuandoSenhaIncorreta() {
        // Testa com senha errada
        boolean resultado = usuarioService.autenticarUsuario(email, "senhaErrada");

        // Verifica se o retorno foi false
        assertFalse(resultado);
    }

    @Test
    void autenticarUsuario_DeveLancarExcecao_QuandoEmailNaoCadastrado() {
        // Simula um usuário não encontrado
        String emailInexistente = "naoexiste@email.com";

        // Verifica se a exceção correta é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            usuarioService.autenticarUsuario(emailInexistente, senha)
        );

        assertEquals("Email não cadastrado.", exception.getMessage());
    }
}
