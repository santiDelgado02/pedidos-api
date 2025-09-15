package com.pedidos.pedidos_api.interfaces;

/**
 * Interfaces para validación de ClienteDTO según la operación.
 */
public class ClienteValidationGroups {

    /** Grupo de validación para creación (POST) */
    public interface OnCreate {}

    /** Grupo de validación para actualización (PATCH/PUT) */
    public interface OnUpdate {}
}
