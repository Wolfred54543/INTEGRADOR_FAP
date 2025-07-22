package fap_sports.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fap_sports.integrador.models.Licenciado;

// Repositorio para la entidad Licenciado, extiende JpaRepository para
// proporcionar métodos CRUD y consultas básicas
public interface LicenciadoRepository extends JpaRepository<Licenciado, Long> {
    
}