package fap_sports.integrador.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.models.Licenciado;
import fap_sports.integrador.models.Tipo;
import fap_sports.integrador.repositories.DecadaRepository;
import fap_sports.integrador.repositories.LicenciadoRepository;
import fap_sports.integrador.repositories.TipoRepository;

import java.util.List;

@Service
public class LicenciadoService {

    // Inyección del repositorio de licenciados
    @Autowired
    private LicenciadoRepository licenciadoRepository;

    // Inyección del repositorio de tipos
    @Autowired
    private TipoRepository tipoRepository;

    // Inyección del repositorio de décadas
    @Autowired
    private DecadaRepository decadaRepository;

    // Registrar un nuevo licenciado
    public Licenciado registerLicenciado(Licenciado licenciado) {
        return licenciadoRepository.save(licenciado);
    }

    // Obtener todos los licenciados registrados
    public List<Licenciado> getAllLicenciados() {
        return licenciadoRepository.findAll();
    }

    // Eliminar un licenciado por su ID
    public void deleteLicenciado(Long id) {
        licenciadoRepository.deleteById(id);
    }

    // Obtener un licenciado por su ID
    public Licenciado getLicenciadoById(Long id) {
        return licenciadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licenciado no encontrado con ID: " + id));
    }

    // Actualizar los datos de un licenciado
    public void updateLicenciado(Licenciado licenciado) {
        licenciadoRepository.save(licenciado);
    }

    // Obtener un tipo por su ID
    public Tipo getTipoById(Long tipoId) {
        return tipoRepository.findById(tipoId)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado con ID: " + tipoId)); 
    }

    // Obtener todos los tipos disponibles
    public List<Tipo> getAllTipos() {
        return tipoRepository.findAll();
    }

    // Obtener una década por su ID
    public Decada getDecadaById(Long decId) {
        return decadaRepository.findById(decId)
                .orElseThrow(() -> new RuntimeException("Decada no encontrada con ID: " + decId)); 
    }

    // Obtener todas las décadas disponibles
    public List<Decada> getAllDecadas() {
        return decadaRepository.findAll();
    }
}
