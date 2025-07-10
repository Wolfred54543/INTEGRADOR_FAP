package fap_sports.integrador.repositories;

import org.springframework.stereotype.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.models.CampeonatoEquipo;


public interface CampeonatoEquipoRepository extends JpaRepository<CampeonatoEquipo, Long> {
    List<CampeonatoEquipo> findByCampeonato(Campeonato campeonato);
     boolean existsByCampeonato(Campeonato campeonato);

}

