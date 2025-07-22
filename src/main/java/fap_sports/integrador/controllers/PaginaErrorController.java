package fap_sports.integrador.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class PaginaErrorController {

    @GetMapping("/404")
    public String Error404() {
        return "redirect:/error/404"; // Redirige a la vista de 404 cuando se inyecta en la url direcciones inexistentes o codigo malicioso
    }
}
