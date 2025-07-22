package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.models.Partido;
import fap_sports.integrador.services.CampeonatoService;
import fap_sports.integrador.services.PartidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AdministradorResultadosController {

    @Autowired
    private PartidoService partidoService; // Inyección del servicio de partidos

    @Autowired
    private CampeonatoService campeonatoService; // Inyección del servicio de partidos


    // Método para mostrar los partidos y registrar resultados
    @GetMapping("/verResultados")
    public String mostrarPartidosParaRegistrarResultados(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model) {

        List<Partido> todosPartidos = partidoService.listarPartidos(); // Obtiene todos los partidos registrados

        // Agrupación por fecha (solo fechas con partidos registrados)
        Map<LocalDate, List<Partido>> partidosPorFecha = todosPartidos.stream()
                .filter(p -> p.getParFecha() != null) // Filtra partidos que tienen fecha
                .collect(Collectors.groupingBy(Partido::getParFecha, LinkedHashMap::new, Collectors.toList())); // Agrupa partidos por fecha

        // Crear una lista de fechas y ordenarlas
        List<LocalDate> fechasOrdenadas = new ArrayList<>(partidosPorFecha.keySet());
        Collections.sort(fechasOrdenadas);

        // Selección automática de la fecha si no se pasó como un parámetro
        if (fecha == null) {
            if (partidosPorFecha.containsKey(LocalDate.now())) {
                fecha = LocalDate.now();
            } else if (!partidosPorFecha.isEmpty()) {
                fecha = fechasOrdenadas.get(0); // Usar la primera fecha de la lista ordenada
            }
        }

        // Obtiene los partidos seleccionados para la fecha elegida
        List<Partido> partidosSeleccionados = partidosPorFecha.getOrDefault(fecha, List.of());

        // Preparar nombres del día abreviados en español
        @SuppressWarnings("deprecation")
        Locale spanish = new Locale("es", "ES"); // Define la localización para español
        DateTimeFormatter diaAbreviadoFormatter = DateTimeFormatter.ofPattern("E", spanish); // Formato de día abreviado
        
        // Mapa para almacenar los días de la semana
        Map<LocalDate, String> diasSemanaMap = new LinkedHashMap<>();
        for (LocalDate f : fechasOrdenadas) {
            diasSemanaMap.put(f, f.format(diaAbreviadoFormatter)); // Agrega la fecha y su día abreviado al mapa
        }

        // Agrega atributos al modelo para ser utilizados en la vista
        model.addAttribute("partidosPorFecha", partidosPorFecha);
        model.addAttribute("diasSemanaMap", diasSemanaMap);
        model.addAttribute("fechasOrdenadas", fechasOrdenadas); // Agregar la lista de fechas ordenadas
        model.addAttribute("fechaSeleccionada", fecha);
        model.addAttribute("partidos", partidosSeleccionados);
        model.addAttribute("hoy", LocalDate.now());

        return "vistas/administrador/resultados/verResultados"; // Ruta de la vista para registrar resultados en miembroMesa
    }
    
    @GetMapping("/guardarResultadosAdm")
    public String mostrarGuardarResultados(@RequestParam Long partidoId, Model model) {
        // Lógica para obtener el partido y pasarlo al modelo
        Partido partido = partidoService.obtenerPartidoPorId(partidoId)
                .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado"));
        model.addAttribute("partido", partido);
        return "vistas/administrador/resultados/guardarResultados"; // Redirecciona a la vista para guardar resultados
    }
    @PostMapping("/guardarResultadosAdm")
    public String guardarResultados(@RequestParam Long partidoId,
                                                @RequestParam Integer puntajeLocal,
                                                @RequestParam Integer puntajeVisitante,
                                                @RequestParam String parEstado) {
        // Lógica para guardar los resultados
        partidoService.guardarResultados(partidoId, puntajeLocal, puntajeVisitante, parEstado);
        
        // Redirige a la página de registrarResultados después de guardar
        return "redirect:/verResultados";
    }

    @GetMapping("/tablaPosicionesAdm")
    public String mostrarTablaPosiciones(@RequestParam Long campeonatoId, Model model) {
        List<Map<String, Object>> tabla = partidoService.obtenerTablaClasificacion(campeonatoId);
        model.addAttribute("tabla", tabla);

        Campeonato campeonato = campeonatoService.buscarPorId(campeonatoId);
        model.addAttribute("campeonato", campeonato);

        return "vistas/administrador/resultados/tablaPosicionesAdm";
    }



}