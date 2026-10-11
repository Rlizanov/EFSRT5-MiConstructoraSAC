package com.edu.pe.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.edu.pe.modelo.Trabajador;

public interface TrabajadorRepository extends CrudRepository<Trabajador, Integer> {

    Optional<Trabajador> findByTrabajo(String trabajo);
}