package com.clinica.clinica.repository;

import com.clinica.clinica.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> { 
    
    boolean existsByDocumento(String documento);
    
    Optional<Paciente> findByDocumento(String documento);
}