package fap_sports.integrador.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fap_sports.integrador.models.Contactanos;
import fap_sports.integrador.repositories.ContactanosRepository;

@Service
public class ContactanosService {
     // Inyección automática del repositorio de Contactanos
    @Autowired
    private ContactanosRepository contactanosRepository;

    // Registra y guarda un nuevo mensaje en la base de datos
    public Contactanos registrarMensaje(Contactanos contactanos) {
        return contactanosRepository.save(contactanos);
    }

    // Obtiene la lista completa de mensajes registrados
    public List<Contactanos> getAllMensajes() {
        return contactanosRepository.findAll();
    }

    // Elimina un mensaje dado su ID
    public void deleteMensaje(Long id) {
        contactanosRepository.deleteById(id);
    }

    // Obtiene un mensaje por su ID, lanza excepción si no existe
    public Contactanos getMensajeById(Long id) {
        return contactanosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado con ID: " + id));
    }

    // Actualiza un mensaje existente en la base de datos
    public void updateMensaje(Contactanos contactanos) {
        contactanosRepository.save(contactanos);
    }
}
