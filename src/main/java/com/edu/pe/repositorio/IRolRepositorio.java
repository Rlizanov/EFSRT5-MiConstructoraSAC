package com.edu.pe.repositorio;

import org.springframework.data.repository.CrudRepository;
import com.edu.pe.modelo.Rol;

public interface IRolRepositorio extends CrudRepository<Rol, Integer> {

	Rol findByNomrol(String nomrol);

}