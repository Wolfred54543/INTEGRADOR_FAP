package fap_sports.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiembroMesaController {

    // Muestra la vista para registrar resultados (ruta principal para miembros de mesa)
    @GetMapping("/miembroMesa")
    public String miembroMesa() {
        return "redirect:/registrarResultados"; // Redirige a /registrarResultados
    }

    // Muestra la vista para registrar goleadores
    @GetMapping("/registrarGoleadores")
    public String registrarGoleadores() {
        return "vistas/miembroMesa/registrarGoleadores";
    }
}
