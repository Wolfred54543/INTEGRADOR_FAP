package fap_sports.integrador.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Clase controladora para manejar las solicitudes relacionadas con el delegado
@Controller
public class DelegadoController {

    // Método que maneja las solicitudes GET a la ruta /delegado
    @GetMapping("/delegado")
    public String delegado() {
        return "redirect:/reclamoFormulario"; // Redirige a la vista de reclamoFormulario cuando se accede a /delegado
    }
}
