package com.edu.pe.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.edu.pe.modelo.IngenieroCivil;

public interface IngenieroCivilRepository
        extends CrudRepository<IngenieroCivil, Integer> {

    @Transactional
    void deleteByIdempleado(Integer idempleado);

}