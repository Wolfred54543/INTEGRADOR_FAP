// package fap_sports.integrador.services;

// import fap_sports.integrador.models.*;
// import fap_sports.integrador.repositories.*;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.*;

// @Service
// public class TablaPosicionesService {

//     private final PartidoRepository partidoRepository;
//     private final TablaPosicionesRepository tablaPosicionesRepository;

//     public TablaPosicionesService(PartidoRepository partidoRepository,
//                                    TablaPosicionesRepository tablaPosicionesRepository) {
//         this.partidoRepository = partidoRepository;
//         this.tablaPosicionesRepository = tablaPosicionesRepository;
//     }

//     @Transactional
//     public void actualizarTablaPosiciones(Campeonato campeonato) {
//         // 1. Obtener todos los partidos jugados de este campeonato
//         List<Partido> partidos = partidoRepository.findByCampeonatoAndParEstado(campeonato, "JUGADO");

//         // 2. Mapa para acumular estadísticas por equipo
//         Map<Equipo, TablaPosiciones> posicionesMap = new HashMap<>();

//         for (Partido partido : partidos) {
//             Equipo local = partido.getEquipoLocal();
//             Equipo visitante = partido.getEquipoVisitante();
//             int golesLocal = Optional.ofNullable(partido.getParPuntajeLocal()).orElse(0);
//             int golesVisitante = Optional.ofNullable(partido.getParPuntajeVisitante()).orElse(0);

//             // Local
//             posicionesMap.putIfAbsent(local, crearNuevaPosicion(campeonato, local));
//             actualizarEstadisticas(posicionesMap.get(local), golesLocal, golesVisitante);

//             // Visitante
//             posicionesMap.putIfAbsent(visitante, crearNuevaPosicion(campeonato, visitante));
//             actualizarEstadisticas(posicionesMap.get(visitante), golesVisitante, golesLocal);
//         }

//         // 3. Guardar o actualizar en la base de datos
//         for (TablaPosiciones pos : posicionesMap.values()) {
//             Optional<TablaPosiciones> existente = tablaPosicionesRepository.findByCampeonatoAndEquipo(pos.getCampeonato(), pos.getEquipo());
//             if (existente.isPresent()) {
//                 TablaPosiciones actual = existente.get();
//                 actual.setPuntos(pos.getPuntos());
//                 actual.setPartidosJugados(pos.getPartidosJugados());
//                 actual.setPartidosGanados(pos.getPartidosGanados());
//                 actual.setPartidosEmpatados(pos.getPartidosEmpatados());
//                 actual.setPartidosPerdidos(pos.getPartidosPerdidos());
//                 actual.setGolesFavor(pos.getGolesFavor());
//                 actual.setGolesContra(pos.getGolesContra());
//                 tablaPosicionesRepository.save(actual);
//             } else {
//                 tablaPosicionesRepository.save(pos);
//             }
//         }
//     }

//     private TablaPosiciones crearNuevaPosicion(Campeonato campeonato, Equipo equipo) {
//         TablaPosiciones tp = new TablaPosiciones();
//         tp.setCampeonato(campeonato);
//         tp.setEquipo(equipo);
//         return tp;
//     }

//     private void actualizarEstadisticas(TablaPosiciones pos, int golesFavor, int golesContra) {
//         pos.setPartidosJugados(pos.getPartidosJugados() + 1);
//         pos.setGolesFavor(pos.getGolesFavor() + golesFavor);
//         pos.setGolesContra(pos.getGolesContra() + golesContra);

//         if (golesFavor > golesContra) {
//             pos.setPartidosGanados(pos.getPartidosGanados() + 1);
//             pos.setPuntos(pos.getPuntos() + 3);
//         } else if (golesFavor == golesContra) {
//             pos.setPartidosEmpatados(pos.getPartidosEmpatados() + 1);
//             pos.setPuntos(pos.getPuntos() + 1);
//         } else {
//             pos.setPartidosPerdidos(pos.getPartidosPerdidos() + 1);
//         }
//     }
// }
