package fap_sports.integrador.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.common.collect.ImmutableList;

import fap_sports.integrador.models.Contactanos;
import fap_sports.integrador.services.ContactanosService;

@Controller
public class ContactanosController {

    @Autowired
    private ContactanosService contactanosService;

       @GetMapping("/contactanos")
    public String contactanos(Model model) {
        model.addAttribute("contactanos", contactanosService.getAllMensajes());
        model.addAttribute("contactanos", new Contactanos()); // Para el formulario
        return "vistas/invitados/contactanos"; // Devuelve la vista para usuarios invitados
    }

    @PostMapping("/contactanos")
    public String registrarContacto(@ModelAttribute Contactanos contactanos) {
        // Registrar el nuevo contacto usando el servicio
        contactanosService.registrarMensaje(contactanos);
        return "redirect:/contactanos";  // Redirige para evitar reenvío de formulario
    }

    @GetMapping("/contactanosAdmin")
    public String contactanosAdmin(Model model) {
        List<Contactanos> contactanos = contactanosService.getAllMensajes();
        model.addAttribute("contactanos", contactanos); // Convertir a ImmutableList para evitar modificaciones accidentales
        return "vistas/correo";
    }
}
