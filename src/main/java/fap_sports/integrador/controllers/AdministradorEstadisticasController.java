package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Incidencia;
import fap_sports.integrador.models.Jugador;
import fap_sports.integrador.models.Partido;
import fap_sports.integrador.services.IncidenciaService;
import fap_sports.integrador.services.JugadorService;
import fap_sports.integrador.services.PartidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdministradorEstadisticasController {

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private JugadorService jugadorService;

    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping("/partidosFecha")
    public String mostrarPartidosParaRegistrarIncidencias(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) Long parId,
            Model model) {

        List<Partido> todosPartidos = partidoService.listarPartidos();

        Map<LocalDate, List<Partido>> partidosPorFecha = todosPartidos.stream()
                .filter(p -> p.getParFecha() != null)
                .collect(Collectors.groupingBy(Partido::getParFecha, LinkedHashMap::new, Collectors.toList()));

        List<LocalDate> fechasOrdenadas = new ArrayList<>(partidosPorFecha.keySet());
        Collections.sort(fechasOrdenadas);

        if (fecha == null) {
            if (partidosPorFecha.containsKey(LocalDate.now())) {
                fecha = LocalDate.now();
            } else if (!fechasOrdenadas.isEmpty()) {
                fecha = fechasOrdenadas.get(0);
            }
        }

        List<Partido> partidosSeleccionados = partidosPorFecha.getOrDefault(fecha, List.of());

        // Día abreviado (en español)
        @SuppressWarnings("deprecation")
        Locale spanish = new Locale("es", "ES");
        DateTimeFormatter diaAbreviadoFormatter = DateTimeFormatter.ofPattern("E", spanish);
        Map<LocalDate, String> diasSemanaMap = new LinkedHashMap<>();
        for (LocalDate f : fechasOrdenadas) {
            diasSemanaMap.put(f, f.format(diaAbreviadoFormatter));
        }

        // Incidencias del partido seleccionado (si hay)
        List<Incidencia> incidenciasFiltradas = new ArrayList<>();
        if (parId != null) {
            incidenciasFiltradas = incidenciaService.listarPorPartido(parId); // Asumes que este método existe
        }

        model.addAttribute("partidosPorFecha", partidosPorFecha);
        model.addAttribute("diasSemanaMap", diasSemanaMap);
        model.addAttribute("fechasOrdenadas", fechasOrdenadas);
        model.addAttribute("fechaSeleccionada", fecha);
        model.addAttribute("partidos", partidosSeleccionados);
        model.addAttribute("parIdSeleccionado", parId); // para mantener el partido seleccionado
        model.addAttribute("incidenciasFiltradas", incidenciasFiltradas);
        model.addAttribute("hoy", LocalDate.now());

        return "vistas/administrador/estadisticas/partidosFecha";
    }


    // Mostrar formulario de registro de incidencias + listar incidencias existentes
    @GetMapping("/verIncidencias")
    public String mostrarFormularioIncidencia(
            @RequestParam Long partidoId,
            @RequestParam(required = false) String tipo, // <-- nuevo filtro
            Model model) {

        Partido partido = partidoService.obtenerPartidoPorId(partidoId)
                .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado"));

        model.addAttribute("partido", partido);
        model.addAttribute("equipoLocal", partido.getEquipoLocal());
        model.addAttribute("equipoVisitante", partido.getEquipoVisitante());
        model.addAttribute("jugadoresLocal", partido.getEquipoLocal().getJugadores());
        model.addAttribute("jugadoresVisitante", partido.getEquipoVisitante().getJugadores());

        List<Incidencia> incidencias = incidenciaService.listarPorPartido(partidoId);
        if (tipo != null && !tipo.isEmpty()) {
            incidencias = incidencias.stream()
                    .filter(i -> i.getTipo().equalsIgnoreCase(tipo))
                    .collect(Collectors.toList());
        }

        model.addAttribute("incidenciasFiltradas", incidencias);
        model.addAttribute("tipoSeleccionado", tipo); // para mantener el valor en el select

        return "vistas/administrador/estadisticas/verIncidencias";
    }

    // Guardar incidencia del jugador en un partido (desde formulario)
    @PostMapping("/verIncidencias")
    public String registrarIncidencia(
            @RequestParam("jugadorId") Long jugadorId,
            @RequestParam("partidoId") Long partidoId,
            @RequestParam("tipoIncidencia") String tipoIncidencia) {

        Jugador jugador = jugadorService.getJugadorById(jugadorId);
        Partido partido = partidoService.obtenerPartidoPorId(partidoId)
                .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado"));

        Incidencia incidencia = new Incidencia();
        incidencia.setJugador(jugador);
        incidencia.setPartido(partido);
        incidencia.setTipo(tipoIncidencia);
        incidencia.setCantidad(1);
        incidencia.setFechaRegistro(LocalDate.now());
        incidencia.setDescripcion("Incidencia de tipo " + tipoIncidencia);

        incidenciaService.guardar(incidencia);

        return "redirect:/verIncidencias?partidoId=" + partidoId;
    }
}
