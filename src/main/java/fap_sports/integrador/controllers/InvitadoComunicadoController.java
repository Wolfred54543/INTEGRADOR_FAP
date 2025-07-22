package fap_sports.integrador.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fap_sports.integrador.services.ComunicadoService;
import java.util.List;
import fap_sports.integrador.models.Comunicado;

// Clase controladora para manejar los comunicados visibles para los invitados
@Controller
public class InvitadoComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService; // Servicio para manejar la lógica de comunicados

    // Muestra los comunicados para la vista de invitados traídos desde el controlador de ComunicadoController.java
    @GetMapping("/comunicadoInvitados")
    public String comunicadoInvitados(Model model) {
        List<Comunicado> comunicadosPublicados = comunicadoService.getComunicadosPublicados(); // Obtiene los comunicados que han sido publicados
        model.addAttribute("comunicados", comunicadosPublicados); // Agrega la lista de comunicados al modelo
        return "vistas/invitados/comunicadoInvitados"; // Retorna la vista para mostrar comunicados a invitados
    }
}