package com.clinica.clinica.service;

import com.clinica.clinica.domain.Usuario;
import com.clinica.clinica.domain.enums.Rol;
import com.clinica.clinica.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale; //Locale es una clase que representa una configuración regional específica, que incluye información sobre el idioma, el país y otras convenciones culturales. En este caso, se utiliza para convertir el correo electrónico a minúsculas de manera consistente, independientemente de la configuración regional del sistema.

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario registrar(String nombre, String correo, String password, Rol rol) { //Se utiliza para indicar que este método se ejecutará dentro de una transacción, lo que significa que si ocurre algún error durante la ejecución del método, todos los cambios realizados en la base de datos se revertirán automáticamente. Esto garantiza la consistencia de los datos y evita que queden registros incompletos o inconsistentes en caso de errores.
        String correoNormalizado = normalizarCorreo(correo);

        if (usuarioRepository.existsByCorreoIgnoreCase(correoNormalizado)) {
            throw new CorreoDuplicadoException("Ya existe un usuario con ese correo.");
        }

        Usuario usuario = new Usuario( //Se utiliza para indicar que estamos creando un nuevo objeto de tipo Usuario, y que le estamos pasando los valores de nombre, correo normalizado, contraseña codificada y rol. Esto nos permite crear un nuevo usuario con los datos proporcionados por el formulario de registro.
                nombre.trim(),
                correoNormalizado,
                passwordEncoder.encode(password),
                rol
        );

        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public void solicitarRecuperacion(String correo) {
        usuarioRepository.findByCorreoIgnoreCase(normalizarCorreo(correo)); //Se utiliza para indicar que queremos buscar un usuario por su correo, y que queremos que la búsqueda sea insensible a mayúsculas y minúsculas, es decir, que si el correo es "
    }

    private String normalizarCorreo(String correo) {
        return correo == null ? "" : correo.trim().toLowerCase(Locale.ROOT); //Se utiliza para indicar que queremos normalizar el correo electrónico, es decir, eliminar los espacios en blanco al inicio y al final, y convertirlo a minúsculas. Esto nos permite evitar problemas de duplicidad de correos electrónicos debido a diferencias en mayúsculas y minúsculas.
    }
}