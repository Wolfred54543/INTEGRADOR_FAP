package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.models.Jugador;
import fap_sports.integrador.services.JugadorService;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Clase controladora para manejar las operaciones relacionadas con los jugadores
@Controller
public class JugadorController {

   @Autowired
   private JugadorService jugadorService; // Inyección del servicio de jugadores

   // Muestra el formulario para crear o listar jugadores, junto con equipos y décadas disponibles
   @GetMapping({"/formularioJugadores"})
   public String formularioJugadores( @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(name = "buscar", required = false) String buscar,
        Model model) {

         Page<Jugador> jugadoresPaginados;

         if (buscar != null && !buscar.isEmpty()) {
            jugadoresPaginados = jugadorService.buscarPorNombreApellidoODni(buscar, PageRequest.of(page, size));
            model.addAttribute("buscar", buscar);
         } else {
            jugadoresPaginados = jugadorService.listarTodosPaginados(PageRequest.of(page, size));
         }

         model.addAttribute("jugadores", jugadoresPaginados.getContent());
         model.addAttribute("paginaActual", page);
         model.addAttribute("totalPaginas", jugadoresPaginados.getTotalPages());
         model.addAttribute("tamañoPagina", size);

         // Datos para el formulario
         model.addAttribute("jugador", new Jugador());
         model.addAttribute("equipos", jugadorService.getAllEquipos());
         model.addAttribute("decadas", jugadorService.getAllDecadas());

         return "vistas/administrador/jugador/jugadores";
      }

   // Procesa el registro de un nuevo jugador y redirige al formulario para refrescar la lista
   @PostMapping({"/registrarJugadores"})
   public String registrarJugadores(@ModelAttribute Jugador jugador, Model model) {
      this.jugadorService.registrarJugador(jugador); // Registra el nuevo jugador
      return "redirect:/formularioJugadores"; // Redirige al formulario de jugadores
   }

   // Muestra el formulario para asignar un equipo a un jugador específico
   @GetMapping({"/asignarEquipo"})
   public String mostrarAsignarEquipo(@RequestParam Long jugadorId, Model model) {
      Jugador jugador = this.jugadorService.getJugadorById(jugadorId); // Obtiene el jugador por ID
      model.addAttribute("jugador", jugador); // Agrega el jugador al modelo

      // Trae equipos según el año de inicio del jugador
      int anioInicioJugador = jugador.getDecada().getAnioInicio();
      model.addAttribute("equipos", this.jugadorService.getEquiposPorAnioInicio(anioInicioJugador)); // Lista de equipos
      return "vistas/administrador/jugador/asignarEquipo"; // Retorna la vista para asignar equipo
   }

   // Guarda la asignación de equipo al jugador, validando que ambos compartan la misma década
   @PostMapping({"/guardarEquipo"})
   public String guardarEquipo(@RequestParam Long jugadorId, @RequestParam Long equipoId, Model model) {
      Jugador jugador = this.jugadorService.getJugadorById(jugadorId); // Obtiene el jugador
      Equipo equipo = this.jugadorService.getEquipoById(equipoId); // Obtiene el equipo

      int anioInicioJugador = jugador.getDecada().getAnioInicio(); // Año de inicio del jugador
      int anioInicioEquipo = equipo.getDecada().getAnioInicio(); // Año de inicio del equipo

      if (anioInicioJugador != anioInicioEquipo) {
         // Error si las décadas no coinciden, devuelve a la vista con mensaje de error
         model.addAttribute("errorMessage", "El año de inicio del jugador no coincide con el del equipo.");
         model.addAttribute("jugador", jugador);
         model.addAttribute("equipos", this.jugadorService.getEquiposPorAnioInicio(anioInicioJugador));
         return "vistas/asignarEquipo"; // Retorna a la vista de asignación de equipo
      } else {
         jugador.setEquipo(equipo); // Asigna el equipo al jugador
         this.jugadorService.registrarJugador(jugador); // Actualiza el jugador
         return "redirect:/formularioJugadores"; // Redirige al formulario de jugadores
      }
   }

   // Elimina un jugador por su ID y redirige al listado actualizado
   @PostMapping("/eliminarJugador/{id}")
    public String eliminarJugador(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            jugadorService.eliminarJugador(id);
            redirectAttributes.addAttribute("success", "Jugador eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "No se puede eliminar el jugador porque esta tiene un registro de goles marcados en un campeonato");
        }
        return "redirect:/formularioJugadores";
    }


   // Muestra el formulario para editar un jugador existente
   @GetMapping("/editarJugador/{id}")
   public String editarJugador(@PathVariable Long id, Model model) {
      Jugador jugador = jugadorService.getJugadorById(id); // Obtiene el jugador por ID
      model.addAttribute("jugador", jugador); // Agrega el jugador al modelo
      model.addAttribute("decadas", jugadorService.getAllDecadas()); // Agrega las décadas al modelo
      model.addAttribute("equipos", jugadorService.getAllEquipos()); // Agrega los equipos al modelo
      return "vistas/administrador/jugador/editarJugador"; // Retorna la vista de edición de jugador
   }

   // Procesa la actualización de los datos de un jugador
   @PostMapping("/actualizarJugador")
   public String actualizarJugador(@ModelAttribute Jugador jugador) {
      jugadorService.actualizarJugador(jugador); // Actualiza el jugador
      return "redirect:/formularioJugadores"; // Redirige al formulario de jugadores
   }

   // Vista de jugadores para los invitados
   @GetMapping("/jugadores")
   public String mostrarJugadoresAgrupados(Model model) {
      List<Jugador> jugadores = jugadorService.getAllJugadores()
            .stream()
            .filter(j -> j.getDecada() != null && j.getEquipo() != null) // Filtra jugadores con década y equipo
            .collect(Collectors.toList());

      // Agrupar jugadores por "Década Año de Inicio y Mes"
      Map<String, List<Jugador>> jugadoresPorGrupo = jugadores.stream()
         .collect(Collectors.groupingBy(j -> {
               Decada d = j.getDecada();
               return "Década " + d.getAnioInicio() + " " + d.getDecMes(); // Agrupa por década y mes
      }));

      // Ordenar el mapa por año y luego por mes
      Map<String, List<Jugador>> jugadoresOrdenados = jugadoresPorGrupo.entrySet().stream()
         .sorted((e1, e2) -> {
               String[] parts1 = e1.getKey().split(" ");
               String[] parts2 = e2.getKey().split(" ");
               int anio1 = Integer.parseInt(parts1[1]); // Año del primer grupo
               int anio2 = Integer.parseInt(parts2[1]); // Año del segundo grupo

               // Asignar el mes a un número (ej: Enero → 1)
               int mes1 = mesAInt(parts1[2]);
               int mes2 = mesAInt(parts2[2]);

               if (anio1 != anio2) {
                  return Integer.compare(anio1, anio2); // Compara años
               } else {
                  return Integer.compare(mes1, mes2); // Compara meses
               }
         })
         .collect(Collectors.toMap(
               Map.Entry::getKey,
               Map.Entry::getValue,
               (e1, e2) -> e1,
              java.util.LinkedHashMap::new // Uso de la libreria hashmap
         ));

      model.addAttribute("jugadoresPorGrupo", jugadoresOrdenados); // Agrega los jugadores agrupados al modelo
      return "vistas/invitados/jugadores"; // Retorna la vista de jugadores para invitados
   }

   // Método auxiliar para convertir nombre del mes a número
   private int mesAInt(String mes) {
      return switch (mes.toLowerCase()) {
         case "enero" -> 1;
         case "febrero" -> 2;
         case "marzo" -> 3;
         case "abril" -> 4;
         case "mayo" -> 5;
         case "junio" -> 6;
         case "julio" -> 7;
         case "agosto" -> 8;
         case "septiembre" -> 9;
         case "octubre" -> 10;
         case "noviembre" -> 11;
         case "diciembre" -> 12;
         default -> 13; // Valor por defecto para meses no válidos
      };
   }
      // Mostrar todos los jugadores de un equipo específico
   @GetMapping("/equipo/{equipoId}/jugadores")
   public String mostrarJugadoresDelEquipo(@PathVariable Long equipoId, Model model) {
      // Obtener el equipo
      Equipo equipo = jugadorService.getEquipoById(equipoId);
      model.addAttribute("equipo", equipo);
      
      // Obtener todos los jugadores del equipo
      List<Jugador> jugadores = jugadorService.getJugadoresPorEquipo(equipoId);
      model.addAttribute("jugadores", jugadores);
      
      return "vistas/administrador/equipo/jugadoresEquipo"; // Nueva vista que crearemos
   }
}