package fap_sports.integrador.services;

import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.repositories.CampeonatoRepository;
import fap_sports.integrador.repositories.PartidoRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampeonatoService {

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    @Autowired
    private PartidoRepository partidoRepository;

    // 🔹 Crear o actualizar campeonato
    public Campeonato guardarCampeonato(Campeonato campeonato) {
        return campeonatoRepository.save(campeonato);
    }

    // 🔹 Obtener todos los campeonatos
    public List<Campeonato> listarCampeonatos() {
        return campeonatoRepository.findAll();
    }

    // 🔹 Obtener un campeonato por ID
    public Optional<Campeonato> obtenerCampeonatoPorId(Long id) {
        return campeonatoRepository.findById(id);
    }

    // 🔹 Eliminar un campeonato por ID
    public void eliminarCampeonato(Long id) {
        campeonatoRepository.deleteById(id);
    }

    // 🔹 Validar existencia por ID
    public boolean existePorId(Long id) {
        return campeonatoRepository.existsById(id);
    }

    @Transactional
    public void eliminarCampeonatoConPartidos(Long campeonatoId) {
        partidoRepository.deleteByCampeonato_CamId(campeonatoId);
        campeonatoRepository.deleteById(campeonatoId);
    }
}
