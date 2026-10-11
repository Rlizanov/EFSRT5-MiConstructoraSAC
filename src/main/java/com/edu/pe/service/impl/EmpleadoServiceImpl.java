package com.edu.pe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.pe.modelo.Empleado;
import com.edu.pe.repositorio.EmpleadoRepository;
import com.edu.pe.service.IEmpleadoService;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> ListarTodos() {
        return (List<Empleado>) empleadoRepository.findAll();
    }

    @Override
    public boolean Guardar(Empleado empleado) {
        try {
            empleadoRepository.save(empleado);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Empleado BuscarPorId(int id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public boolean Eliminar(int id) {
        try {
            empleadoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}