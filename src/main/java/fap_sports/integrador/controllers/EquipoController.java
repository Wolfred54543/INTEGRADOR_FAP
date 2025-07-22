package fap_sports.integrador.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import fap_sports.integrador.repositories.EquipoRepository;
import fap_sports.integrador.models.Decada;
//import fap_sports.integrador.models.Rol;
import fap_sports.integrador.models.Usuario;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.services.EquipoService;
import fap_sports.integrador.services.UsuarioService;

// Clase controladora para manejar las solicitudes relacionadas con los equipos
@Controller
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/formularioEquipos")
    public String formularioEquipos(Model model) {
        // Crea un objeto vacío para el formulario de registro de equipo
        model.addAttribute("equipo", new Equipo());

        // Lista todos los equipos existentes para mostrarlos en la vista
        model.addAttribute("equipos", equipoService.getAllEquipos());

        // Carga las décadas disponibles para asignarlas al equipo
        model.addAttribute("decadas", equipoService.getAllDecadas());

        // Obtiene los usuarios que tienen el rol de "DELEGADO" para asignarlos a un equipo
        List<Usuario> delegados = equipoService.getUsuariosPorRol("DELEGADO");
        model.addAttribute("delegados", delegados);

        // Muestra la vista que contiene el formulario de equipos
        return "vistas/administrador/equipo/equipos";
    }

    @PostMapping("/registrarEquipos")
    public String registrarEquipos(
            @ModelAttribute Equipo equipo,
            @RequestParam Long decId,
            @RequestParam Long delegadoId) {

        // Asocia una década seleccionada al equipo
        Decada decada = equipoService.getDecadaById(decId);
        equipo.setDecada(decada);

        // Asocia un delegado al equipo, validando que el usuario tenga el rol adecuado (id 2 = DELEGADO)
        Usuario delegado = usuarioService.getUsuarioById(delegadoId);
        if (delegado != null && delegado.getRoles().stream().anyMatch(rol -> rol.getId() == 2)) {
            equipo.setDelegado(delegado);
        }

        // Registra el nuevo equipo en la base de datos
        equipoService.registrarEquipo(equipo);

        // Redirige al formulario para actualizar la vista y evitar reenvíos
        return "redirect:/formularioEquipos";
    }
    // Eliminar un equipo por su ID
    @PostMapping("/eliminarEquipo/{id}")
    public String eliminarEquipo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            equipoService.eliminarEquipo(id);
            redirectAttributes.addAttribute("success", "Equipo eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "No se puede eliminar el equipo porque esta registrado en un campeonato");
        }
        return "redirect:/formularioEquipos";
    }

    // Mostrar formulario para editar un equipo existente
    @GetMapping("/editarEquipo/{id}")
    public String editarEquipo(@PathVariable Long id, Model model) {
        Equipo equipo = equipoService.getEquipoById(id);
        model.addAttribute("equipos", equipo);
        model.addAttribute("decadas", usuarioService.getAllDecadas());
        // Obtiene los usuarios que tienen el rol de "DELEGADO" para asignarlos a un equipo
        List<Usuario> delegados = equipoService.getUsuariosPorRol("DELEGADO");
        model.addAttribute("delegados", delegados);
        // model.addAttribute("roles", usuarioService.getAllRoles()); // Activar si es necesario
        return "vistas/administrador/equipo/editarEquipo";
    }

    // Procesa la actualización de un usuario
    @PostMapping("/actualizarEquipo")
    public String actualizarEquipo(@ModelAttribute Equipo equipo) {
        equipoService.actualizarEquipo(equipo);
        return "redirect:/formularioEquipos";
    }
}
