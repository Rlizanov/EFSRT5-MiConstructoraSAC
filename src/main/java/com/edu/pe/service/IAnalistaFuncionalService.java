package com.edu.pe.service;

import java.util.List;
import com.edu.pe.modelo.AnalistaFuncional;

public interface IAnalistaFuncionalService {

    List<AnalistaFuncional> listarTodos();
    void guardar(AnalistaFuncional analistaFuncional);
    AnalistaFuncional buscarPorId(int id);
    void eliminar(int id);
}