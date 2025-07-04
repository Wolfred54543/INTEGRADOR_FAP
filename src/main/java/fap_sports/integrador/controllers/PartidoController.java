package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Partido;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.services.PartidoService;
import fap_sports.integrador.repositories.PartidoRepository;
import fap_sports.integrador.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    // Muestra la lista de partidos y el formulario
    @GetMapping("/partidos")
    public String formularioPartidos(Model model) {
        List<Partido> partidos = partidoRepository.findAll();
        List<Equipo> equipos = equipoRepository.findAll();
        model.addAttribute("partidos", partidos);
        model.addAttribute("equipos", equipos);
        return "vistas/partidos";
    }

    // Realizar sorteo de partidos
    @PostMapping("/partidos/sorteo")
    public String realizarSorteo(Model model) {
        try {
            partidoService.realizarSorteo();
            return "redirect:/partidos";
        } catch (Exception e) {
            model.addAttribute("error", "Error al realizar el sorteo: " + e.getMessage());
            return "vistas/partidos";
        }
    }

    // Mostrar formulario para crear nuevo partido
    @GetMapping("/partidos/nuevo")
    public String nuevoPartido(Model model) {
        model.addAttribute("partido", new Partido());
        model.addAttribute("equipos", equipoRepository.findAll());
        return "vistas/partidoFormulario";
    }

    // Mostrar formulario de edición de fecha y hora
    @GetMapping("/editarPartido/{id}")
    public String mostrarFormularioEdicionPartido(@PathVariable Long id, Model model) {
        Partido partido = partidoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de partido inválido: " + id));

        model.addAttribute("partido", partido);
        return "vistas/editarPartido";
    }

    // Guardar cambios de fecha y hora
    @PostMapping("/actualizarPartido")
    public String actualizarPartido(@ModelAttribute Partido partidoForm) {
        Partido partidoExistente = partidoRepository.findById(partidoForm.getParId())
            .orElseThrow(() -> new IllegalArgumentException("ID de partido inválido"));

        partidoExistente.setParFecha(partidoForm.getParFecha());
        partidoExistente.setParHora(partidoForm.getParHora());

        partidoRepository.save(partidoExistente);

        return "redirect:/partidos";
    }

    @PostMapping("/eliminarPartido/{id}")
    public String eliminarPartido(@PathVariable Long id) {
        partidoRepository.deleteById(id);
        return "redirect:/partidos";
    }

    @GetMapping("/")
    public String redirigirInicio() {
        return "redirect:/ultimosPartidos";
    }

    @GetMapping("/ultimosPartidos")
    public String mostrarInicio(Model model) {
        List<Partido> ultimosPartidos = partidoService.obtenerUltimosPartidos();
        model.addAttribute("ultimosPartidos", ultimosPartidos);
        return "vistas/invitado";
    }



}
