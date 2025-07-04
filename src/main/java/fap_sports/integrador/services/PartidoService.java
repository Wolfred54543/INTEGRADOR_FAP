package fap_sports.integrador.services;

import fap_sports.integrador.models.Partido;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.repositories.PartidoRepository;
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

   
    // SORTEO DE PARTIDOS
    public void realizarSorteo() {
        List<Equipo> equipos = equipoRepository.findAll();

        if (equipos.size() != 12) {
            throw new IllegalArgumentException("Se requieren exactamente 12 equipos.");
        }

        Collections.shuffle(equipos);

        for (int i = 0; i < equipos.size(); i += 2) {
            Partido partido = new Partido();
            partido.setEquipoLocal(equipos.get(i));
            partido.setEquipoVisitante(equipos.get(i + 1));
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
}
