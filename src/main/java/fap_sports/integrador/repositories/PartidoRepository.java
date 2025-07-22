package fap_sports.integrador.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.models.Partido;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findTop3ByParFechaIsNotNullAndParHoraIsNotNullOrderByParFechaDescParHoraDesc();
    List<Partido> findByCampeonato(Campeonato campeonato);
    void deleteByCampeonato_CamId(Long camId);
    List<Partido> findByCampeonatoCamId(Long campeonatoId);
    List<Partido> findByCampeonatoAndParEstado(Campeonato campeonato, String parEstado);
    

}