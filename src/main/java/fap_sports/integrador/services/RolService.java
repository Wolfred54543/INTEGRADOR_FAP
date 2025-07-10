package fap_sports.integrador.services;

import fap_sports.integrador.models.Rol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    // Logger para registrar información útil del servicio
    private static final Logger logger = LoggerFactory.getLogger(RolService.class);

    // Objeto JdbcTemplate para realizar operaciones directas en la base de datos
    private final JdbcTemplate jdbcTemplate;

    // Constructor con inyección de dependencia para JdbcTemplate
    public RolService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Método para listar todos los roles existentes en la base de datos
    public List<Rol> listarRoles() {
        String sql = "SELECT rol_id, rol_nombre FROM roles"; // Consulta SQL
        logger.info("Ejecutando consulta SQL: {}", sql); // Log de la operación
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Rol rol = new Rol();
            rol.setId(rs.getLong("rol_id")); // Asignar ID del rol
            rol.setNombre(rs.getString("rol_nombre")); // Asignar nombre del rol
            return rol;
        });
    }

    // Método para obtener un rol específico por su ID
    @SuppressWarnings("deprecation")
    public Rol obtenerRolPorId(Long id) {
        String sql = "SELECT rol_id, rol_nombre FROM roles WHERE rol_id = ?"; // Consulta SQL
        logger.info("Ejecutando consulta SQL: {}", sql); // Log de la consulta
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Rol rol = new Rol();
                rol.setId(rs.getLong("rol_id")); // Asignar ID
                rol.setNombre(rs.getString("rol_nombre")); // Asignar nombre
                return rol;
            });
        } catch (Exception e) {
            // Manejo de error si no se encuentra el rol o falla la consulta
            logger.error("Error al obtener el rol con ID: {}", id, e);
            return null;
        }
    }
}
