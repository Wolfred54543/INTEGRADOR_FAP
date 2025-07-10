package fap_sports.integrador.services;

import fap_sports.integrador.models.Partido;
import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.models.CampeonatoEquipo;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.repositories.PartidoRepository;
import fap_sports.integrador.repositories.CampeonatoEquipoRepository;
import fap_sports.integrador.repositories.CampeonatoRepository;
import fap_sports.integrador.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    @Autowired
    private CampeonatoEquipoRepository campeonatoEquipoRepository;
   
    public void realizarSorteoPorCampeonato(Long campeonatoId, List<Equipo> equipos) {
        if (equipos == null || equipos.size() < 2 || equipos.size() % 2 != 0) {
            throw new IllegalArgumentException("Debes seleccionar una cantidad par de equipos (mínimo 2).");
        }

        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
            .orElseThrow(() -> new RuntimeException("Campeonato no encontrado."));


         // Guardar equipos asignados al campeonato
        for (Equipo equipo : equipos) {
            CampeonatoEquipo ce = new CampeonatoEquipo();
            ce.setCampeonato(campeonato);
            ce.setEquipo(equipo);
            campeonatoEquipoRepository.save(ce);
        }
   
        Collections.shuffle(equipos);

        for (int i = 0; i < equipos.size(); i += 2) {
            Partido partido = new Partido();
            partido.setEquipoLocal(equipos.get(i));
            partido.setEquipoVisitante(equipos.get(i + 1));
            partido.setCampeonato(campeonato); // si tienes la relación
            partidoRepository.save(partido);
        }
    }


    // Crear partido
    public Partido crearPartido(Partido partido) {
        return partidoRepository.save(partido);
    }

    // Listar todos los partidos
    public List<Partido> listarPartidos() {
        return partidoRepository.findAll();
    }

    // Obtener partido por ID
    public Optional<Partido> obtenerPartidoPorId(Long id) {
        return partidoRepository.findById(id);
    }

    // Actualizar partido
    public Partido actualizarPartido(Long id, Partido partidoActualizado) {
        return partidoRepository.findById(id).map(partidoExistente -> {
            partidoExistente.setEquipoLocal(partidoActualizado.getEquipoLocal());
            partidoExistente.setEquipoVisitante(partidoActualizado.getEquipoVisitante());
            partidoExistente.setParFecha(partidoActualizado.getParFecha());
            partidoExistente.setParHora(partidoActualizado.getParHora());
            return partidoRepository.save(partidoExistente);
        }).orElseThrow(() -> new IllegalArgumentException("Partido no encontrado con ID: " + id));
    }

    // Eliminar partido
    public void eliminarPartido(Long id) {
        partidoRepository.deleteById(id);
    }
        // Devuelve los últimos 3 partidos jugados
    public List<Partido> obtenerUltimosPartidos() {
        return partidoRepository.findTop3ByOrderByParFechaDescParHoraDesc();
    }

    public List<Partido> obtenerPartidosPorCampeonatoId(Long campeonatoId) {
        return partidoRepository.findByCampeonatoCamId(campeonatoId);
    }

}
