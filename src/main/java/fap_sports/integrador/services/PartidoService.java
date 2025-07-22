package fap_sports.integrador.services;

import fap_sports.integrador.models.Partido;
import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.models.CampeonatoEquipo;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.repositories.PartidoRepository;
import fap_sports.integrador.repositories.CampeonatoEquipoRepository;
import fap_sports.integrador.repositories.CampeonatoRepository;
import fap_sports.integrador.repositories.EquipoRepository;

//import fap_sports.integrador.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// Clase de servicio para manejar la lógica de los partidos
@Service
public class PartidoService {

    @Autowired
    private PartidoRepository partidoRepository; // Repositorio para acceder a la entidad Partido

    @Autowired
    private EquipoRepository equipoRepository; // Repositorio para acceder a la entidad Equipo

    @Autowired
    private CampeonatoRepository campeonatoRepository; // Repositorio para acceder a la entidad Campeonato

    @Autowired
    private CampeonatoEquipoRepository campeonatoEquipoRepository; // Repositorio para manejar la relación entre Campeonatos y Equipos
   
    // Método para realizar el sorteo de partidos por campeonato
public void realizarSorteoPorCampeonato(Long campeonatoId, List<Equipo> equipos) {
    if (equipos == null || equipos.size() < 2) {
        throw new IllegalArgumentException("Debe haber al menos dos equipos.");
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

    // Crear partidos "todos contra todos" (solo ida)
    for (int i = 0; i < equipos.size(); i++) {
        for (int j = i + 1; j < equipos.size(); j++) {
            Equipo equipoA = equipos.get(i);
            Equipo equipoB = equipos.get(j);

            // Sortear aleatoriamente quién es local y quién es visitante
            boolean equipoAEsLocal = Math.random() < 0.5;

            Partido partido = new Partido();
            partido.setEquipoLocal(equipoAEsLocal ? equipoA : equipoB);
            partido.setEquipoVisitante(equipoAEsLocal ? equipoB : equipoA);
            partido.setCampeonato(campeonato);

            partidoRepository.save(partido);
        }
    }
}


    // Crear un nuevo partido
    public Partido crearPartido(Partido partido) {
        return partidoRepository.save(partido); // Guardar el partido en la base de datos
    }

    // Listar todos los partidos
    public List<Partido> listarPartidos() {
        return partidoRepository.findAll(); // Obtener todos los partidos
    }

    // Obtener un partido por su ID
    public Optional<Partido> obtenerPartidoPorId(Long id) {
        return partidoRepository.findById(id); // Buscar el partido por ID
    }

    // Actualizar un partido existente
    public Partido actualizarPartido(Long id, Partido partidoActualizado) {
        return partidoRepository.findById(id).map(partidoExistente -> {
            partidoExistente.setEquipoLocal(partidoActualizado.getEquipoLocal()); // Actualizar equipo local
            partidoExistente.setEquipoVisitante(partidoActualizado.getEquipoVisitante()); // Actualizar equipo visitante
            partidoExistente.setParFecha(partidoActualizado.getParFecha()); // Actualizar fecha
            partidoExistente.setParHora(partidoActualizado.getParHora()); // Actualizar hora
            return partidoRepository.save(partidoExistente); // Guardar los cambios
        }).orElseThrow(() -> new IllegalArgumentException("Partido no encontrado con ID: " + id)); // Manejo de excepción si no se encuentra
    }

    // Eliminar un partido por su ID
    public void eliminarPartido(Long id) {
        partidoRepository.deleteById(id); // Eliminar el partido
    }
    
    // Obtener los últimos partidos
    public List<Partido> obtenerUltimosPartidos() {
        return partidoRepository.findTop3ByParFechaIsNotNullAndParHoraIsNotNullOrderByParFechaDescParHoraDesc(); // Obtener los últimos tres partidos
    }

    // Obtener partidos por ID de campeonato
    public List<Partido> obtenerPartidosPorCampeonatoId(Long campeonatoId) {
        return partidoRepository.findByCampeonatoCamId(campeonatoId); // Buscar partidos por ID del campeonato
    }
    
    // Método para guardar los resultados de un partido
    public void guardarResultados(Long partidoId, Integer puntajeLocal, Integer puntajeVisitante, String parEstado) {
        Partido partido = obtenerPartidoPorId(partidoId)
            .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado con ID: " + partidoId));

        partido.setParPuntajeLocal(puntajeLocal);
        partido.setParPuntajeVisitante(puntajeVisitante);
        partido.setParEstado(parEstado);
        partido.calcularPuntos();

        partidoRepository.save(partido);
    }

    public List<Map<String, Object>> obtenerTablaClasificacion(Long campeonatoId) {
        List<Partido> partidos = partidoRepository.findByCampeonatoCamId(campeonatoId);
        List<Equipo> equipos = equipoRepository.findByCampeonatoCamId(campeonatoId); // Traer todos los equipos

        // Crear un mapa para almacenar las estadísticas de cada equipo
        Map<Equipo, int[]> tabla = new HashMap<>();

        // Asegura que todos los equipos estén en la tabla aunque no hayan jugado
        for (Equipo equipo : equipos) {
            tabla.putIfAbsent(equipo, new int[8]); // PJ, PG, PE, PP, GF, GC, DG, Pts
        }

        // Itera sobre los partidos para actualizar las estadísticas
        for (Partido partido : partidos) {
            // Verifica que el partido esté finalizado
            if (partido.getParEstado() == null || !partido.getParEstado().equalsIgnoreCase("Finalizado")) {
                // Asegura que los equipos estén en la tabla aunque no hayan jugado
                tabla.putIfAbsent(partido.getEquipoLocal(), new int[8]);
                tabla.putIfAbsent(partido.getEquipoVisitante(), new int[8]);
                continue;
            }

            // Obtener los equipos y los goles
            Equipo local = partido.getEquipoLocal();
            Equipo visitante = partido.getEquipoVisitante();
            Integer golesLocal = partido.getParPuntajeLocal();
            Integer golesVisitante = partido.getParPuntajeVisitante();

            // Asegura que los goles estén asignados
            if (golesLocal == null || golesVisitante == null) continue;

            // Actualizar estadísticas para el equipo local
            int[] statsLocal = tabla.get(local);
            statsLocal[0]++; // PJ
            statsLocal[4] += golesLocal; // GF
            statsLocal[5] += golesVisitante; // GC
            statsLocal[6] = statsLocal[4] - statsLocal[5]; // DG

            // Actualizar estadísticas para el equipo visitante
            int[] statsVisitante = tabla.get(visitante);
            statsVisitante[0]++; // PJ
            statsVisitante[4] += golesVisitante; // GF
            statsVisitante[5] += golesLocal; // GC
            statsVisitante[6] = statsVisitante[4] - statsVisitante[5]; // DG

            // Asignar puntos
            if (golesLocal > golesVisitante) {
                statsLocal[1]++; // PG
                statsVisitante[3]++; // PP
                statsLocal[7] += 3; // Pts
            } else if (golesLocal < golesVisitante) {
                statsVisitante[1]++; // PG
                statsLocal[3]++; // PP
                statsVisitante[7] += 3; // Pts
            } else {
                statsLocal[2]++; // PE
                statsVisitante[2]++; // PE
                statsLocal[7]++; // Pts
                statsVisitante[7]++; // Pts
            }
        }

        // Convertir el mapa a una lista ordenada
        return tabla.entrySet().stream()
            .map(entry -> {
                Map<String, Object> fila = new HashMap<>();
                fila.put("equipo", entry.getKey());
                int[] stats = entry.getValue();
                fila.put("PJ", stats[0]);
                fila.put("PG", stats[1]);
                fila.put("PE", stats[2]);
                fila.put("PP", stats[3]);
                fila.put("GF", stats[4]);
                fila.put("GC", stats[5]);
                fila.put("DG", stats[6]);
                fila.put("Pts", stats[7]);
                return fila;
            })
            .sorted((a, b) -> {
                        int ptsA = (int) a.get("Pts");
                        int ptsB = (int) b.get("Pts");
                        if (ptsB != ptsA) return ptsB - ptsA;
                        int dgA = (int) a.get("DG");
                        int dgB = (int) b.get("DG");
                        if (dgB != dgA) return dgB - dgA;

                        // Agregar criterio adicional: ordenar por nombre de equipo
                        String nombreA = ((Equipo) a.get("equipo")).getEquNombre(); 
                        String nombreB = ((Equipo) b.get("equipo")).getEquNombre(); 
                        return nombreA.compareTo(nombreB);
                    })
                    .collect(Collectors.toList());
    }



}