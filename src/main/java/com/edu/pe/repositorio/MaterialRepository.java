package com.edu.pe.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.edu.pe.modelo.Material;

public interface MaterialRepository extends CrudRepository<Material, Integer> {

    @Transactional
    void deleteByObraIdobra(Integer idobra);

}