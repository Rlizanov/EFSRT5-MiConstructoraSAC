package com.edu.pe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.edu.pe.modelo.AnalistaProgramador;
import com.edu.pe.modelo.Empleado;
import com.edu.pe.repositorio.AnalistaProgramadorRepository;
import com.edu.pe.repositorio.EmpleadoRepository;
import com.edu.pe.service.IAnalistaProgramadorService;

@Service
public class AnalistaProgramadorServiceImpl implements IAnalistaProgramadorService {

    @Autowired
    private AnalistaProgramadorRepository repo;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<AnalistaProgramador> listarTodos() {
        return (List<AnalistaProgramador>) repo.findAll();
    }

    @Override
    public boolean guardar(AnalistaProgramador analistaProgramador) {

        Integer id = analistaProgramador.getIdempleado();

        if (id == null) {
            return false;
        }

        Empleado empleado = empleadoRepository.findById(id).orElse(null);

        if (empleado == null) {
            return false;
        }

        Integer existe = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM tbl_analista_programador WHERE idanalistaprog = ?",
            Integer.class,
            id
        );

        if (existe != null && existe > 0) {
            jdbcTemplate.update(
                "UPDATE tbl_analista_programador " +
                "SET lenguajeprincipal = ?, nivel = ?, experienciaanios = ? " +
                "WHERE idanalistaprog = ?",
                analistaProgramador.getLenguajeprincipal(),
                analistaProgramador.getNivel(),
                analistaProgramador.getExperienciaanios(),
                id
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO tbl_analista_programador " +
                "(idanalistaprog, lenguajeprincipal, nivel, experienciaanios) " +
                "VALUES (?, ?, ?, ?)",
                id,
                analistaProgramador.getLenguajeprincipal(),
                analistaProgramador.getNivel(),
                analistaProgramador.getExperienciaanios()
            );
        }

        return true;
    }

    @Override
    public AnalistaProgramador buscarPorId(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(int id) {
        jdbcTemplate.update(
            "DELETE FROM tbl_analista_programador WHERE idanalistaprog = ?",
            id
        );
    }
}