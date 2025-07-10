package fap_sports.integrador.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fap_sports.integrador.services.ComunicadoService;
import java.util.List;
import fap_sports.integrador.models.Comunicado;

@Controller
public class InvitadoComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService;

    //Muestra los comunicados para la vista de invitados traidos desde el controlador de ComunicadoController.java
    @GetMapping("/comunicadoInvitados")
    public String comunicadoInvitados(Model model) {
        List<Comunicado> comunicadosPublicados = comunicadoService.getComunicadosPublicados();
        model.addAttribute("comunicados", comunicadosPublicados);
        return "vistas/invitados/comunicadoInvitados";
    }
}
