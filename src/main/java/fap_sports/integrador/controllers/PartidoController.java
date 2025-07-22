package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Partido;
import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.services.CampeonatoService;
import fap_sports.integrador.services.EquipoService;
import fap_sports.integrador.services.PartidoService;
import fap_sports.integrador.repositories.PartidoRepository;
import fap_sports.integrador.repositories.CampeonatoRepository;
import fap_sports.integrador.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;

// Clase controladora para manejar las operaciones relacionadas con partidos
@Controller
public class PartidoController {

    @Autowired
    private PartidoService partidoService; // Servicio para manejar lógica de partidos

    @Autowired
    private EquipoService equipoService; // Servicio para manejar lógica de equipos

    @Autowired
    private PartidoRepository partidoRepository; // Repositorio de partidos

    @Autowired
    private EquipoRepository equipoRepository; // Repositorio de equipos

    @Autowired
    private CampeonatoRepository campeonatoRepository; // Repositorio de campeonatos

    @Autowired
    private CampeonatoService campeonatoService; // Servicio para manejar lógica de campeonatos

    // Muestra la lista de partidos y el formulario
    @GetMapping("/partidos")
    public String formularioPartidos(Model model) {
        Map<Campeonato, List<Partido>> partidosPorCampeonato = new LinkedHashMap<>();
        List<Campeonato> campeonatos = campeonatoRepository.findAll(); // Obtiene todos los campeonatos

        for (Campeonato campeonato : campeonatos) {
            List<Partido> partidos = partidoRepository.findByCampeonato(campeonato); // Obtiene partidos por campeonato
            if (!partidos.isEmpty()) {
                partidosPorCampeonato.put(campeonato, partidos); // Agrupa partidos por campeonato
            }
        }

        model.addAttribute("partidosPorCampeonato", partidosPorCampeonato); // Agrega partidos agrupados al modelo
        return "vistas/administrador/partido/partidos"; // Retorna la vista de partidos
    }

    // Realiza el sorteo de partidos para un campeonato específico
    @PostMapping("/partidos/sorteo")
    public String realizarSorteo(@RequestParam("campeonatoId") Long campeonatoId,
                                  @RequestParam("equiposSeleccionados") List<Long> equiposSeleccionados,
                                  Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Equipo> equipos = equipoService.obtenerEquiposPorIds(equiposSeleccionados); // Obtiene equipos seleccionados
            partidoService.realizarSorteoPorCampeonato(campeonatoId, equipos); // Realiza el sorteo
            redirectAttributes.addAttribute("success", "Sorteo generado correctamente");
            return "redirect:/campeonatos"; // Redirige a la lista de campeonatos
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "No se puede generar el sorteo porque la cantidad de equipos es impar");
            return "redirect:/campeonatos"; // Retorna vista de campeonatos en caso de error
        }
    }

    // Mostrar formulario para crear un nuevo partido
    @GetMapping("/partidos/nuevo")
    public String nuevoPartido(Model model) {
        model.addAttribute("partido", new Partido()); // Crea un nuevo objeto Partido
        model.addAttribute("equipos", equipoRepository.findAll()); // Agrega todos los equipos al modelo
        return "vistas/partidoFormulario"; // Retorna la vista del formulario de partido
    }

    // Mostrar formulario de edición de fecha y hora de un partido
    @GetMapping("/editarPartido/{id}")
    public String mostrarFormularioEdicionPartido(@PathVariable Long id, Model model) {
        Partido partido = partidoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de partido inválido: " + id)); // Manejo de errores

        model.addAttribute("partido", partido); // Agrega el partido al modelo
        return "vistas/administrador/partido/editarPartido"; // Retorna la vista de edición
    }

    // Guardar cambios de fecha y hora de un partido
    @PostMapping("/actualizarPartido")
    public String actualizarPartido(@ModelAttribute Partido partidoForm) {
        Partido partidoExistente = partidoRepository.findById(partidoForm.getParId())
            .orElseThrow(() -> new IllegalArgumentException("ID de partido inválido")); // Manejo de errores

        partidoExistente.setParFecha(partidoForm.getParFecha()); // Actualiza la fecha
        partidoExistente.setParHora(partidoForm.getParHora()); // Actualiza la hora

        partidoRepository.save(partidoExistente); // Guarda los cambios

        return "redirect:/partidos/campeonato/" + partidoExistente.getCampeonato().getCamId(); // Redirige a los partidos del campeonato
    }

    // Elimina un partido por ID
    @PostMapping("/eliminarPartido/{id}")
    public String eliminarPartido(@PathVariable Long id) {
        partidoRepository.deleteById(id); // Elimina el partido
        return "redirect:/partidos"; // Redirige a la lista de partidos
    }

    // Redirige a la página de inicio
    @GetMapping("/")
    public String redirigirInicio() {
        return "redirect:/ultimosPartidos"; // Redirige a los últimos partidos
    }

    // Muestra los últimos partidos
    @GetMapping("/ultimosPartidos")
    public String mostrarInicio(Model model) {
        List<Partido> ultimosPartidos = partidoService.obtenerUltimosPartidos(); // Obtiene los últimos partidos
        model.addAttribute("ultimosPartidos", ultimosPartidos); // Agrega al modelo
        return "vistas/invitado"; // Retorna la vista para invitados
    }

    // Lista partidos por campeonato específico
    @GetMapping("/partidos/campeonato/{id}")
    public String listarPartidosPorCampeonato(@PathVariable("id") Long campeonatoId, Model model) {
        Campeonato campeonato = campeonatoService.obtenerCampeonatoPorId(campeonatoId).orElseThrow(); // Obtiene el campeonato
        List<Partido> partidos = partidoService.obtenerPartidosPorCampeonatoId(campeonatoId); // Obtiene partidos del campeonato

        model.addAttribute("soloUnCampeonato", true); // Bandera para indicar un solo campeonato
        model.addAttribute("campeonato", campeonato); // Agrega el campeonato al modelo
        model.addAttribute("partidos", partidos); // Agrega partidos al modelo

        return "vistas/administrador/partido/partidos"; // Retorna la vista de partidos
    }
}