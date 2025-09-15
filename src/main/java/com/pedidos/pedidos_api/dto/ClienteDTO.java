package com.pedidos.pedidos_api.dto;

import com.pedidos.pedidos_api.interfaces.ClienteValidationGroups.OnCreate;
import com.pedidos.pedidos_api.interfaces.ClienteValidationGroups.OnUpdate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClienteDTO {

    @NotBlank(message = "El email es obligatorio", groups = OnCreate.class)
    @Email(message = "Debe ser un email válido", groups = {OnCreate.class, OnUpdate.class})
    private String email; // Solo se usa al crear

    @NotBlank(message = "El nombre es obligatorio", groups = OnCreate.class)
    private String nombre;

    private String telefono;
    private String direccion;
    private Boolean activo;

    // Getters y setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
