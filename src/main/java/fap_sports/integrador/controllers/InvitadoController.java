package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Noticia;
import fap_sports.integrador.models.Partido;
import fap_sports.integrador.models.Jugador;
import fap_sports.integrador.services.NoticiaService;
import fap_sports.integrador.services.PartidoService;
import fap_sports.integrador.services.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

// Clase controladora para manejar la vista inicial para invitados
@Controller
public class InvitadoController {

    @Autowired
    private PartidoService partidoService; // Inyección del servicio de partidos

    @Autowired
    private NoticiaService noticiaService; // Inyección del servicio de noticias

    @Autowired
    private JugadorService jugadorService; // Inyección del servicio de jugadores

    // Método para mostrar la página de inicio para invitados
    @GetMapping("/invitado")
    public String mostrarInicio(Model model) {
        // Obtener los últimos partidos
        List<Partido> ultimosPartidos = partidoService.obtenerUltimosPartidos(); // Llama al servicio para obtener los últimos partidos
        model.addAttribute("ultimosPartidos", ultimosPartidos); // Agrega los últimos partidos al modelo

        // Obtener todas las noticias
        List<Noticia> todasLasNoticias = noticiaService.obtenerTodasLasNoticias(); // Llama al servicio para obtener todas las noticias

        // Listas para noticias principales y secundarias
        List<Noticia> noticiasPrincipales = new ArrayList<>(); // Lista para noticias principales
        List<Noticia> noticiasSecundarias = new ArrayList<>(); // Lista para noticias secundarias

        // Clasificar noticias según su tipo
        for (Noticia noticia : todasLasNoticias) {
            if ("principal".equals(noticia.getNotTipo())) {
                noticiasPrincipales.add(noticia); // Agrega a la lista de noticias principales
            } else if ("secundaria".equals(noticia.getNotTipo())) {
                noticiasSecundarias.add(noticia); // Agrega a la lista de noticias secundarias
            }
        }

        model.addAttribute("noticiasPrincipales", noticiasPrincipales); // Agrega noticias principales al modelo
        model.addAttribute("noticiasSecundarias", noticiasSecundarias); // Agrega noticias secundarias al modelo

        // Obtener los primeros 6 jugadores registrados
        List<Jugador> jugadores = jugadorService.getAllJugadores().stream().limit(4).toList(); // Limita la lista a los primeros 4 jugadores
        model.addAttribute("jugadores", jugadores); // Agrega la lista de jugadores al modelo

        return "vistas/invitado"; // Retorna la vista para la página de inicio de invitados
    }
}