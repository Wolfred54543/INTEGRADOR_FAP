package fap_sports.integrador.repositories;

import org.springframework.stereotype.*;
import org.springframework.data.jpa.repository.JpaRepository;

import fap_sports.integrador.models.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {
    
}
