package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.models.Rol;
import fap_sports.integrador.models.Tipo;
import fap_sports.integrador.models.Usuario;
import fap_sports.integrador.services.RolService;
import fap_sports.integrador.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    // Constructor para inyección de dependencias
    public RegistroController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    // Mostrar formulario de registro con datos necesarios para la vista
    @GetMapping("/registro")
    public String mostrarRegistroForm(Model model) {
        model.addAttribute("usuario", new Usuario()); // Objeto vacío para el formulario
        model.addAttribute("roles", rolService.listarRoles()); // Listado de roles disponibles
        model.addAttribute("tipos", usuarioService.getAllTipos()); // Tipos de usuario
        model.addAttribute("decadas", usuarioService.getAllDecadas()); // Décadas para selección
        model.addAttribute("usuarios", usuarioService.getAllUsuarios()); // Lista de usuarios para mostrar
        return "vistas/registrarLicenciado";
    }

    // Procesar el registro de un nuevo usuario
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, @RequestParam Long tipoId, @RequestParam Long decId) {
        try {
            // Obtener y asignar tipo y década seleccionados
            Tipo tipo = usuarioService.getTipoById(tipoId);
            Decada decada = usuarioService.getDecadaById(decId);
            usuario.setTipo(tipo);
            usuario.setDecada(decada);

            // Si no tiene roles asignados, asignar el rol de invitado por defecto
            if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
                Rol rolInvitado = rolService.obtenerRolPorId(3L);
                if (rolInvitado != null) {
                    List<Rol> roles = new ArrayList<>();
                    roles.add(rolInvitado);
                    usuario.setRoles(roles);
                }
            }

            // Registrar usuario a través del servicio
            usuarioService.registrarUsuario(usuario);
            return "redirect:/registro";

        } catch (Exception e) {
            // En caso de error, mostrar el formulario de registro nuevamente
            return "vistas/registrarLicenciado";
        }
    }

    // Eliminar un usuario por su ID
    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return "redirect:/registro";
    }

    // Mostrar formulario para editar un usuario existente
    @GetMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("tipos", usuarioService.getAllTipos());
        model.addAttribute("decadas", usuarioService.getAllDecadas());
        // model.addAttribute("roles", usuarioService.getAllRoles()); // Activar si es necesario
        return "vistas/editarLicenciado";
    }

    // Procesar la actualización de un usuario
    @PostMapping("/actualizarUsuario")
    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.updateUsuario(usuario);
        return "redirect:/registro";
    }
}
