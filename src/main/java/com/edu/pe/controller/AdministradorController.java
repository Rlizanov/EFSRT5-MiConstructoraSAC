package com.edu.pe.controller;

import com.edu.pe.modelo.Trabajador;
import com.edu.pe.repositorio.MaterialRepository;
import com.edu.pe.repositorio.ObraRepository;
import com.edu.pe.repositorio.TrabajadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AdministradorController {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ObraRepository obraRepository;

    @GetMapping("/administrador")
    public String verAdministrador(Model model) {

        model.addAttribute("trabajadores", trabajadorRepository.findAll());
        model.addAttribute("materiales", materialRepository.findAll());
        model.addAttribute("obras", obraRepository.findAll());

        return "Administrador/administrador";
    }

    @PostMapping("/actualizar-sueldo")
    public String actualizarSueldo(
            @RequestParam("trabajo") String trabajo,
            @RequestParam("sueldo") Double sueldo) {

        Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByTrabajo(trabajo);

        if (trabajadorOpt.isPresent()) {
            Trabajador t = trabajadorOpt.get();
            t.setSueldo(sueldo);
            trabajadorRepository.save(t);
        }

        return "redirect:/administrador";
    }

    @GetMapping("/trabajo/empleado")
    public String empleado(Model model) {
        model.addAttribute("trabajadores", trabajadorRepository.findAll());
        return "Admin/empleado";
    }

    @GetMapping("/trabajo/analista-programador")
    public String analistaProgramador(Model model) {
        model.addAttribute("trabajadores", trabajadorRepository.findAll());
        return "Admin/analista_programador";
    }

    @GetMapping("/trabajo/ingeniero-civil")
    public String ingenieroCivil(Model model) {
        model.addAttribute("trabajadores", trabajadorRepository.findAll());
        return "Ingeniero/ingeniero";
    }

    @GetMapping("/trabajo/analista-funcional")
    public String analistaFuncional(Model model) {
        model.addAttribute("trabajadores", trabajadorRepository.findAll());
        return "Admin/funcional";
    }

    @GetMapping("/trabajo/administrador")
    public String administradorTrabajo(Model model) {
        model.addAttribute("trabajadores", trabajadorRepository.findAll());
        return "Administrador/administrador";
    }

    @GetMapping("/trabajo/jefe-proyectos")
    public String jefeProyectos(Model model) {
        model.addAttribute("trabajadores", trabajadorRepository.findAll());
        return "Jefe/jefe";
    }
}