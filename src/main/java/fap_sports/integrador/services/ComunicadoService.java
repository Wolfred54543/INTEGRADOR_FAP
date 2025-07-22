package fap_sports.integrador.services;

import fap_sports.integrador.models.Comunicado;
import fap_sports.integrador.repositories.ComunicadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComunicadoService {

    @Autowired
    private ComunicadoRepository comunicadoRepository;

    // Registrar nuevo comunicado
    public Comunicado registrarComunicado(Comunicado comunicado) {
        return comunicadoRepository.save(comunicado);
    }
    public Comunicado saveComunicado(Comunicado comunicado) {
        return comunicadoRepository.save(comunicado);
    }

    // Obtener todos los comunicados
    public List<Comunicado> getAllComunicados() {
        return comunicadoRepository.findAll();
    }

    // Eliminar comunicado por ID
    public void eliminarComunicado(Long id) {
        comunicadoRepository.deleteById(id);
    }

    // Obtener comunicado por ID
    public Comunicado getComunicadoById(Long id) {
        return comunicadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comunicado no encontrado con ID: " + id));
    }

    // Actualizar comunicado existente
    public void actualizarComunicado(Comunicado comunicado) {
        comunicadoRepository.save(comunicado);
    }
    public void publicarComunicado(Long id) {
    Comunicado comunicado = comunicadoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Comunicado no encontrado con ID: " + id));
    
        comunicado.setComEstado("publicado");
        comunicadoRepository.save(comunicado);
    }
    public List<Comunicado> getComunicadosPublicados() {
        return comunicadoRepository.findByComEstadoOrderByComFechaPublicacionDesc("publicado");
        
    }

    public List<Comunicado> getUltimosComunicadosPublicados() {
        return comunicadoRepository.findByComEstadoTrueOrderByComFechaPublicacionDesc();
    }
}
