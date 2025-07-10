package fap_sports.integrador.services;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.models.Rol;
import fap_sports.integrador.models.Tipo;
import fap_sports.integrador.models.Usuario;
import fap_sports.integrador.repositories.DecadaRepository;
import fap_sports.integrador.repositories.RolRepository;
import fap_sports.integrador.repositories.TipoRepository;
import fap_sports.integrador.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Indica que esta clase es un servicio de Spring
public class UsuarioService {

    // Repositorio para operaciones CRUD sobre usuarios
    private final UsuarioRepository usuarioRepository;

    // Bean encargado de encriptar contraseñas
    private final PasswordEncoder passwordEncoder;

    // Repositorio para operaciones sobre Tipos
    @Autowired
    private TipoRepository tipoRepository;

    // Repositorio para operaciones sobre Décadas
    @Autowired
    private DecadaRepository decadaRepository;

    // Repositorio para operaciones sobre Roles
    @Autowired
    private RolRepository rolRepository;

    // Constructor con inyección de dependencias necesarias
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para registrar un usuario en la base de datos
    @Transactional // Asegura que se ejecuta como una transacción
    public void registrarUsuario(Usuario usuario) {
        // Encriptar la contraseña antes de guardarla
        String contraseniaEncriptada = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(contraseniaEncriptada);

        // Guardar el usuario con la contraseña encriptada
        usuarioRepository.save(usuario);
    }

    // Verifica si ya existe un usuario con ese correo
    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByEmail(correo);
    }

    // Verifica si ya existe un usuario con ese DNI
    public boolean existeDni(String dni) {
        return usuarioRepository.existsByDni(dni);
    }

    // Verifica si ya existe un usuario con ese número de teléfono
    public boolean existeTelefono(String telefono) {
        return usuarioRepository.existsByTelefono(telefono);
    }

    // Devuelve una lista con todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Alias del método anterior para obtener todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Elimina un usuario por su ID
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Obtiene un usuario por su ID o lanza una excepción si no lo encuentra
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licenciado no encontrado con ID: " + id));
    }

    // Actualiza la información de un usuario (asumiendo que el ID ya existe)
    public void updateUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    // Devuelve un tipo por su ID, o lanza excepción si no se encuentra
    public Tipo getTipoById(Long tipoId) {
        return tipoRepository.findById(tipoId)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado con ID: " + tipoId));
    }

    // Lista todos los tipos existentes
    public List<Tipo> getAllTipos() {
        return tipoRepository.findAll();
    }

    // Obtiene una década por su ID
    public Decada getDecadaById(Long decId) {
        return decadaRepository.findById(decId)
                .orElseThrow(() -> new RuntimeException("Decada no encontrada con ID: " + decId));
    }

    // Devuelve todas las décadas existentes
    public List<Decada> getAllDecadas() {
        return decadaRepository.findAll();
    }

    // Devuelve un rol por su ID
    public Rol getRolById(Long rolId) {
        return rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId));
    }

    // Devuelve todos los roles
    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }
}
