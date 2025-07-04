package fap_sports.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministradorController {

    @GetMapping("/admin")
    public String admin() {
        return "redirect:/registro"; // Redirige a la vista de registro cuando se accede a /admin
    }
}
