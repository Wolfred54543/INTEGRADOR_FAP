package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.models.Jugador;
import fap_sports.integrador.services.JugadorService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JugadorController {

   @Autowired
   private JugadorService jugadorService;

   // Muestra el formulario para crear o listar jugadores, junto con equipos y décadas disponibles
   @GetMapping({"/formularioJugadores"})
   public String formularioJugadores(Model model) {
      model.addAttribute("jugador", new Jugador());
      model.addAttribute("equipos", this.jugadorService.getAllEquipos());
      model.addAttribute("decadas", this.jugadorService.getAllDecadas());
      model.addAttribute("jugadores", this.jugadorService.getAllJugadores());
      return "vistas/jugadores";
   }

   // Procesa el registro de un nuevo jugador y redirige al formulario para refrescar la lista
   @PostMapping({"/registrarJugadores"})
   public String registrarJugadores(@ModelAttribute Jugador jugador, Model model) {
      this.jugadorService.registrarJugador(jugador);
      return "redirect:/formularioJugadores";
   }

   // Muestra el formulario para asignar un equipo a un jugador específico
   @GetMapping({"/asignarEquipo"})
   public String mostrarAsignarEquipo(@RequestParam Long jugadorId, Model model) {
      Jugador jugador = this.jugadorService.getJugadorById(jugadorId);
      model.addAttribute("jugador", jugador);

      // Trae equipos solo por el año de inicio, no por ID de la década
      int anioInicioJugador = jugador.getDecada().getAnioInicio();
      model.addAttribute("equipos", this.jugadorService.getEquiposPorAnioInicio(anioInicioJugador));

      return "vistas/asignarEquipo";
   }


   // Guarda la asignación de equipo al jugador, validando que ambos compartan la misma década
   @PostMapping({"/guardarEquipo"})
   public String guardarEquipo(@RequestParam Long jugadorId, @RequestParam Long equipoId, Model model) {
      Jugador jugador = this.jugadorService.getJugadorById(jugadorId);
      Equipo equipo = this.jugadorService.getEquipoById(equipoId);

      int anioInicioJugador = jugador.getDecada().getAnioInicio();
      int anioInicioEquipo = equipo.getDecada().getAnioInicio();

      if (anioInicioJugador != anioInicioEquipo) {
         // Error si las décadas no coinciden, devuelve a la vista con mensaje
         model.addAttribute("errorMessage", "El año de inicio del jugador no coincide con el del equipo.");
         model.addAttribute("jugador", jugador);
          model.addAttribute("equipos", this.jugadorService.getEquiposPorAnioInicio(anioInicioJugador));
         return "vistas/asignarEquipo";
      } else {
         jugador.setEquipo(equipo);
         this.jugadorService.registrarJugador(jugador);
         return "redirect:/formularioJugadores";
      }
   }

   // Elimina un jugador por su ID y redirige al listado actualizado
   @PostMapping("/eliminarJugador/{id}")
   public String eliminarJugador(@PathVariable Long id) {
      jugadorService.eliminarJugador(id);
      return "redirect:/formularioJugadores";
   }

   // Muestra el formulario para editar un jugador existente
   @GetMapping("/editarJugador/{id}")
   public String editarJugador(@PathVariable Long id, Model model) {
      Jugador jugador = jugadorService.getJugadorById(id);
      model.addAttribute("jugador", jugador);
      model.addAttribute("decadas", jugadorService.getAllDecadas());
      model.addAttribute("equipos", jugadorService.getAllEquipos());
      return "vistas/editarJugador";
   }

   // Procesa la actualización de los datos de un jugador
   @PostMapping("/actualizarJugador")
   public String actualizarJugador(@ModelAttribute Jugador jugador) {
      jugadorService.actualizarJugador(jugador);
      return "redirect:/formularioJugadores";
   }

   // Vista de jugadores para los invitados
   @GetMapping("/jugadores")
   public String mostrarJugadoresAgrupados(Model model) {
      List<Jugador> jugadores = jugadorService.getAllJugadores();

      // Agrupar jugadores por "Década Año de Inicio y Mes"
      Map<String, List<Jugador>> jugadoresPorGrupo = jugadores.stream()
         .filter(j -> j.getDecada() != null)
         .collect(Collectors.groupingBy(j -> {
               Decada d = j.getDecada();
               return "Década " + d.getAnioInicio() + " " + d.getDecMes();
         }));

      // Ordenar el mapa por año y luego lo hace por mes
      Map<String, List<Jugador>> jugadoresOrdenados = jugadoresPorGrupo.entrySet().stream()
         .sorted((e1, e2) -> {
               String[] parts1 = e1.getKey().split(" ");
               String[] parts2 = e2.getKey().split(" ");
               int anio1 = Integer.parseInt(parts1[1]);
               int anio2 = Integer.parseInt(parts2[1]);

               //Asignar el Mes a un número (ej: Enero → 1)
               int mes1 = mesAInt(parts1[2]);
               int mes2 = mesAInt(parts2[2]);

               if (anio1 != anio2) {
                  return Integer.compare(anio1, anio2);
               } else {
                  return Integer.compare(mes1, mes2);
               }
         })
         .collect(Collectors.toMap(
               java.util.Map.Entry::getKey,
               java.util.Map.Entry::getValue,
               (e1, e2) -> e1,
               java.util.LinkedHashMap::new 
         ));

      model.addAttribute("jugadoresPorGrupo", jugadoresOrdenados);
      return "vistas/invitados/jugadores";
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
         default -> 13; // valor por default 
      };
   }

}
