package fap_sports.integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fap_sports.integrador.models.Incidencia;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

    // Buscar incidencias por ID de partido
    List<Incidencia> findByPartido_ParId(Long partidoId);
}
