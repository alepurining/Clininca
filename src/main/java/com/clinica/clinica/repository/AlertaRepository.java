package com.clinica.clinica.repository;

import com.clinica.clinica.domain.Alerta;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import java.util.List; 

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> { //interfaz que extiende JpaRepository para proporcionar métodos CRUD y de consulta para la entidad Alerta, utilizando Long como tipo de dato para el identificador de la entidad.

    List<Alerta> findByPacienteId(Long pacienteId); //Sirve para indicar que queremos buscar una alerta por su id de paciente, y que queremos que la búsqueda sea insensible a mayúsculas y minúsculas
}
