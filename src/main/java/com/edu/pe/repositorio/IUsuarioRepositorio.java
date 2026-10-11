package com.edu.pe.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.edu.pe.modelo.Usuario;

public interface IUsuarioRepositorio extends CrudRepository<Usuario, Integer> {

	@Query("select u from Usuario u join fetch u.roles where u.nomusuario=:nomusuario")
	Usuario findByNomusuario(@Param("nomusuario") String nomusuario);
}