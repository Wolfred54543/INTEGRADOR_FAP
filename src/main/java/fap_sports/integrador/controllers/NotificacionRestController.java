package fap_sports.integrador.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fap_sports.integrador.models.Comunicado;
import fap_sports.integrador.services.ComunicadoService;

@RestController
@RequestMapping("/api")
public class NotificacionRestController {

    @Autowired
    private ComunicadoService comunicadoService;

    @GetMapping("/notificaciones")
    public List<Comunicado> obtenerNotificaciones() {
        return comunicadoService.getComunicadosPublicados(); // o .stream().limit(5).toList()
    }
}
