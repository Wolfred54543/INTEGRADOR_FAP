package fap_sports.integrador.repositories;

import fap_sports.integrador.models.Decada;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para la entidad Decada, extiende JpaRepository para
// proporcionar métodos CRUD y consultas básicas
public interface DecadaRepository extends JpaRepository<Decada, Long> {
   
}