package com.clinica.clinica.domain;

import com.clinica.clinica.domain.enums.EstadoCita;
import jakarta.persistence.*; 
import java.time.LocalDateTime;

@Entity
@Table(name = "Citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name= "fecha_hora", nullable = false)
    private LocalDateTime fecha_hora; // Anotación que indica que el atributo fecha_hora no puede ser nulo en la tabla "citas". Esto asegura que todas las citas tengan una fecha y hora asignadas, evitando registros incompletos en la base de datos.

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCita estado = EstadoCita.Programada;

    @Column(name = "motivo_cancelacion")
    private String motivo_cancelacion;
    
//----------------------------------------------------------------  
@ManyToOne // Anotación que indica que existe una relación uno a uno entre la entidad Paciente y la entidad Usuario. Cada paciente está asociado a un único usuario en el sistema de la clínica.
@JoinColumn(name = "paciente_id", nullable = false)
private Paciente paciente; // Atributo que representa la relación con la entidad Usuario. Este atributo permite acceder a la información del usuario asociado al paciente, como su nombre, correo y rol en el sistema de la clínica.

@ManyToOne // Anotación que indica que existe una relación uno a uno entre la entidad Paciente y la entidad Usuario. Cada paciente está asociado a un único usuario en el sistema de la clínica.
@JoinColumn(name = "medico_id", nullable = false) //medico_id es la clave foránea que hace referencia a la tabla de usuarios, ya que un médico es un tipo de usuario en el sistema de la clínica.
private Usuario medico; // Atributo que representa la relación con la entidad Usuario. Este atributo permite acceder a la información del usuario asociado al paciente, como su nombre, correo y rol en el sistema de la clínica.
//----------------------------------------------------------------  


public Cita() {}
    public Cita(LocalDateTime fecha_hora, EstadoCita estado, String motivo_cancelacion, Paciente paciente, Usuario medico) {
        this.fecha_hora = fecha_hora;
        this.estado = estado;
        this.motivo_cancelacion = motivo_cancelacion;
        this.paciente = paciente;
        this.medico = medico;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFechaHora() { return fecha_hora; }
    public void setFechaHora(LocalDateTime fecha_hora) { this.fecha_hora = fecha_hora; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public String getMotivoCancelacion() { return motivo_cancelacion; }
    public void setMotivoCancelacion(String motivo_cancelacion) { this.motivo_cancelacion = motivo_cancelacion; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Usuario getMedico() { return medico; }
    public void setMedico(Usuario medico) { this.medico = medico; }

}