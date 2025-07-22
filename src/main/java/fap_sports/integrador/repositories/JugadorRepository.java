package fap_sports.integrador.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fap_sports.integrador.models.Jugador;

// Repositorio para la entidad Jugador, extiende JpaRepository para
// proporcionar métodos CRUD y consultas básicas
@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long>{
    //Metodo boleano para verificar si el jugador pertenece a un equipo
    boolean existsByEquipoEquId(Long equipoId);
    //Método para obtener todos los jugadores de un equipo específico
    List<Jugador> findByEquipoEquId(Long equipoId);

    // Consulta para filtrar usuarios por nombre, apellido y dni
    @Query("SELECT u FROM Jugador u WHERE " +
       "LOWER(u.jugNombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
       "LOWER(u.jugApellido) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
       "u.jugDni LIKE CONCAT('%', :filtro, '%')")
    Page<Jugador> buscarPorNombreApellidoDni(@Param("filtro") String filtro, Pageable pageable);
}
