package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Partido;
import fap_sports.integrador.models.Reclamo;
import fap_sports.integrador.services.PartidoService;
import fap_sports.integrador.services.ReclamoService;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional; // Importa la clase Optional
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ReclamoController {

      private final DelegadoController delegadoController;
    private static final Logger logger = LoggerFactory.getLogger(ReclamoController.class);
    private final ReclamoService reclamoService;
    private final PartidoService partidoService; // Servicio para obtener partidos

    public ReclamoController(ReclamoService reclamoService, DelegadoController delegadoController, PartidoService partidoService) {
        this.reclamoService = reclamoService;
        this.delegadoController = delegadoController;
        this.partidoService = partidoService;
    }

    // Muestra el formulario para registrar un nuevo reclamo
    @GetMapping("/reclamoFormulario")
    public String mostrarReclamoForm(Model model) {
        List<Partido> partidos = partidoService.listarPartidos(); // Obtener la lista de partidos
        model.addAttribute("partidos", partidos); // Pasar la lista de partidos al modelo
        return "vistas/reclamoFormulario";
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
            if (motivo.isBlank() || descripcion.isBlank() || solicitud.isBlank() || partidoId == null) {
                model.addAttribute("error", "Todos los campos son obligatorios.");
                return "vistas/reclamoFormulario";
            }

            // Obtener el partido de referencia
            Optional<Partido> optionalPartido = partidoService.obtenerPartidoPorId(partidoId);

            if (optionalPartido.isEmpty()) {
                model.addAttribute("error", "Partido no encontrado.");
                return "vistas/reclamoFormulario";
            }

            Partido partidoReferencia = optionalPartido.get(); // Obtener el objeto Partido

            // Crear el nuevo reclamo
            Reclamo nuevoReclamo = new Reclamo(motivo, descripcion, solicitud, fecha, hora, partidoReferencia);
            reclamoService.registrarReclamo(nuevoReclamo);
            model.addAttribute("mensaje", "Reclamo registrado con éxito");

        } catch (Exception e) {
            logger.error("Error al registrar el reclamo", e);
            model.addAttribute("error", "Error al registrar el reclamo: " + e.getMessage());
        }

        return "vistas/reclamoFormulario";
    }

    // Muestra historial de reclamos al usuario
    @GetMapping("/reclamoHistorial")
    public String listarReclamos(@RequestParam(value = "estado", required = false) String estado, Model model) {
        List<Reclamo> reclamos;

        if (estado == null || estado.isBlank()) {
            reclamos = reclamoService.listarReclamos(); // listar todos los reclamos
        } else {
            reclamos = reclamoService.obtenerReclamosPorEstado(estado); // determinar el estado
        }

        model.addAttribute("reclamos", reclamos);
        model.addAttribute("estado", estado);
        return "vistas/reclamoHistorial";
    }


    // Vista del administrador para ver y responder reclamos
    @GetMapping("/reclamoRespuesta")
    public String mostrarReclamosParaResponder(Model model) {
        List<Reclamo> reclamos = reclamoService.listarReclamos(); // Sin filtro
        model.addAttribute("reclamos", reclamos);
        return "vistas/reclamoRespuesta";
    }


    // Procesa la respuesta del administrador al reclamo
    @PostMapping("/responderReclamo")
    public String responderReclamo(
            @RequestParam("id") Long id,
            @RequestParam("respuesta") String respuesta,
            Model model) {

        try {
            reclamoService.responderReclamo(id, respuesta);
            model.addAttribute("mensaje", "Reclamo respondido correctamente.");
        } catch (Exception e) {
            logger.error("Error al responder el reclamo", e);
            model.addAttribute("error", "Error al responder el reclamo: " + e.getMessage());
        }

        return "redirect:/reclamoRespuesta";
    }

    // Permite al administrador eliminar reclamos
    @PostMapping("/eliminarReclamo")
    public String eliminarReclamo(@RequestParam("id") Long id, Model model) {
        try {
            reclamoService.eliminarReclamo(id);
        } catch (Exception e) {
            logger.error("Error al eliminar el reclamo", e);
        }
        return "redirect:/reclamoRespuesta";
    }

    @GetMapping("/reclamoFormularioRespuesta/{id}")
    public String mostrarFormularioRespuesta(@PathVariable Long id, Model model) {
        Reclamo reclamo = reclamoService.obtenerReclamoPorId(id); // Obtener el id del reclamo de la base de datos
        if (reclamo == null) {
            model.addAttribute("error", "No se encontró el reclamo.");
            return "redirect:/reclamoRespuesta";
        }
        model.addAttribute("reclamo", reclamo);
        return "vistas/reclamoFormularioRespuesta";
    }

}
