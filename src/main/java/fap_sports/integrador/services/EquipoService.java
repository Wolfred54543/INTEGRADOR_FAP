package fap_sports.integrador.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.models.Rol;
import fap_sports.integrador.models.Usuario;
import fap_sports.integrador.repositories.DecadaRepository;
import fap_sports.integrador.repositories.EquipoRepository;
import fap_sports.integrador.repositories.RolRepository;

@Service
public class EquipoService {
    // Inyección del repositorio para manejar datos de equipos
    @Autowired
    private EquipoRepository equipoRepository;

    // Inyección del repositorio para manejar datos de décadas
    @Autowired
    private DecadaRepository decadaRepository;

    // Inyección del repositorio para manejar datos de roles
    @Autowired
    private RolRepository rolRepository;

    // Método para registrar o guardar un nuevo equipo
    public Equipo registrarEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    // Método para obtener todos los equipos registrados
    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    // Método para eliminar un equipo por su ID
    public void eliminarEquipo(Long id) {
        equipoRepository.deleteById(id);
    }

    // Método para obtener un equipo por su ID, lanza excepción si no existe
    public Equipo getEquipoById(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
    }

    // Método para actualizar los datos de un equipo existente
    public void actualizarEquipo(Equipo equipo) {
        equipoRepository.save(equipo);
    }

    // Método para obtener una década por su ID, lanza excepción si no existe
    public Decada getDecadaById(Long decId) {
        return decadaRepository.findById(decId)
                .orElseThrow(() -> new RuntimeException("Decada no encontrada con ID: " + decId)); 
    }

    // Método para obtener todas las décadas registradas
    public List<Decada> getAllDecadas() {
        return decadaRepository.findAll();
    }

    // Método para obtener un rol por su ID, lanza excepción si no existe
    public Rol getRolById(Long rolId) {
        return rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Decada no encontrada con ID: " + rolId));
    }

    // Método para obtener todos los roles registrados
    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    // Método para obtener la lista de usuarios que tienen un rol específico
    public List<Usuario> getUsuariosPorRol(String rolNombre) {
        Rol rolDelegado = rolRepository.findByNombre(rolNombre);
        return rolDelegado != null ? rolDelegado.getUsuarios() : new ArrayList<>();
    }
}
