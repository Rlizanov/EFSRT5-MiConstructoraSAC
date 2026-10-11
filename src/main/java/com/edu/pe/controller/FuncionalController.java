package com.edu.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.pe.modelo.AnalistaFuncional;
import com.edu.pe.modelo.Empleado;
import com.edu.pe.repositorio.AnalistaFuncionalRepository;
import com.edu.pe.repositorio.EmpleadoRepository;

@Controller
@RequestMapping("/funcionales")
public class FuncionalController {

    @Autowired
    private AnalistaFuncionalRepository repo;

    @Autowired
    private EmpleadoRepository empleadoRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String verFuncionales(Model model) {

        model.addAttribute("listaFuncional", repo.findAll());

        return "Admin/funcional";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("funcional", new AnalistaFuncional());

        return "Admin/funcional_form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {

        AnalistaFuncional funcional = repo.findById(id).orElse(null);

        if (funcional == null) {
            model.addAttribute("funcional", new AnalistaFuncional());
        } else {
            model.addAttribute("funcional", funcional);
        }

        return "Admin/funcional_form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("funcional") AnalistaFuncional funcional,
                          RedirectAttributes attributes) {

        Integer id = funcional.getIdempleado();

        if (id == null) {
            attributes.addFlashAttribute("error",
                    "Debe ingresar un ID de empleado existente");
            return "redirect:/funcionales/nuevo";
        }

        Empleado emp = empleadoRepo.findById(id).orElse(null);

        if (emp == null) {
            attributes.addFlashAttribute("error",
                    "Ese empleado no existe");
            return "redirect:/funcionales/nuevo";
        }

        Integer existe = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM tbl_analista_funcional WHERE idanalistafunc = ? OR idanalistafuncional = ?",
                Integer.class,
                id,
                id
        );

        if (existe != null && existe > 0) {
            jdbcTemplate.update(
                    "UPDATE tbl_analista_funcional SET area = ?, proceso = ?, herramienta = ? WHERE idanalistafunc = ? OR idanalistafuncional = ?",
                    funcional.getArea(),
                    funcional.getProceso(),
                    funcional.getHerramienta(),
                    id,
                    id
            );
        } else {
            jdbcTemplate.update(
                    "INSERT INTO tbl_analista_funcional " +
                    "(idanalistafunc, area, proceso, herramienta, idanalistafuncional) " +
                    "VALUES (?, ?, ?, ?, ?)",
                    id,
                    funcional.getArea(),
                    funcional.getProceso(),
                    funcional.getHerramienta(),
                    id
            );
        }

        attributes.addFlashAttribute("success",
                "Registro guardado correctamente");

        return "redirect:/funcionales";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id,
                           RedirectAttributes attributes) {

        jdbcTemplate.update(
                "DELETE FROM tbl_analista_funcional WHERE idanalistafunc = ? OR idanalistafuncional = ?",
                id,
                id
        );

        attributes.addFlashAttribute("success",
                "Registro eliminado correctamente");

        return "redirect:/funcionales";
    }
}