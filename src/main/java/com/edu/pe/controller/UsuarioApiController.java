package com.edu.pe.controller;

import com.edu.pe.modelo.Usuario;
import com.edu.pe.repositorio.IUsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date; // <-- Cambiamos la importación aquí

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioApiController {

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestBody Usuario nuevoUsuario) {

        // 1. Encriptar la contraseña recibida en texto plano
        String passwordEncriptada = passwordEncoder.encode(nuevoUsuario.getPassword());
        nuevoUsuario.setPassword(passwordEncriptada);

        // 2. Establecer valores por defecto (activo y fecha actual)
        nuevoUsuario.setActivo(true);
        nuevoUsuario.setFechareg(new Date()); // <-- Generamos la fecha compatible

        // 3. Guardar en la base de datos SQL Server
        usuarioRepositorio.save(nuevoUsuario);

        return "Usuario " + nuevoUsuario.getNomusuario() + " registrado correctamente en la base de datos.";
    }
}