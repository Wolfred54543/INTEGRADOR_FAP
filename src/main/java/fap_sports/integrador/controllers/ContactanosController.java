package fap_sports.integrador.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fap_sports.integrador.models.Contactanos;
import fap_sports.integrador.services.ContactanosService;

// Clase controladora para manejar los mensajes de contacto
@Controller
public class ContactanosController {

    @Autowired
    private ContactanosService contactanosService; // Servicio para manejar la lógica de mensajes de contacto

    // Método para mostrar el formulario de contacto y los mensajes recibidos
    @GetMapping("/contactanos")
    public String contactanos(Model model) {
        model.addAttribute("contactanos", contactanosService.getAllMensajes()); // Obtiene todos los mensajes de contacto
        model.addAttribute("contactanos", new Contactanos()); // Agrega un nuevo objeto Contactanos al modelo para el formulario
        return "vistas/invitados/contactanos"; // Devuelve la vista para usuarios invitados
    }

    // Método para registrar un nuevo mensaje de contacto
    @PostMapping("/contactanos")
    public String registrarContacto(@ModelAttribute Contactanos contactanos) {
        // Registrar el nuevo contacto usando el servicio
        contactanosService.registrarMensaje(contactanos); // Llama al servicio para registrar el mensaje
        return "redirect:/contactanos";  // Redirige para evitar reenvío de formulario
    }

    // Método para mostrar los mensajes de contacto en la vista del administrador
    @GetMapping("/contactanosAdmin")
    public String contactanosAdmin(Model model) {
        List<Contactanos> contactanos = contactanosService.getAllMensajes(); // Obtiene todos los mensajes de contacto
        model.addAttribute("contactanos", contactanos); // Agrega la lista de mensajes al modelo
        return "vistas/administrador/correo/correo"; // Retorna la vista para el administrador
    }
}