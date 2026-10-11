package com.edu.pe.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.edu.pe.modelo.Rol;

public interface IRolRepositorio extends JpaRepository<Rol, Integer> {

	Rol findByNomrol(String nomrol);

}