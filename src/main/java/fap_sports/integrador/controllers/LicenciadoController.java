package fap_sports.integrador.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.models.Licenciado;
import fap_sports.integrador.models.Tipo;
import fap_sports.integrador.services.LicenciadoService;

@Controller
public class LicenciadoController {

    @Autowired
    private LicenciadoService licenciadoService;

    // Muestra el formulario de registro de licenciado junto con listas de tipos, décadas y licenciados
    @GetMapping("/registrolicenciado")
    public String showRegistrationForm(Model model) {
        model.addAttribute("licenciado", new Licenciado());
        model.addAttribute("tipos", licenciadoService.getAllTipos());
        model.addAttribute("decadas", licenciadoService.getAllDecadas());
        model.addAttribute("licenciados", licenciadoService.getAllLicenciados());
        return "vistas/registroLic";
    }

    // Procesa el registro de un nuevo licenciado, asignando tipo y década seleccionados
    @PostMapping("/registrolicenciado")
    public String registerLicenciado(@ModelAttribute Licenciado licenciado, @RequestParam Long tipoId, @RequestParam Long decId) {
        Tipo tipo = licenciadoService.getTipoById(tipoId);
        Decada decada = licenciadoService.getDecadaById(decId);
        licenciado.setTipo(tipo);
        licenciado.setDecada(decada);
        licenciadoService.registerLicenciado(licenciado);
        return "redirect:/registrolicenciado";  // Redirige para evitar reenvío de formulario
    }

    // Muestra la lista de todos los licenciados registrados
    @GetMapping("/licenciados")
    public String listLicenciados(Model model) {
        List<Licenciado> licenciados = licenciadoService.getAllLicenciados();
        model.addAttribute("licenciados", licenciados);
        return "vistas/licenciados";
    }

    // Elimina un licenciado por ID y redirige a la página de registro
    @PostMapping("/eliminarlicenciado/{id}")
    public String deleteLicenciado(@PathVariable Long id) {
        licenciadoService.deleteLicenciado(id);
        return "redirect:/registrolicenciado";
    }

    // Muestra el formulario para editar un licenciado existente, cargando datos y listas de tipos y décadas
    @GetMapping("/editarlicenciado/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Licenciado licenciado = licenciadoService.getLicenciadoById(id);
        model.addAttribute("licenciado", licenciado);
        model.addAttribute("tipos", licenciadoService.getAllTipos());
        model.addAttribute("decadas", licenciadoService.getAllDecadas());
        return "vistas/editarLicenciado";
    }

    // Procesa la actualización de un licenciado y redirige a la página principal de registro
    @PostMapping("/actualizarlicenciado")
    public String updateLicenciado(@ModelAttribute Licenciado licenciado) {
        licenciadoService.updateLicenciado(licenciado);
        return "redirect:/registrolicenciado";
    }
}
