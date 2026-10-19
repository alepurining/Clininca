package com.clinica.clinica.repository; //Sirve para indicar que esta clase es un repositorio, es decir, una clase que se encarga de hacer consultas a la base de datos

import com.clinica.clinica.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository; //Sirve para hacer consultas a la base de datos
import org.springframework.stereotype.Repository; //Sirve para indicar que esta clase es un repositorio, es decir, una clase que se encarga de hacer consultas a la base de datos

import java.util.Optional; //Sirve para indicar que el resultado de la consulta puede ser nulo, es decir, que no se encontró ningún usuario con el correo indicado

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> { //JpaRepository es una interfaz que nos permite hacer consultas a la base de datos, en este caso a la tabla usuario, y nos permite hacer consultas como findAll(), findById(), save(), deleteById(), etc.
    
    boolean existsByCorreoIgnoreCase(String correo); //Sirve para indicar que queremos buscar un usuario por su correo, y que queremos que la búsqueda sea insensible a mayúsculas y minúsculas, es decir, que si el correo es "
    
    Optional<Usuario> findByCorreoIgnoreCase(String correo); //Sirve para indicar que queremos buscar un usuario por su correo, y que queremos que la búsqueda sea insensible a mayúsculas y minúsculas, es decir, que si el correo es "
}