package fap_sports.integrador.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DelegadoController {
    @GetMapping("/delegado")
    public String admin() {
        return "vistas/reclamoFormulario"; // Redirige a la vista de reclamoFormulario cuando se accede a /delegado
    }
}
