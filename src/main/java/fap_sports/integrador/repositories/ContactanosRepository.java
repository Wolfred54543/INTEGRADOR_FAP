package fap_sports.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import fap_sports.integrador.models.Contactanos;

// Repositorio para la entidad Contactanos, extiende JpaRepository para
// proporcionar métodos CRUD y consultas básicas
public interface ContactanosRepository extends JpaRepository<Contactanos, Long> {
    
}