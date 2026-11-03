package com.clinica.clinica.config;

import com.clinica.clinica.domain.enums.Rol;
import com.clinica.clinica.service.CorreoDuplicadoException;
import com.clinica.clinica.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Crea un usuario de prueba cada vez que arranca la aplicación (la base H2 está en memoria y empieza vacía).
// Credenciales para probar el login: admin@clinica.com / Admin123!
@Component
public class DatosPruebaLogin implements CommandLineRunner {

    private final UsuarioService usuarioService;

    public DatosPruebaLogin(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) {
        try {
            usuarioService.registrar("Administrador", "admin@clinica.com", "Admin123!", Rol.Administrador);
        } catch (CorreoDuplicadoException exception) {
            // Si el usuario ya existe, no se hace nada
        }
    }
}
