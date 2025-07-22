package fap_sports.integrador.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fap_sports.integrador.models.Incidencia;
import fap_sports.integrador.models.Jugador;
import fap_sports.integrador.models.Partido;
import fap_sports.integrador.repositories.IncidenciaRepository;
import fap_sports.integrador.repositories.JugadorRepository;
import fap_sports.integrador.repositories.PartidoRepository;

@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private PartidoRepository partidoRepository;


    public void guardar(Incidencia incidencia) {
    incidenciaRepository.save(incidencia);
    }
    
    public void guardarIncidencias(List<Incidencia> incidencias) {
        incidenciaRepository.saveAll(incidencias);
    }

    public List<Incidencia> obtenerPorPartido(Long partidoId) {
        return incidenciaRepository.findByPartido_ParId(partidoId);
    }

    //Nuevo método para registrar una sola incidencia
    public void registrar(Long jugadorId, Long partidoId, String tipoIncidencia) {
        Incidencia incidencia = new Incidencia();

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));

        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado"));

        incidencia.setJugador(jugador);
        incidencia.setPartido(partido);
        incidencia.setTipo(tipoIncidencia);
        incidencia.setCantidad(1);
        incidencia.setFechaRegistro(LocalDate.now());

        incidenciaRepository.save(incidencia);  
    }
    
    public List<Incidencia> listarPorPartido(Long partidoId) {
        return incidenciaRepository.findByPartido_ParId(partidoId);
    }
}
