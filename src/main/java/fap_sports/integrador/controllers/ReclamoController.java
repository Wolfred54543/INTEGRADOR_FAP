package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Partido;
import fap_sports.integrador.models.Reclamo;
import fap_sports.integrador.services.PartidoService;
import fap_sports.integrador.services.ReclamoService;
//import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional; // Importa la clase Optional
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



// Clase controladora para manejar los reclamos
@Controller
public class ReclamoController {

    //private final DelegadoController delegadoController; Controlador para manejar delegados no olvidar inyectarlo en el constructor de dependencias
    private static final Logger logger = LoggerFactory.getLogger(ReclamoController.class); // Logger para registrar eventos
    private final ReclamoService reclamoService; // Servicio para manejar reclamos
    private final PartidoService partidoService; // Servicio para obtener partidos

    // Constructor para inyección de dependencias
    public ReclamoController(ReclamoService reclamoService, PartidoService partidoService) {
        this.reclamoService = reclamoService;
        this.partidoService = partidoService;
    }

    // Muestra el formulario para registrar un nuevo reclamo
    @GetMapping("/reclamoFormulario")
    public String mostrarReclamoForm(Model model) {
        List<Partido> partidos = partidoService.listarPartidos(); // Obtener la lista de partidos
        model.addAttribute("partidos", partidos); // Pasar la lista de partidos al modelo
        return "vistas/delegado/reclamoFormulario"; // Retorna la vista del formulario de reclamo
    }

    // Procesa el registro del reclamo
    @PostMapping("/reclamoFormulario")
    public String registrarReclamo(
            @RequestParam("motivo") String motivo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("solicitud") String solicitud,
            @RequestParam("partidoReferencia") Long partidoId,
            @RequestParam("fecha") LocalDate fecha,
            @RequestParam("hora") LocalTime hora,
            Model model) {

        try {
            // Validación de campos obligatorios
            if (motivo.isBlank() || descripcion.isBlank() || solicitud.isBlank() || partidoId == null) {
                model.addAttribute("error", "Todos los campos son obligatorios.");
                return "vistas/delegado/reclamoFormulario"; // Retorna el formulario en caso de error
            }

            // Obtener el partido de referencia
            Optional<Partido> optionalPartido = partidoService.obtenerPartidoPorId(partidoId);

            if (optionalPartido.isEmpty()) {
                model.addAttribute("error", "Partido no encontrado.");
                return "vistas/delegado/reclamoFormulario"; // Retorna el formulario si no se encuentra el partido
            }

            Partido partidoReferencia = optionalPartido.get(); // Obtener el objeto Partido

            // Crear el nuevo reclamo
            Reclamo nuevoReclamo = new Reclamo(motivo, descripcion, solicitud, fecha, hora, partidoReferencia);
            reclamoService.registrarReclamo(nuevoReclamo); // Registrar el reclamo
            model.addAttribute("mensaje", "Reclamo registrado con éxito");

        } catch (Exception e) {
            logger.error("Error al registrar el reclamo", e); // Log del error
            model.addAttribute("error", "Error al registrar el reclamo: " + e.getMessage()); // Mensaje de error
        }

        return "vistas/delegado/reclamoFormulario"; // Retorna el formulario después de intentar registrar
    }

    // Muestra historial de reclamos al usuario
    @GetMapping("/reclamoHistorial")
    public String listarReclamos(@RequestParam(value = "estado", required = false) String estado, Model model) {
        List<Reclamo> reclamos;

        // Listar reclamos según el estado
        if (estado == null || estado.isBlank()) {
            reclamos = reclamoService.listarReclamos(); // listar todos los reclamos
        } else {
            reclamos = reclamoService.obtenerReclamosPorEstado(estado); // Filtrar por estado
        }

        model.addAttribute("reclamos", reclamos); // Agrega la lista de reclamos al modelo
        model.addAttribute("estado", estado); // Agrega el estado al modelo
        return "vistas/delegado/reclamoHistorial"; // Retorna la vista del historial de reclamos
    }

    

    // Vista del administrador para ver y responder reclamos
    @GetMapping("/reclamoRespuesta")
    public String mostrarReclamosParaResponder(Model model) {
        List<Reclamo> reclamos = reclamoService.listarReclamos(); // Sin filtro
        model.addAttribute("reclamos", reclamos); // Agrega reclamos al modelo
        return "vistas/administrador/reclamo/reclamoRespuesta"; // Retorna la vista de respuesta a reclamos
    }

    // Procesa la respuesta del administrador al reclamo
    @PostMapping("/responderReclamo")
    public String responderReclamo(
            @RequestParam("id") Long id,
            @RequestParam("respuesta") String respuesta,
            Model model) {

        try {
            reclamoService.responderReclamo(id, respuesta); // Responde al reclamo
            model.addAttribute("mensaje", "Reclamo respondido correctamente.");
        } catch (Exception e) {
            logger.error("Error al responder el reclamo", e); // Log del error
            model.addAttribute("error", "Error al responder el reclamo: " + e.getMessage()); // Mensaje de error
        }

        return "redirect:/reclamoRespuesta"; // Redirige a la vista de respuesta a reclamos
    }

    // Permite al administrador eliminar reclamos
    @PostMapping("/eliminarReclamo")
    public String eliminarReclamo(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes ) {
        try {
            reclamoService.eliminarReclamo(id);
            redirectAttributes.addAttribute("success", "Reclamo eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "No se puede eliminar el reclamo");
        }
        return "redirect:/reclamoRespuesta"; // Redirige a la vista de respuesta a reclamos
    }

    // Muestra el formulario de respuesta para un reclamo específico
    @GetMapping("/reclamoFormularioRespuesta/{id}")
    public String mostrarFormularioRespuesta(@PathVariable Long id, Model model) {
        Reclamo reclamo = reclamoService.obtenerReclamoPorId(id); // Obtener el reclamo por ID
        if (reclamo == null) {
            model.addAttribute("error", "No se encontró el reclamo."); // Manejo de error si no se encuentra
            return "redirect:/reclamoRespuesta"; // Redirige si no se encuentra el reclamo
        }
        model.addAttribute("reclamo", reclamo); // Agrega el reclamo al modelo
        return "vistas/administrador/reclamo/reclamoFormularioRespuesta"; // Retorna la vista del formulario de respuesta
    }
}