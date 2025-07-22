package fap_sports.integrador.controllers; // ← Cambia el paquete si lo prefieres en otro lugar (mejor fuera de `repositories`)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.repositories.CampeonatoRepository;

@ControllerAdvice
public class GlobalModelAttribute {

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    @ModelAttribute("camp")
    public Campeonato agregarCampeonato() {
        return campeonatoRepository
                .findFirstByCamEstadoOrderByCamIdDesc("Activo")
                .orElse(null); // Devuelve el último campeonato activo o null si no hay
    }
}
