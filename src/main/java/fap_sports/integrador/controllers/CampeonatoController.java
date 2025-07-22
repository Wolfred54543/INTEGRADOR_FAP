package fap_sports.integrador.controllers;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.models.CampeonatoEquipo;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.repositories.CampeonatoEquipoRepository;
import fap_sports.integrador.repositories.DecadaRepository;
// import fap_sports.integrador.repositories.PartidoRepository;
import fap_sports.integrador.services.CampeonatoService;
import fap_sports.integrador.services.EquipoService;
import fap_sports.integrador.services.PartidoService;

// Clase controladora para manejar las solicitudes relacionadas con campeonatos
@Controller
public class CampeonatoController {

    @Autowired
    private CampeonatoService campeonatoService; // Servicio para manejar la lógica de campeonatos

    @Autowired
    private EquipoService equipoService; // Servicio para manejar equipos

    @Autowired
    private PartidoService partidoService; // Servicio para manejar partidos

    @Autowired
    private DecadaRepository decadaRepository; // Repositorio para manejar décadas

    @Autowired
    private CampeonatoEquipoRepository campeonatoEquipoRepository; // Repositorio para manejar relaciones entre campeonatos y equipos

    // @Autowired
    // private PartidoRepository partidoRepository; Repositorio para manejar partidos

    // Mostrar listado de campeonatos
    @GetMapping("/campeonatos")
    public String listarCampeonatos(Model model) {
        model.addAttribute("campeonatos", campeonatoService.listarCampeonatos()); // Agrega la lista de campeonatos al modelo
        return "vistas/administrador/campeonato/ListarCampeonato"; // Retorna la vista para listar campeonatos
    }

    // Mostrar formulario para nuevo campeonato
    @GetMapping("/nuevoCampeonato")
    public String mostrarFormularioNuevoCampeonato(Model model) {
        model.addAttribute("campeonato", new Campeonato()); // Agrega un nuevo objeto Campeonato al modelo
        model.addAttribute("decadas", decadaRepository.findAll()); // Agrega todas las décadas al modelo
        return "vistas/administrador/campeonato/CrearEditarCampeonato"; // Retorna la vista para crear/editar campeonatos
    }

    // Guardar nuevo o editar existente
    @PostMapping("/guardarCampeonato")
    public String guardarCampeonato(@ModelAttribute Campeonato campeonato) {
        campeonatoService.guardarCampeonato(campeonato); // Guarda el campeonato en la base de datos
        return "redirect:/campeonatos"; // Redirige a la lista de campeonatos
    }

    // Mostrar formulario para editar
    @GetMapping("/editarCampeonato/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Campeonato campeonato = campeonatoService.obtenerCampeonatoPorId(id).orElseThrow(); // Obtiene el campeonato por ID

        // Verificamos si ya tiene equipos asignados
        boolean yaTieneFixture = campeonatoEquipoRepository.existsByCampeonato(campeonato); // Verifica si ya tiene equipos asignados
        if (yaTieneFixture) {
            return "vistas/administrador/campeonato/ErrorEditarCampeonato"; // Retorna error si ya tiene fixture
        }

        model.addAttribute("campeonato", campeonato); // Agrega el campeonato al modelo
        model.addAttribute("decadas", decadaRepository.findAll()); // Agrega todas las décadas al modelo
        return "vistas/administrador/campeonato/CrearEditarCampeonato"; // Retorna la vista para crear/editar campeonatos
    }

    // Eliminar campeonato siempre y cuando no tenga reclamos realizados
    @PostMapping("/eliminarCampeonato/{id}")
    public String eliminarCampeonato(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            campeonatoService.eliminarCampeonatoConPartidos(id);
            redirectAttributes.addAttribute("success", "Campeonato eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "No se puede eliminar el campeonato porque guarda un historial de reclamos");
        }
        return "redirect:/campeonatos";
    }



    // Mostrar formulario para asignar equipos a un campeonato
    @GetMapping("/asignarEquipos/{campeonatoId}")
    public String mostrarFormularioAsignarEquipos(@PathVariable Long campeonatoId, Model model) {
        Campeonato campeonato = campeonatoService.obtenerCampeonatoPorId(campeonatoId).orElseThrow(); // Obtiene el campeonato por ID
        model.addAttribute("campeonato", campeonato); // Agrega el campeonato al modelo

        // Verifica si ya hay equipos asignados
        List<CampeonatoEquipo> equiposAsignados = campeonatoEquipoRepository.findByCampeonato(campeonato); // Obtiene equipos asignados
        model.addAttribute("equiposAsignados", equiposAsignados); // Envia los equipos asignados a la vista

        // Condicional para bloquear la asignacion de equipos si ya se a generado el fixture
        if (equiposAsignados != null && !equiposAsignados.isEmpty()) {
            // Ya se generó el fixture
            return "vistas/administrador/campeonato/AsignarEquipos"; // La vista se encargará de mostrar el mensaje de control
        }

        // Obtener equipos por la década del campeonato
        List<Equipo> equiposDisponibles = equipoService.obtenerEquiposPorAnioDecada(campeonato.getDecada().getDecId()); // Obtener equipos disponibles
        model.addAttribute("equiposDisponibles", equiposDisponibles); // Agregar equipos disponibles al modelo

        // Generar una lista de índices [0, 1, ..., camTotalEquipos - 1]
        List<Integer> indices = IntStream.range(0, campeonato.getCamTotalEquipos()).boxed().toList(); // Crea la lista de índices
        model.addAttribute("indices", indices); // Agrega índices al modelo

        return "vistas/administrador/campeonato/AsignarEquipos"; // Retorna la vista para asignar equipos
    }

    // Guardar equipos seleccionados en el campeonato
    @PostMapping("/guardarEquiposEnCampeonato")
    public String guardarEquiposYGenerarFixture(
            @RequestParam Long campeonatoId,
            @RequestParam("equiposSeleccionados") List<Long> equiposSeleccionados,
            Model model) {

        Campeonato campeonato = campeonatoService.obtenerCampeonatoPorId(campeonatoId).orElseThrow(); // Obtiene el campeonato por ID

        List<Equipo> equipos = equipoService.obtenerEquiposPorIds(equiposSeleccionados); // Obtiene los equipos seleccionados

        // Verifica que la cantidad de equipos seleccionados coincida con el total definido
        if (equipos.size() != campeonato.getCamTotalEquipos()) {
            model.addAttribute("error", "La cantidad de equipos seleccionados no coincide con el total definido."); // Agregar un mensaje de error al modelo
            return "redirect:/asignarEquipos/" + campeonatoId; // Redirige de vuelta al formulario de asignación
        }

        // Guardar relaciones en la tabla intermedia
        for (Equipo equipo : equipos) {
            CampeonatoEquipo relacion = new CampeonatoEquipo(); // Crea una nueva relación entre campeonato y equipo
            relacion.setCampeonato(campeonato); // Establece el campeonato
            relacion.setEquipo(equipo); // Establece el equipo
            campeonatoEquipoRepository.save(relacion); // Guarda la relación en la base de datos
        }

        // Generar el fixture ahora que los equipos están registrados
        partidoService.realizarSorteoPorCampeonato(campeonatoId, equipos); // Genera el fixture para el campeonato

        return "redirect:/partidos"; // Redirige a la lista de partidos
    }
}