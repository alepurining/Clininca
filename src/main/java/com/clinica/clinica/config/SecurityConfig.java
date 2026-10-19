package com.clinica.clinica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean //Se utiliza para indicar que este método devuelve un bean de Spring, que es un objeto administrado por el contenedor de Spring y que puede ser inyectado en otras clases. En este caso, estamos creando un bean de tipo PasswordEncoder, que se utilizará para codificar las contraseñas de los usuarios.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}