package fap_sports.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Clase controladora para manejar las solicitudes relacionadas con el administrador
@Controller
public class AdministradorController {

    // Método que maneja las solicitudes GET a la ruta /admin
    @GetMapping("/admin")
    public String admin() {
        // Redirige a la vista de registro cuando se accede a /admin
        return "redirect:/registro"; 
    }
}