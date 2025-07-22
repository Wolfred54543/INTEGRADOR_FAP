package fap_sports.integrador.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fap_sports.integrador.models.Noticia;
import fap_sports.integrador.repositories.NoticiaRepository;

@Service
public class NoticiaService {

    // Inyección automática del repositorio de Comunicados
    @Autowired
    private NoticiaRepository noticiaRepository;

    // Registra y guarda un nuevo comunicado en la base de datos
    public Noticia registrarNoticia(Noticia noticia) {
        return noticiaRepository.save(noticia);
    }

    // Obtiene la lista completa de comunicados registrados
    public List<Noticia> getAllNoticias() {
        return noticiaRepository.findAll();
    }

    // Elimina un comunicado dado su ID
    public void deleteNoticia(Long id) {
        noticiaRepository.deleteById(id);
    }

    // Obtiene un comunicado por su ID, lanza excepción si no existe
    public Noticia getNoticiaById(Long id) {
        return noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada con ID: " + id));
    }

    // Actualiza un comunicado existente en la base de datos
    public void updateNoticia(Noticia noticia) {
        noticiaRepository.save(noticia);
    }
    
    public List<Noticia> obtenerNoticiasPorTipo(String tipo) {
        return noticiaRepository.findByNotTipo(tipo);
    }

    public List<Noticia> obtenerTodasLasNoticias() {
    return noticiaRepository.findAll();
    }

     public List<Noticia> obtenerUltimasNoticias(int cantidad) {
        return noticiaRepository.findTopNByOrderByNotFechaCreacionDesc(cantidad);
    }

        //Paginador de la vista de los jugadores
    public Page<Noticia> listarTodosPaginados(Pageable pageable) {
        return noticiaRepository.findAll(pageable);
    }

    //Buscador de la vista de los jugadores
    public Page<Noticia> buscarPorTituloSubtituloAutor(String filtro, Pageable pageable) {
        return noticiaRepository.buscarPorTituloSubtituloAutor(filtro, pageable);
    }

}
