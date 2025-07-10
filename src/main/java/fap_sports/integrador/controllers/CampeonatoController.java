package fap_sports.integrador.controllers;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.models.CampeonatoEquipo;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.repositories.CampeonatoEquipoRepository;
import fap_sports.integrador.repositories.DecadaRepository;
import fap_sports.integrador.repositories.PartidoRepository;
import fap_sports.integrador.services.CampeonatoService;
import fap_sports.integrador.services.EquipoService;
import fap_sports.integrador.services.PartidoService;

@Controller
public class CampeonatoController {

    @Autowired
    private CampeonatoService campeonatoService;

    @Autowired
    private EquipoService equipoService;
    
    @Autowired
    private PartidoService partidoService;

    @Autowired
    private DecadaRepository decadaRepository;

    @Autowired
    private CampeonatoEquipoRepository campeonatoEquipoRepository;

    @Autowired
    private PartidoRepository partidoRepository;


    // 🟢 Mostrar listado de campeonatos
    @GetMapping("/campeonatos")
    public String listarCampeonatos(Model model) {
        model.addAttribute("campeonatos", campeonatoService.listarCampeonatos());
        return "vistas/administrador/campeonato/ListarCampeonato";
    }

    // 🟢 Mostrar formulario para nuevo campeonato
    @GetMapping("/nuevoCampeonato")
    public String mostrarFormularioNuevoCampeonato(Model model) {
        model.addAttribute("campeonato", new Campeonato());
        model.addAttribute("decadas", decadaRepository.findAll());
        return "vistas/administrador/campeonato/CrearEditarCampeonato";
    }

    // 🟢 Guardar nuevo o editar existente
    @PostMapping("/guardarCampeonato")
    public String guardarCampeonato(@ModelAttribute Campeonato campeonato) {
        campeonatoService.guardarCampeonato(campeonato);
        return "redirect:/campeonatos";
    }

    // 🟢 Mostrar formulario para editar
    @GetMapping("/editarCampeonato/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Campeonato campeonato = campeonatoService.obtenerCampeonatoPorId(id).orElseThrow();

        // ⚠ Verificamos si ya tiene equipos asignados
        boolean yaTieneFixture = campeonatoEquipoRepository.existsByCampeonato(campeonato);
        if (yaTieneFixture) {
            model.addAttribute("errorEditar", "⚠️ No puedes editar este campeonato porque ya tiene un fixture generado.");
            return "vistas/administrador/campeonato/ErrorEditarCampeonato";
        }

        model.addAttribute("campeonato", campeonato);
        model.addAttribute("decadas", decadaRepository.findAll());
        return "vistas/administrador/campeonato/CrearEditarCampeonato";
    }


    // 🟢 Eliminar campeonato
    @PostMapping("/eliminarCampeonato/{id}")
    public String eliminarCampeonato(@PathVariable Long id) {
        campeonatoService.eliminarCampeonatoConPartidos(id);
        return "redirect:/campeonatos";
    }


    @GetMapping("/asignarEquipos/{campeonatoId}")
    public String mostrarFormularioAsignarEquipos(@PathVariable Long campeonatoId, Model model) {
        Campeonato campeonato = campeonatoService.obtenerCampeonatoPorId(campeonatoId).orElseThrow();
        model.addAttribute("campeonato", campeonato);

        // Verifica si ya hay equipos asignados
        List<CampeonatoEquipo> equiposAsignados = campeonatoEquipoRepository.findByCampeonato(campeonato);
        model.addAttribute("equiposAsignados", equiposAsignados); // 🔥 Enviamos esto a la vista

        if (equiposAsignados != null && !equiposAsignados.isEmpty()) {
            // Ya se generó el fixture
            return "vistas/administrador/campeonato/AsignarEquipos"; // La vista se encargará de mostrar mensaje
        }

        // Obtener equipos por la década del campeonato
        List<Equipo> equiposDisponibles = equipoService.obtenerEquiposPorAnioDecada(campeonato.getDecada().getDecId());
        model.addAttribute("equiposDisponibles", equiposDisponibles);

        // Generar una lista de índices [0, 1, ..., camTotalEquipos - 1]
        List<Integer> indices = IntStream.range(0, campeonato.getCamTotalEquipos()).boxed().toList();
        model.addAttribute("indices", indices);

        return "vistas/administrador/campeonato/AsignarEquipos";
    }


   @PostMapping("/guardarEquiposEnCampeonato")
    public String guardarEquiposYGenerarFixture(
            @RequestParam Long campeonatoId,
            @RequestParam("equiposSeleccionados") List<Long> equiposSeleccionados,
            Model model) {

        Campeonato campeonato = campeonatoService.obtenerCampeonatoPorId(campeonatoId).orElseThrow();

        List<Equipo> equipos = equipoService.obtenerEquiposPorIds(equiposSeleccionados);

        if (equipos.size() != campeonato.getCamTotalEquipos()) {
            model.addAttribute("error", "La cantidad de equipos seleccionados no coincide con el total definido.");
            return "redirect:/asignarEquipos/" + campeonatoId;
        }

        // 🟨 Paso importante: guardar relaciones en la tabla intermedia
        for (Equipo equipo : equipos) {
            CampeonatoEquipo relacion = new CampeonatoEquipo();
            relacion.setCampeonato(campeonato);
            relacion.setEquipo(equipo);
            campeonatoEquipoRepository.save(relacion);
        }

        // 🔥 Generar el fixture ahora que los equipos están registrados
        partidoService.realizarSorteoPorCampeonato(campeonatoId, equipos);

        return "redirect:/partidos";
    }

}
