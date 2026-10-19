package com.clinica.clinica.service;

public class DocumentoDuplicadoException extends RuntimeException { //runtimeException es una clase de excepción en Java que indica que ha ocurrido un error en tiempo de ejecución. Al extender RuntimeException, DocumentoDuplicadoException se convierte en una excepción personalizada que puede ser lanzada cuando se detecta un documento duplicado en el sistema.

    public DocumentoDuplicadoException(String message) {
        super(message);
    }
}