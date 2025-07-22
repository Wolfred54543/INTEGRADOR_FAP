// package fap_sports.integrador.controllers;

// import fap_sports.integrador.models.Campeonato;
// import fap_sports.integrador.models.TablaPosiciones;
// import fap_sports.integrador.services.TablaPosicionesService;
// import fap_sports.integrador.repositories.CampeonatoRepository;
// import fap_sports.integrador.repositories.TablaPosicionesRepository;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @Controller
// @RequestMapping("/miembroMesa/tabla-posiciones")
// public class TablaPosicionesController {

//     private final TablaPosicionesService tablaPosicionesService;
//     private final TablaPosicionesRepository tablaPosicionesRepository;
//     private final CampeonatoRepository campeonatoRepository;

//     public TablaPosicionesController(
//         TablaPosicionesService tablaPosicionesService,
//         TablaPosicionesRepository tablaPosicionesRepository,
//         CampeonatoRepository campeonatoRepository
//     ) {
//         this.tablaPosicionesService = tablaPosicionesService;
//         this.tablaPosicionesRepository = tablaPosicionesRepository;
//         this.campeonatoRepository = campeonatoRepository;
//     }

//     @GetMapping("/{campId}")
//     public String verTabla(@PathVariable("campId") Long campId, Model model) {
//         Campeonato campeonato = campeonatoRepository.findById(campId).orElseThrow();
//         List<TablaPosiciones> posiciones = tablaPosicionesRepository
//             .findByCampeonatoOrderByPuntosDescPartidosGanadosDescGolesFavorDesc(campeonato);

//         model.addAttribute("campeonato", campeonato);
//         model.addAttribute("posiciones", posiciones);
//         return "vistas/miembroMesa/tabla_posiciones";
//     }

//     @PostMapping("/actualizar/{campId}")
//     public String actualizarTabla(@PathVariable("campId") Long campId) {
//         Campeonato campeonato = campeonatoRepository.findById(campId).orElseThrow();
//         tablaPosicionesService.actualizarTablaPosiciones(campeonato);
//         return "redirect:/miembroMesa/tabla-posiciones/" + campId + "?actualizado=true";
//     }
// }
