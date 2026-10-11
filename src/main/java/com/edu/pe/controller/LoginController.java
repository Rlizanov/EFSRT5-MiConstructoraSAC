package com.edu.pe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/")
	public String inicio() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String mostrarLogin() {
		return "Login/login";
	}

	@GetMapping("/denegado")
	public String accesoDenegado() {
		return "Error/error";
	}
}