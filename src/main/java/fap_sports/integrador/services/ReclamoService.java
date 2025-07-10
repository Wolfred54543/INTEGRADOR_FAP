package fap_sports.integrador.services;

import fap_sports.integrador.models.Reclamo;
import fap_sports.integrador.repositories.ReclamoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReclamoService {

    private static final Logger logger = LoggerFactory.getLogger(ReclamoService.class);

    private final ReclamoRepository reclamoRepository;

    public ReclamoService(ReclamoRepository reclamoRepository) {
        this.reclamoRepository = reclamoRepository;
    }

    @Transactional
    public void registrarReclamo(Reclamo reclamo) {
        try {
            // Estado por defecto
            reclamo.setRecEstado("abierto");
            reclamo.setRecRespuesta(null); // Opcional, si no viene desde el formulario

            Reclamo savedReclamo = reclamoRepository.save(reclamo);
            logger.info("Reclamo registrado con éxito: ID={}, Motivo={}", savedReclamo.getRecId(), savedReclamo.getRecMotivo());
        } catch (Exception e) {
            logger.error("Error al registrar el reclamo", e);
            throw new RuntimeException("Error al registrar el reclamo", e);
        }
    }

    @Transactional
    public void responderReclamo(Long id, String respuesta) {
        Reclamo reclamo = reclamoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reclamo no encontrado con ID: " + id));

        reclamo.setRecRespuesta(respuesta);
        reclamo.setRecEstado("cerrado");

        reclamoRepository.save(reclamo);

        logger.info("Reclamo ID={} respondido y marcado como cerrado", id);
    }

    public List<Reclamo> listarReclamos() {
        logger.info("Listando todos los reclamos");
        return reclamoRepository.findAll();
    }

    // Obtener un reclamo por ID
    public Reclamo obtenerReclamoPorId(Long id) {
        return reclamoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reclamo no encontrado con ID: " + id));
    }

    // Eliminar reclamo
    public void eliminarReclamo(Long id) {
        reclamoRepository.deleteById(id);
    }

    // Obtener reclamos por estado
    public List<Reclamo> obtenerReclamosPorEstado(String estado) {
        return reclamoRepository.findByRecEstadoIgnoreCase(estado);
    }

}
