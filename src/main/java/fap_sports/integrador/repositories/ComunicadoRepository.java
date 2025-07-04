package fap_sports.integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fap_sports.integrador.models.Comunicado;

// Repositorio para la entidad Comunicado, extiende JpaRepository para
// proporcionar métodos CRUD y consultas básicas
@Repository
public interface ComunicadoRepository extends JpaRepository<Comunicado, Long> {
    List<Comunicado> findByComEstadoOrderByComFechaPublicacionDesc(String estado);
}
