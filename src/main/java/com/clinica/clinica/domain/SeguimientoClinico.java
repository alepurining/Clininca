package com.clinica.clinica.domain;

import jakarta.persistence.*; 
import java.time.LocalDateTime;

@Entity
@Table(name = "seguimiento_clinico")
public class SeguimientoClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fecha_registro = LocalDateTime.now(); // Valor predeterminado para la fecha y hora de registro, que se establece como la fecha y hora actual al crear un nuevo seguimiento clínico. Esto asegura que todos los seguimientos clínicos tengan una fecha y hora asignadas, incluso si no se proporciona explícitamente al crear la instancia de SeguimientoClinico.

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "recomendaciones", columnDefinition = "TEXT")
    private String recomendaciones;

    
//----------------------------------------------------------------  
@ManyToOne // Anotación que indica que existe una relación uno a uno entre la entidad Paciente y la entidad Usuario. Cada paciente está asociado a un único usuario en el sistema de la clínica.
@JoinColumn(name = "paciente_id", nullable = false)
private Paciente paciente; // Atributo que representa la relación con la entidad Usuario. Este atributo permite acceder a la información del usuario asociado al paciente, como su nombre, correo y rol en el sistema de la clínica.

@ManyToOne // Anotación que indica que existe una relación uno a uno entre la entidad Paciente y la entidad Usuario. Cada paciente está asociado a un único usuario en el sistema de la clínica.
@JoinColumn(name = "medico_id", nullable = false)
private Usuario medico; // Atributo que representa la relación con la entidad Usuario. Este atributo permite acceder a la información del usuario asociado al paciente, como su nombre, correo y rol en el sistema de la clínica.
//----------------------------------------------------------------  


public SeguimientoClinico() {}

    public SeguimientoClinico(LocalDateTime fecha_registro, String observaciones, String recomendaciones, Paciente paciente, Usuario medico) {
        this.fecha_registro = fecha_registro;
        this.observaciones = observaciones;
        this.recomendaciones = recomendaciones;
        this.paciente = paciente;
        this.medico = medico;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFechaRegistro() { return fecha_registro; }
    public void setFechaRegistro(LocalDateTime fecha_registro) { this.fecha_registro = fecha_registro; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getRecomendaciones() { return recomendaciones; }
    public void setRecomendaciones(String recomendaciones) { this.recomendaciones = recomendaciones; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Usuario getMedico() { return medico; }
    public void setMedico(Usuario medico) { this.medico = medico; }

}