// package fap_sports.integrador.repositories;

// import fap_sports.integrador.models.TablaPosiciones;
// import fap_sports.integrador.models.Campeonato;
// import fap_sports.integrador.models.Equipo;
// import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;
// import java.util.Optional;

// public interface TablaPosicionesRepository extends JpaRepository<TablaPosiciones, Long> {

//     // Obtener todas las posiciones de un campeonato ordenadas
//     List<TablaPosiciones> findByCampeonatoOrderByPuntosDescPartidosGanadosDescGolesFavorDesc(Campeonato campeonato);

//     // Buscar la posición específica de un equipo en un campeonato
//     Optional<TablaPosiciones> findByCampeonatoAndEquipo(Campeonato campeonato, Equipo equipo);
// }
