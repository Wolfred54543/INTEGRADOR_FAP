package fap_sports.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fap_sports.integrador.models.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Long> {}