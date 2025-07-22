package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Partido;
import fap_sports.integrador.services.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

// Clase controladora para manejar los partidos visibles para los invitados
@Controller
public class InvitadoPartidoController {

    @Autowired
    private PartidoService partidoService; // Inyección del servicio de partidos

    // Método para mostrar los partidos en un calendario
    @GetMapping("/partidoInvitados")
    public String mostrarPartidosConCalendario(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model) {

        List<Partido> todosPartidos = partidoService.listarPartidos(); // Obtiene todos los partidos registrados
        
        // Agrupación por fecha (solo fechas con partidos registrados)
        Map<LocalDate, List<Partido>> partidosPorFecha = todosPartidos.stream()
                .filter(p -> p.getParFecha() != null) // Filtra partidos que tienen fecha
                .collect(Collectors.groupingBy(Partido::getParFecha, LinkedHashMap::new, Collectors.toList())); // Agrupa partidos por fecha

        // Selección automática de la fecha si no se pasó como un parámetro
        if (fecha == null) {
            // Si hay partidos para la fecha actual, se selecciona esa fecha
            if (partidosPorFecha.containsKey(LocalDate.now())) {
                fecha = LocalDate.now();
            } else if (!partidosPorFecha.isEmpty()) {
                // Si no, selecciona la primera fecha disponible
                fecha = partidosPorFecha.keySet().stream().sorted().findFirst().orElse(null);
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
        for (LocalDate f : partidosPorFecha.keySet()) {
            diasSemanaMap.put(f, f.format(diaAbreviadoFormatter)); // Agrega la fecha y su día abreviado al mapa
        }

        // Agrega atributos al modelo para ser utilizados en la vista
        model.addAttribute("partidosPorFecha", partidosPorFecha);       // Muestra el listado de partidos correspondientes a su fecha
        model.addAttribute("diasSemanaMap", diasSemanaMap);             // Muestra los días de la semana
        model.addAttribute("fechaSeleccionada", fecha);                 // Fecha seleccionada
        model.addAttribute("partidos", partidosSeleccionados);          // Lista de partidos del día
        model.addAttribute("hoy", LocalDate.now());                     // Resalta la fecha actual

        return "vistas/invitados/partidoInvitados"; // Ruta de la vista del formulario 
    }
}