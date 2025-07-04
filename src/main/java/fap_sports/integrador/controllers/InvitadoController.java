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
import java.util.Map;

@Controller
public class InvitadoController {

    @Autowired
    private PartidoService partidoService; // Inyección del servicio de partidos

    @Autowired
    private NoticiaService noticiaService; // Inyección del servicio de noticias

    @Autowired
    private JugadorService jugadorService; // Inyección del servicio de jugadores

    @GetMapping("/invitado")
    public String mostrarInicio(Model model) {
        // Obtener los últimos partidos
        List<Partido> ultimosPartidos = partidoService.obtenerUltimosPartidos();
        model.addAttribute("ultimosPartidos", ultimosPartidos);

        // Obtener todas las noticias
        List<Noticia> todasLasNoticias = noticiaService.obtenerTodasLasNoticias();

        // Listas para noticias principales y secundarias
        List<Noticia> noticiasPrincipales = new ArrayList<>();
        List<Noticia> noticiasSecundarias = new ArrayList<>();

        // Clasificar noticias según su tipo
        for (Noticia noticia : todasLasNoticias) {
            if ("principal".equals(noticia.getNotTipo())) {
                noticiasPrincipales.add(noticia);
            } else if ("secundaria".equals(noticia.getNotTipo())) {
                noticiasSecundarias.add(noticia);
            }
        }

        model.addAttribute("noticiasPrincipales", noticiasPrincipales);
        model.addAttribute("noticiasSecundarias", noticiasSecundarias);

        // Obtener los primeros 6 jugadores registrados
        List<Jugador> jugadores = jugadorService.getAllJugadores().stream().limit(4).toList();

        model.addAttribute("jugadores", jugadores);


        return "vistas/invitado";
    }
}