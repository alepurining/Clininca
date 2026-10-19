package com.clinica.clinica.domain;

import com.clinica.clinica.domain.enums.NivelRiesgo;
import jakarta.persistence.*; 

@Entity
@Table(name = "Pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name = "documento", nullable = false, unique = true, length = 11)
    private String documento;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombre_completo;

    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_riesgo", nullable = false)
    private NivelRiesgo nivelRiesgo = NivelRiesgo.Bajo;

    @Column(name = "citas_incumplidas", nullable = false)
    private int citas_incumplidas = 0; // Valor predeterminado para el número de citas incumplidas, que se establece como 0 al crear un nuevo paciente. Esto asegura que todos los pacientes tengan un valor inicial para este atributo.

    @Column(name = "requiere_seguimiento", nullable = false)
    private boolean requiere_seguimiento = false; // Valor predeterminado para indicar si el paciente requiere seguimiento, que se establece como false al crear un nuevo paciente.


//----------------------------------------------------------------  
@OneToOne // Anotación que indica que existe una relación uno a uno entre la entidad Paciente y la entidad Usuario. Cada paciente está asociado a un único usuario en el sistema de la clínica.
@JoinColumn(name = "usuario_id", nullable = true) 
private Usuario usuario; // Atributo que representa la relación con la entidad Usuario. Este atributo permite acceder a la información del usuario asociado al paciente, como su nombre, correo y rol en el sistema de la clínica.

//----------------------------------------------------------------  


public Paciente() {}
    public Paciente(String documento, String nombre_completo, String telefono, NivelRiesgo nivelRiesgo) {
        this.documento = documento;
        this.nombre_completo = nombre_completo;
        this.telefono = telefono;
        this.nivelRiesgo = nivelRiesgo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getNombreCompleto() { return nombre_completo; }
    public void setNombreCompleto(String nombre_completo) { this.nombre_completo = nombre_completo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public NivelRiesgo getNivelRiesgo() { return nivelRiesgo; }
    public void setNivelRiesgo(NivelRiesgo nivelRiesgo) { this.nivelRiesgo = nivelRiesgo; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}