package fap_sports.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "vistas/login";  // Devuelve la vista del formulario de login cuando se accede a /login
    }
}