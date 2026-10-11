package com.edu.pe.controller;

import com.edu.pe.modelo.Rol;
import com.edu.pe.repositorio.IRolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolApiController {

    @Autowired
    private IRolRepositorio rolRepositorio;

    @PostMapping("/registrar")
    public String registrarRol(@RequestBody Rol nuevoRol) {

        // Guardar el rol en la base de datos SQL Server
        rolRepositorio.save(nuevoRol);

        return "El rol " + nuevoRol.getNomrol() + " fue registrado correctamente.";
    }
}