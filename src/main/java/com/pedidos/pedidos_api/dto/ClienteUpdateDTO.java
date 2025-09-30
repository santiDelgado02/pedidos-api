// ClienteUpdateDTO.java
package com.pedidos.pedidos_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class ClienteUpdateDTO {

    @Email(message = "Debe ser un email válido")
    private String email;

    private String nombre;
    private String telefono;
    private String direccion;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password; // opcional

    private Boolean activo;

    // Getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
