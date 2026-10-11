package com.edu.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.pe.modelo.AnalistaProgramador;
import com.edu.pe.service.IAnalistaProgramadorService;

@Controller
@RequestMapping("/analistas")
public class AnalistaProgramadorController {

    @Autowired
    private IAnalistaProgramadorService analistaProgramadorService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String vistaPrincipal(Model model) {
        model.addAttribute("lista", analistaProgramadorService.listarTodos());
        return "Admin/analista_programador";
    }

    @GetMapping("/administracion")
    public String administracion(Model model) {
        model.addAttribute("analista", new AnalistaProgramador());
        model.addAttribute("lista", analistaProgramadorService.listarTodos());
        return "Admin/analista_administracion";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("analista") AnalistaProgramador analistaProgramador,
                          RedirectAttributes attributes) {

        boolean result = analistaProgramadorService.guardar(analistaProgramador);

        if (!result) {
            attributes.addFlashAttribute("error",
                    "Ese empleado no existe. Primero debe estar registrado en la tabla empleados.");
            return "redirect:/analistas/administracion";
        }

        attributes.addFlashAttribute("success",
                "Analista Programador guardado correctamente");

        return "redirect:/analistas/administracion";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        AnalistaProgramador analistaProgramador =
                analistaProgramadorService.buscarPorId(id);

        model.addAttribute("analista", analistaProgramador);
        model.addAttribute("lista", analistaProgramadorService.listarTodos());

        return "Admin/analista_administracion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id,
                           RedirectAttributes attributes) {

        jdbcTemplate.update(
                "DELETE FROM tbl_analista_programador WHERE idanalistaprog = ?",
                id
        );

        attributes.addFlashAttribute("success",
                "Analista Programador eliminado correctamente");

        return "redirect:/analistas/administracion";
    }
}