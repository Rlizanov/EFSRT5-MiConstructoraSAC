package com.edu.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.pe.modelo.Empleado;
import com.edu.pe.service.IEmpleadoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private IEmpleadoService empleadoService;

    @GetMapping
    public String Inicio(Model model) {
        model.addAttribute("lista", empleadoService.ListarTodos());
        return "Admin/empleado";
    }

    @GetMapping("/administracion")
    public String Nuevo(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("lista", empleadoService.ListarTodos());
        return "Admin/empleado_administracion";
    }

    @PostMapping("/guardar")
    public String Guardar(@ModelAttribute("empleado") Empleado empleado,
                          RedirectAttributes attributes) {

        boolean result = empleadoService.Guardar(empleado);

        if (!result) {
            attributes.addFlashAttribute("error",
                    "No se pudo guardar los datos");
            return "redirect:/empleados/administracion";
        }

        attributes.addFlashAttribute("success",
                "Empleado guardado correctamente");

        return "redirect:/empleados";
    }

    @GetMapping("/editar/{id}")
    public String Editar(@PathVariable("id") int id,
                         Model model,
                         RedirectAttributes attributes) {

        Empleado empleado = empleadoService.BuscarPorId(id);

        if (empleado == null) {
            attributes.addFlashAttribute("error",
                    "No se encontró empleado con ID " + id);
            return "redirect:/empleados";
        }

        model.addAttribute("empleado", empleado);
        model.addAttribute("lista", empleadoService.ListarTodos());

        return "Admin/empleado_administracion";
    }

    @GetMapping("/eliminar/{id}")
    public String Eliminar(@PathVariable("id") int id,
                           RedirectAttributes attributes) {

        boolean result = empleadoService.Eliminar(id);

        if (result) {
            attributes.addFlashAttribute("success",
                    "Empleado eliminado correctamente");
        } else {
            attributes.addFlashAttribute("error",
                    "No se pudo eliminar el empleado");
        }

        return "redirect:/empleados";
    }

    @ModelAttribute
    public void CargarDatos(Model model) {

        String cargos[] = {
                "Empleado",
                "Analista Programador",
                "Ingeniero Civil",
                "Analista Funcional",
                "Administrador",
                "Jefe de Proyectos"
        };

        model.addAttribute("cargos", cargos);
    }
}