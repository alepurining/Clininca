package com.clinica.clinica.service;

import com.clinica.clinica.domain.Usuario;
import com.clinica.clinica.domain.enums.Rol;
import com.clinica.clinica.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario registrar(String nombre, String correo, String password, Rol rol) {
        // 1. Validación de campos obligatorios
        validarObligatorio(nombre, "El nombre es obligatorio.");
        validarObligatorio(correo, "El correo es obligatorio.");
        validarObligatorio(password, "La contraseña es obligatoria.");
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio.");
        }

        // 2. NUEVO: Validación de formato de correo (debe contener '@' y dominio)
        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El formato del correo es inválido.");
        }

        // 3. NUEVO: Validación de seguridad de la contraseña (mínimo 8 caracteres)
        if (password.length() < 8) {
            throw new IllegalArgumentException("La contraseña es muy débil (mínimo 8 caracteres).");
        }

        String correoNormalizado = normalizarCorreo(correo);

        if (usuarioRepository.existsByCorreoIgnoreCase(correoNormalizado)) {
            throw new CorreoDuplicadoException("Ya existe un usuario con ese correo.");
        }

        Usuario usuario = new Usuario(
                nombre.trim(),
                correoNormalizado,
                passwordEncoder.encode(password),
                rol
        );

        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario login(String correo, String password) {
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(normalizarCorreo(correo))
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas."));

        if (!passwordEncoder.matches(password, usuario.getContrasena())) {
            throw new IllegalArgumentException("Credenciales inválidas.");
        }

        return usuario;
    }

    @Transactional(readOnly = true)
    public void solicitarRecuperacion(String correo) {
        usuarioRepository.findByCorreoIgnoreCase(normalizarCorreo(correo));
    }

    private String normalizarCorreo(String correo) {
        return correo == null ? "" : correo.trim().toLowerCase(Locale.ROOT);
    }

    private void validarObligatorio(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}