package com.clinica.clinica.domain;

import com.clinica.clinica.domain.enums.Rol;
import jakarta.persistence.*; //Importa las anotaciones y clases necesarias para la persistencia de datos utilizando JPA (Jakarta Persistence API), como @Entity, @Table, @Id, @GeneratedValue, @Column y @Enumerated.

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Anotación que indica que el atributo id es la clave primaria de la entidad y que su valor se generará automáticamente utilizando la estrategia de identidad de la base de datos.
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100) // Anotación que indica que el atributo nombre no puede ser nulo y tiene una longitud máxima de 100 caracteres en la tabla "usuarios".
    private String nombre;

    @Column(name = "correo", nullable = false, unique = true, length = 100) // Anotación que indica que el atributo correo no puede ser nulo y debe ser único en la tabla "usuarios", evitando duplicados en la base de datos.
    private String correo;

    @Column(name = "contrasena", nullable = false, length = 100)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    //--------------------------------------------------------------
    
    public Usuario() {} // Constructor por defecto de la clase Usuario. Es necesario para que JPA pueda crear instancias de la entidad Usuario al recuperar datos de la base de datos.

// Constructor de la clase Usuario que inicializa los atributos nombre, correo, contrasena y rol. Este constructor se utiliza para crear instancias de Usuario con los valores proporcionados.
    public Usuario(String nombre, String correo, String contrasena, Rol rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

// Getters y Setters para los atributos de la clase Usuario. Permiten acceder y modificar los valores de id, nombre, correo, contrasena y rol.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}