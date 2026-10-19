package com.clinica.clinica.web;

import com.clinica.clinica.domain.enums.NivelRiesgo;

public class PacienteForm {

    private String documento = ""; //El "" se utiliza para inicializar la variable documento con un valor vacío, evitando que sea nula y evitando posibles errores de NullPointerException al acceder a ella antes de asignarle un valor.
    private String nombreCompleto = "";
    private String telefono = "";
    private NivelRiesgo nivelRiesgo = NivelRiesgo.Bajo;

    public String getDocumento() { //Se utiliza para obtener el valor del documento del paciente
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public NivelRiesgo getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(NivelRiesgo nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }
}