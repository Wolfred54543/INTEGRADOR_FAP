package fap_sports.integrador.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class PaginaErrorController {

    @GetMapping("/404")
    public String Error404() {
        return "redirect:/error/404"; // Redirige a la vista de registro cuando se accede a /admin
    }
}
