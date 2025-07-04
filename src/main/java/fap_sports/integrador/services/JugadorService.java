package fap_sports.integrador.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.models.Jugador;
import fap_sports.integrador.models.Licenciado;
import fap_sports.integrador.repositories.DecadaRepository;
import fap_sports.integrador.repositories.EquipoRepository;
import fap_sports.integrador.repositories.JugadorRepository;


@Service
public class JugadorService {

    // Inyección del repositorio de jugadores para operaciones CRUD
    @Autowired
    private JugadorRepository jugadorRepository;

    // Inyección del repositorio de décadas
    @Autowired
    private DecadaRepository decadaRepository;

    // Inyección del repositorio de equipos
    @Autowired
    private EquipoRepository equipoRepository;

    // Registrar un nuevo jugador
    public Jugador registrarJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    // Obtener todos los jugadores registrados
    public List<Jugador> getAllJugadores() {
        return jugadorRepository.findAll();
    }

    // Obtener un jugador por su ID
    public Jugador getJugadorById(Long jugId) {
        return jugadorRepository.findById(jugId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con ID: " + jugId)); 
    }

    // Eliminar un jugador por su ID
    public void eliminarJugador(Long id) {
        jugadorRepository.deleteById(id);
    }

    // Actualizar los datos de un jugador existente
    public void actualizarJugador(Jugador jugador) {
        jugadorRepository.save(jugador);
    }

    // Obtener un equipo por su ID
    public Equipo getEquipoById(Long decId) {
        return equipoRepository.findById(decId)
                .orElseThrow(() -> new RuntimeException("Decada no encontrada con ID: " + decId)); 
    }

    // Obtener todos los equipos disponibles
    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    // Obtener una década por su ID
    public Decada getDecadaById(Long decId) {
        return decadaRepository.findById(decId)
                .orElseThrow(() -> new RuntimeException("Decada no encontrada con ID: " + decId)); 
    }

    // Obtener todas las décadas disponibles
    public List<Decada> getAllDecadas() {
        return decadaRepository.findAll();
    }

    public List<Equipo> getEquiposPorAnioInicio(int anioInicio) {
        return equipoRepository.findByAnioInicio(anioInicio);
    }

}
