package com.edu.pe.controller;

import com.edu.pe.modelo.Rol;
import com.edu.pe.modelo.Usuario;
import com.edu.pe.repositorio.IRolRepositorio; // Asegúrate de tener este repositorio
import com.edu.pe.repositorio.IUsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

// Usamos @Controller para devolver vistas HTML, a diferencia de tu @RestController
@Controller
public class UsuarioWebController {

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IRolRepositorio rolRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. Carga la pantalla del formulario
    @GetMapping("/usuarios/nuevo")
    public String mostrarFormulario(Model model) {
        List<Rol> listaRoles = rolRepositorio.findAll();

        model.addAttribute("roles", listaRoles);
        model.addAttribute("usuario", new Usuario());

        return "Admin/nuevo_usuario"; // Llama al HTML que te pasé en el mensaje anterior
    }

    // 2. Guarda el usuario y lo une con su Rol
    @PostMapping("/usuarios/guardar")
    public String guardarUsuarioWeb(Usuario usuario, @RequestParam("idrol") Integer idrol) {

        // Encriptar contraseña
        String claveEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(claveEncriptada);

        // Valores por defecto
        usuario.setFechareg(new Date());
        usuario.setActivo(true);

        // Asignar el rol seleccionado en el combo box
        Rol rolSeleccionado = rolRepositorio.findById(idrol).orElse(null);
        if (rolSeleccionado != null) {
            Set<Rol> roles = new HashSet<>();
            roles.add(rolSeleccionado);
            usuario.setRoles(roles);
        }

        // Guardar en SQL Server (Guarda el usuario y la relación en tbl_usuario_rol)
        usuarioRepositorio.save(usuario);

        return "redirect:/empleados";
    }

    // 1. Listar todos los usuarios en una tabla
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepositorio.findAll());
        return "Admin/lista_usuarios";
    }

    // 2. Cargar datos del usuario para editar
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        List<Rol> listaRoles = rolRepositorio.findAll();

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", listaRoles);
        return "Admin/editar_usuario";
    }

    // 3. Guardar los cambios de la actualización
    @PostMapping("/usuarios/actualizar")
    public String actualizarUsuario(Usuario usuario, @RequestParam("idrol") Integer idrol) {
        Usuario usuarioExistente = usuarioRepositorio.findById(usuario.getIdusuario()).orElse(null);

        if (usuarioExistente != null) {
            usuarioExistente.setNomusuario(usuario.getNomusuario());
            usuarioExistente.setEmail(usuario.getEmail());

            // Si el administrador escribió una nueva contraseña, la encriptamos y actualizamos.
            // Si lo dejó en blanco, conservamos la contraseña anterior.
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            // Actualizar el rol
            Rol rolSeleccionado = rolRepositorio.findById(idrol).orElse(null);
            if (rolSeleccionado != null) {
                Set<Rol> roles = new HashSet<>();
                roles.add(rolSeleccionado);
                usuarioExistente.setRoles(roles);
            }

            usuarioRepositorio.save(usuarioExistente);
        }
        return "redirect:/usuarios";
    }

    // 4. Activar o Desactivar usuario (Anulación lógica)
    @GetMapping("/usuarios/estado/{id}")
    public String cambiarEstado(@PathVariable("id") Integer id) {
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        if (usuario != null) {
            // Invierte el estado actual: si es true pasa a false, si es false pasa a true
            usuario.setActivo(!usuario.getActivo());
            usuarioRepositorio.save(usuario);
        }
        return "redirect:/usuarios";
    }
}