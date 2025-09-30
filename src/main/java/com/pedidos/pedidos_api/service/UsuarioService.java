package com.pedidos.pedidos_api.service;

import com.pedidos.pedidos_api.model.Usuario;
import com.pedidos.pedidos_api.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public Usuario registrarUsuario(String username, String password, Usuario.Rol rol) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }
}
