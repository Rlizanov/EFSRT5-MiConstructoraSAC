package com.edu.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.edu.pe.modelo.AnalistaProgramador;
import com.edu.pe.repositorio.AnalistaProgramadorRepository;

@Controller
public class AdminVistaController {

	@Autowired
	private AnalistaProgramadorRepository analistaProgramadorRepository;

	@GetMapping("/admin/empleado")
	public String empleado() {
		return "Admin/empleado";
	}

	@GetMapping("/admin/empleado-administracion")
	public String empleadoAdministracion() {
		return "Admin/empleado_administracion";
	}

	@GetMapping("/admin/analistas")
	public String analistaProgramador(Model model) {
		model.addAttribute("lista", analistaProgramadorRepository.findAll());
		return "Admin/analista_programador";
	}

	@GetMapping("/admin/analista-administracion")
	public String analistaAdministracion(Model model) {

		model.addAttribute("analista", new AnalistaProgramador());
		model.addAttribute("lista", analistaProgramadorRepository.findAll());

		return "Admin/analista_administracion";
	}

	@GetMapping("/admin/funcional")
	public String funcional() {
		return "Admin/funcional";
	}

	@GetMapping("/admin/funcional-form")
	public String funcionalForm() {
		return "Admin/funcional_form";
	}

	@GetMapping({"/admin/ingeniero", "/trabajo/ingeniero-civil"})
	public String ingenieroAdmin() {
		return "Admin/ingeniero";
	}
}