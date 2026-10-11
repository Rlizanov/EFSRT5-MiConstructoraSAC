package com.edu.pe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.pe.modelo.AnalistaFuncional;
import com.edu.pe.repositorio.AnalistaFuncionalRepository;
import com.edu.pe.service.IAnalistaFuncionalService;

@Service
public class AnalistaFuncionalServiceImpl implements IAnalistaFuncionalService {

    @Autowired
    private AnalistaFuncionalRepository repo;

    @Override
    public List<AnalistaFuncional> listarTodos() {
        return (List<AnalistaFuncional>) repo.findAll();
    }

    @Override
    public void guardar(AnalistaFuncional analistaFuncional) {
        repo.save(analistaFuncional);
    }

    @Override
    public AnalistaFuncional buscarPorId(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(int id) {
        repo.deleteById(id);
    }
}