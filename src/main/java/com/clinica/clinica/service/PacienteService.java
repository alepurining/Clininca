package com.clinica.clinica.service;

import com.clinica.clinica.domain.Paciente;
import com.clinica.clinica.domain.enums.NivelRiesgo;
import com.clinica.clinica.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public Paciente registrar(String documento, String nombreCompleto, String telefono, NivelRiesgo nivelRiesgo) {
        String documentoLimpio = normalizarDocumento(documento);

        if (pacienteRepository.existsByDocumento(documentoLimpio)) {
            throw new DocumentoDuplicadoException("Ya existe un paciente registrado con el documento: " + documentoLimpio);
        }

        Paciente paciente = new Paciente(
                documentoLimpio,
                nombreCompleto.trim(),
                telefono != null ? telefono.trim() : null, //Se utiliza para indicar que queremos asignar el valor del teléfono al paciente, pero si el teléfono es nulo, queremos asignar null en lugar de una cadena vacía. Esto nos permite evitar posibles errores de NullPointerException al acceder al teléfono antes de asignarle un valor.
                nivelRiesgo
        );

        return pacienteRepository.save(paciente);
    }

    @Transactional(readOnly = true) 
    public List<Paciente> listar() {
        return pacienteRepository.findAll(); //Se utiliza para indicar que queremos obtener una lista de todos los pacientes registrados en la base de datos, y que esta operación no modificará los datos, por lo que se puede ejecutar en modo de solo lectura.
    }

    private String normalizarDocumento(String documento) { 
        return documento == null ? "" : documento.trim(); //trim() elimina los espacios en blanco al inicio y al final de la cadena, y el operador ternario se utiliza para devolver una cadena vacía si el documento es nulo, evitando así posibles errores de NullPointerException al acceder a él antes de asignarle un valor.
    }
}