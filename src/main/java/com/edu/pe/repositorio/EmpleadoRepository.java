package com.edu.pe.repositorio;

import org.springframework.data.repository.CrudRepository;
import com.edu.pe.modelo.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {

}