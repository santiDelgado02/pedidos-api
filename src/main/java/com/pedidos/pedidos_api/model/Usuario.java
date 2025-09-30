package com.pedidos.pedidos_api.model;

import jakarta.persistence.*;

@Entity
@Table(name="usuario")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String username; // o email

    @Column(nullable=false)
    private String password; // 🔒 se almacena encriptada

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Rol rol;

    public enum Rol {
        ADMIN,
        USER
    }

    // getters y setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
