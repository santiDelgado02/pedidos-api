package com.pedidos.pedidos_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class TransportistaDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(example = "Transporte López SRL")
    private String nombre;

    @NotBlank(message = "El CUIT es obligatorio")
    @Pattern(regexp = "^\\d{2}-?\\d{8}-?\\d$", message = "El CUIT debe tener el formato correcto (ej: 20-12345678-9)")
    @Schema(example = "20-12345678-9")
    private String cuit;

    @Schema(example = "+54 9 223 123 4567")
    private String telefono;

    @Email(message = "El email no es válido")
    @Schema(example = "contacto@lopezsrl.com")
    private String email;

    @Schema(description = "Estado del transportista (activo/inactivo)", example = "true")
    private Boolean activo;

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
