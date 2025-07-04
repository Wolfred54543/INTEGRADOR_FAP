package fap_sports.integrador.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.repositories.DecadaRepository;

@Service
public class DecadaService {

    // Inyección del repositorio para acceder a la base de datos de décadas
    @Autowired
    private DecadaRepository decadaRepository;

    // Método para registrar o guardar una nueva década en la base de datos
    public Decada registrarDecada(Decada decada) {
        return decadaRepository.save(decada);
    }

    // Método para obtener todas las décadas registradas
    public List<Decada> getAllDecadas() {
        return decadaRepository.findAll();
    }

    // Método para eliminar una década por su ID
    public void eliminarDecada(Long id) {
        decadaRepository.deleteById(id);
    }

    // Método para buscar una década por su ID, lanza excepción si no existe
    public Decada getDecadaById(Long id) {
        return decadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
    }

    // Método para actualizar los datos de una década existente
    public void actualizarDecada(Decada decada) {
        decadaRepository.save(decada);
    }
}

