package com.edu.pe.controller;

import com.edu.pe.modelo.Obra;
import com.edu.pe.repositorio.MaterialRepository;
import com.edu.pe.repositorio.ObraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/jefes")
public class JefeProyectoController {

    @Autowired
    private ObraRepository obraRepo;

    @Autowired
    private MaterialRepository materialRepo;

    @GetMapping
    public String verJefe(Model model) {

        model.addAttribute("obras", obraRepo.findAll());
        model.addAttribute("obra", new Obra());

        return "Jefe/jefe";
    }

    @PostMapping("/guardar")
    public String guardarObra(@ModelAttribute("obra") Obra obra) {

        obraRepo.save(obra);

        return "redirect:/jefes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {

        materialRepo.deleteByObraIdobra(id);

        obraRepo.deleteById(id);

        return "redirect:/jefes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {

        Optional<Obra> optionalObra = obraRepo.findById(id);

        if (optionalObra.isPresent()) {
            model.addAttribute("obra", optionalObra.get());
        } else {
            model.addAttribute("obra", new Obra());
        }

        model.addAttribute("obras", obraRepo.findAll());

        return "Jefe/jefe";
    }
}