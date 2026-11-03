package com.clinica.clinica.service;

import com.clinica.clinica.domain.Usuario;
import com.clinica.clinica.domain.enums.Rol;
import com.clinica.clinica.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        usuarioService = new UsuarioService(usuarioRepository, passwordEncoder);
    }

    // 1. Rechazo de correo inválido (Sintaxis)
    @Test
    void rechazoDeCorreoInvalido() {
        String correoInvalido = "usuario_sin_arroba.com";
        String password = "PasswordFuerte123!";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrar("Juan", correoInvalido, password, Rol.Medico);
        });

        assertTrue(exception.getMessage().toLowerCase().contains("formato") ||
                exception.getMessage().toLowerCase().contains("correo"));
    }

    // 2. Rechazo de contraseña débil
    @Test
    void rechazoDeContrasenaDebil() {
        String correoValido = "usuario@dominio.com";
        String contrasenaDebil = "123";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrar("Juan", correoValido, contrasenaDebil, Rol.Medico);
        });

        assertTrue(exception.getMessage().toLowerCase().contains("débil") ||
                exception.getMessage().toLowerCase().contains("contraseña"));
    }

    // 3. Login exitoso
    @Test
    void loginExitoso() {
        String correoValido = "admin@clinica.com";
        String passwordCorrecta = "ClaveSegura123!";

        Usuario usuarioGuardado = new Usuario("Admin", correoValido, passwordEncoder.encode(passwordCorrecta), Rol.Administrador);
        when(usuarioRepository.findByCorreoIgnoreCase(correoValido)).thenReturn(Optional.of(usuarioGuardado));

        Usuario usuarioLogueado = usuarioService.login(correoValido, passwordCorrecta);

        assertNotNull(usuarioLogueado, "El usuario debe retornar correctamente tras un login exitoso");
        assertEquals("Admin", usuarioLogueado.getNombre());
    }

    // 4. Login con contraseña errónea
    @Test
    void rechazaLoginConContrasenaErronea() {
        Usuario usuario = new Usuario("Ana", "ana@clinica.com", passwordEncoder.encode("ClaveCorrecta1!"), Rol.Medico);
        when(usuarioRepository.findByCorreoIgnoreCase("ana@clinica.com")).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> usuarioService.login("ana@clinica.com", "ClaveIncorrecta9!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Credenciales");
    }

    // 5. Login de usuario inexistente
    @Test
    void rechazaLoginDeUsuarioInexistente() {
        when(usuarioRepository.findByCorreoIgnoreCase("noexiste@clinica.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.login("noexiste@clinica.com", "Password123!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Credenciales");
    }

    // 6. Campos obligatorios: ninguno puede ser nulo o estar en blanco
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rechazaNombreNuloOEnBlanco(String nombre) {
        assertThatThrownBy(() -> usuarioService.registrar(nombre, "ana@clinica.com", "Password123!", Rol.Medico))
                .isInstanceOf(IllegalArgumentException.class);
        verify(usuarioRepository, never()).save(any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rechazaCorreoNuloOEnBlanco(String correo) {
        assertThatThrownBy(() -> usuarioService.registrar("Ana", correo, "Password123!", Rol.Medico))
                .isInstanceOf(IllegalArgumentException.class);
        verify(usuarioRepository, never()).save(any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rechazaContrasenaNulaOEnBlanco(String password) {
        assertThatThrownBy(() -> usuarioService.registrar("Ana", "ana@clinica.com", password, Rol.Medico))
                .isInstanceOf(IllegalArgumentException.class);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void rechazaRolNulo() {
        assertThatThrownBy(() -> usuarioService.registrar("Ana", "ana@clinica.com", "Password123!", null))
                .isInstanceOf(IllegalArgumentException.class);
        verify(usuarioRepository, never()).save(any());
    }
}