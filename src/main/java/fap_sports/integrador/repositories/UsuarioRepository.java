package fap_sports.integrador.repositories;

import fap_sports.integrador.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio para la entidad Usuario, extiende JpaRepository para
// proporcionar operaciones CRUD básicas
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para verificar si existe un usuario con el email dado
    boolean existsByEmail(String email);

    // Método para verificar si existe un usuario con el DNI dado
    boolean existsByDni(String dni);

    // Método para verificar si existe un usuario con el teléfono dado
    boolean existsByTelefono(String telefono);
}
