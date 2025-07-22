package fap_sports.integrador.config;

import fap_sports.integrador.models.Comunicado;
import fap_sports.integrador.services.ComunicadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttributeConfig {

    @Autowired
    private ComunicadoService comunicadoService;

    @ModelAttribute("notificaciones")
    public List<Comunicado> notificaciones() {
        return comunicadoService.getComunicadosPublicados();
    }
}
