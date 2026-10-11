package com.edu.pe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.edu.pe.modelo.Empleado;
import com.edu.pe.service.IEmpleadoService;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoApiController {

    @Autowired
    private IEmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.ListarTodos();
    }
}