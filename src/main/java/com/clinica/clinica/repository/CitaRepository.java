package com.clinica.clinica.repository;

import com.clinica.clinica.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

import java.util.List; //La lista es para listar todas las citas de un paciente, ya que un paciente puede tener varias citas, y queremos que la búsqueda sea insensible a mayúsculas y minúsculas

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> { 
    List<Cita> findByPacienteId(Long pacienteId); 
}
