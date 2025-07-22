package fap_sports.integrador.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fap_sports.integrador.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// Repositorio para la entidad Usuario, extiende JpaRepository para
// proporcionar operaciones CRUD básicas
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para verificar si existe un usuario con el email
    boolean existsByEmail(String email);

    // Método para verificar si existe un usuario con el DNI
    boolean existsByDni(String dni);

    // Método para verificar si existe un usuario con el teléfono
    boolean existsByTelefono(String telefono);

    // Consulta para filtrar usuarios por nombre, apellido y dni
    @Query("SELECT u FROM Usuario u WHERE " +
       "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
       "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
       "u.dni LIKE CONCAT('%', :filtro, '%')")
    Page<Usuario> buscarPorNombreApellidoDni(@Param("filtro") String filtro, Pageable pageable);

}
