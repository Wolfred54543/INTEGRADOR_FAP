package fap_sports.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fap_sports.integrador.models.Jugador;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long>{
    
}
