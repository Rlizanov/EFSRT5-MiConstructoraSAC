package com.edu.pe.service;

import java.util.List;
import com.edu.pe.modelo.AnalistaProgramador;

public interface IAnalistaProgramadorService {

    List<AnalistaProgramador> listarTodos();
    boolean guardar(AnalistaProgramador analistaProgramador);
    AnalistaProgramador buscarPorId(int id);
    void eliminar(int id);
}