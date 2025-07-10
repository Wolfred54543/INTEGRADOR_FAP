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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private EquipoService equipoService;


    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    @Autowired
    private CampeonatoService campeonatoService;

    // Muestra la lista de partidos y el formulario
    @GetMapping("/partidos")
    public String formularioPartidos(Model model) {
        Map<Campeonato, List<Partido>> partidosPorCampeonato = new LinkedHashMap<>();
        List<Campeonato> campeonatos = campeonatoRepository.findAll();

        for (Campeonato campeonato : campeonatos) {
            List<Partido> partidos = partidoRepository.findByCampeonato(campeonato);
            if (!partidos.isEmpty()) {
                partidosPorCampeonato.put(campeonato, partidos);
            }
        }

        model.addAttribute("partidosPorCampeonato", partidosPorCampeonato);
        return "vistas/partidos"; // Asegúrate de que esta sea tu vista correcta
    }


@PostMapping("/partidos/sorteo")
public String realizarSorteo(@RequestParam("campeonatoId") Long campeonatoId,
                             @RequestParam("equiposSeleccionados") List<Long> equiposSeleccionados,
                             Model model) {
    try {
        List<Equipo> equipos = equipoService.obtenerEquiposPorIds(equiposSeleccionados);
        partidoService.realizarSorteoPorCampeonato(campeonatoId, equipos);
        return "redirect:/campeonatos";
    } catch (Exception e) {
        model.addAttribute("error", "Error al realizar el sorteo: " + e.getMessage());
        return "vistas/campeonatos";
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

        return "redirect:/partidos/campeonato/" + partidoExistente.getCampeonato().getCamId();
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

    @GetMapping("/partidos/campeonato/{id}")
    public String listarPartidosPorCampeonato(@PathVariable("id") Long campeonatoId, Model model) {
        Campeonato campeonato = campeonatoService.obtenerCampeonatoPorId(campeonatoId).orElseThrow();
        List<Partido> partidos = partidoService.obtenerPartidosPorCampeonatoId(campeonatoId);

        model.addAttribute("soloUnCampeonato", true); // bandera para saber si solo mostrar uno
        model.addAttribute("campeonato", campeonato);
        model.addAttribute("partidos", partidos);

        return "vistas/partidos"; // Reutiliza la misma vista
    }



}
