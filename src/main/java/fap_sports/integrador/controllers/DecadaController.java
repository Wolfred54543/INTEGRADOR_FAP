package fap_sports.integrador.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.services.DecadaService;

@Controller
public class DecadaController {

    @Autowired
    private DecadaService decadaService; // Servicio que maneja la lógica relacionada a la entidad Década

    @GetMapping("/formularioDecadas")
    public String formularioDecadas(Model model) {
        model.addAttribute("decada", new Decada()); // Objeto vacío para el formulario
        model.addAttribute("decadas", decadaService.getAllDecadas()); // Lista de décadas ya registradas
        return "vistas/decadas"; // Devuelve la vista donde se muestran y registran décadas
    }

    @PostMapping("/registrarDecadas")
    public String registrarDecadas(@ModelAttribute Decada decada) {
        decadaService.registrarDecada(decada); // Guarda la nueva década enviada desde el formulario
        return "redirect:/formularioDecadas"; // Redirige para evitar reenvío de datos y refrescar la vista
    }
}
