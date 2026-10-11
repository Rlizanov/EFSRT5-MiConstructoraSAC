package com.edu.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.pe.modelo.AnalistaFuncional;
import com.edu.pe.service.IAnalistaFuncionalService;

@Controller
@RequestMapping("/analista-funcional")
public class AnalistaFuncionalController {

    @Autowired
    private IAnalistaFuncionalService analistaFuncionalService;

    @GetMapping
    public String vistaPrincipal(Model model) {

        model.addAttribute("lista", analistaFuncionalService.listarTodos());

        return "Admin/funcional";
    }

    @GetMapping("/administracion")
    public String administracion(Model model) {

        model.addAttribute("analistaFuncional", new AnalistaFuncional());
        model.addAttribute("lista", analistaFuncionalService.listarTodos());

        return "Admin/analista_administracion";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute AnalistaFuncional analistaFuncional,
                          RedirectAttributes attributes) {

        analistaFuncionalService.guardar(analistaFuncional);

        attributes.addFlashAttribute("success",
                "Analista Funcional guardado correctamente");

        return "redirect:/analista-funcional/administracion";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {

        AnalistaFuncional analistaFuncional = analistaFuncionalService.buscarPorId(id);

        model.addAttribute("analistaFuncional", analistaFuncional);
        model.addAttribute("lista", analistaFuncionalService.listarTodos());

        return "Admin/analista_administracion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id,
                           RedirectAttributes attributes) {

        analistaFuncionalService.eliminar(id);

        attributes.addFlashAttribute("success",
                "Analista Funcional eliminado correctamente");

        return "redirect:/analista-funcional/administracion";
    }
}