package fap_sports.integrador.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.data.domain.Page;

import fap_sports.integrador.models.Noticia;
import fap_sports.integrador.services.NoticiaService;

// Clase controladora para manejar las operaciones relacionadas con noticias
@Controller
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService; // Inyecta el servicio que maneja la lógica de negocio relacionada a noticias

    // Muestra el formulario para crear o listar noticias
    @GetMapping("/formularioNoticias")
    public String formularioNoticias(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "buscar", required = false) String buscar,
            Model model) {

        Page<Noticia> noticiasPaginadas;

        if (buscar != null && !buscar.isEmpty()) {
            noticiasPaginadas = noticiaService.buscarPorTituloSubtituloAutor(buscar, PageRequest.of(page, size));
            model.addAttribute("buscar", buscar); // para mantener el valor en el input
        } else {
            noticiasPaginadas = noticiaService.listarTodosPaginados(PageRequest.of(page, size));
        }

        model.addAttribute("noticia", new Noticia()); // objeto para el formulario
        model.addAttribute("noticias", noticiasPaginadas.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", noticiasPaginadas.getTotalPages());

        return "vistas/administrador/noticia/noticiaFormulario";
    }


    // Procesa el registro de una nueva noticia
    @PostMapping("/registrarNoticias")
    public String registrarNoticias(@ModelAttribute Noticia noticia) {
        noticiaService.registrarNoticia(noticia); // Guarda la nueva noticia
        return "redirect:/formularioNoticias"; // Redirige para evitar reenvío del formulario y actualizar la lista
    }

    // Muestra el formulario para editar una noticia existente
    @GetMapping("/editarNoticia/{id}")
    public String editarNoticia(@PathVariable("id") Long id, Model model) {
        Noticia noticia = noticiaService.getNoticiaById(id); // Obtiene la noticia por ID
        if (noticia == null) {
            return "redirect:/formularioNoticias"; // Redirige si la noticia no existe
        }
        model.addAttribute("noticia", noticia); // Agrega la noticia al modelo
        model.addAttribute("noticias", noticiaService.getAllNoticias()); // Lista de noticias existentes
        return "vistas/administrador/noticia/editarNoticia"; // Retorna la vista de edición de noticia
    }

    // Procesa la actualización de una noticia
    @PostMapping("/actualizarNoticia")
    public String actualizarNoticia(@ModelAttribute Noticia noticia) {
        Noticia noticiaExistente = noticiaService.getNoticiaById(noticia.getNotId()); // Obtiene la noticia existente
        if (noticiaExistente != null) {
            noticia.setNotFechaCreacion(noticiaExistente.getNotFechaCreacion()); // Mantiene la fecha de creación
        }
        noticiaService.registrarNoticia(noticia); // Actualiza la noticia
        return "redirect:/formularioNoticias"; // Redirige al formulario de noticias
    }

    // Elimina una noticia por ID
    @PostMapping("/eliminarNoticia/{id}")
    public String eliminarNoticia(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            noticiaService.deleteNoticia(id);
            redirectAttributes.addAttribute("success", "Noticia eliminada correctamente");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "No se puede eliminar la noticia");
        }
        return "redirect:/formularioNoticias"; // Redirige al formulario de noticias
    }

    // Muestra la página de noticias para invitados, con noticias principales y secundarias
    @GetMapping("/noticias")
    public String noticias(Model model) {
        List<Noticia> noticiasPrincipales = noticiaService.obtenerNoticiasPorTipo("principal"); // Obtiene noticias principales
        List<Noticia> noticiasSecundarias = noticiaService.obtenerNoticiasPorTipo("secundaria"); // Obtiene noticias secundarias

        model.addAttribute("noticiasPrincipales", noticiasPrincipales); // Agrega noticias principales al modelo
        model.addAttribute("noticiasSecundarias", noticiasSecundarias); // Agrega noticias secundarias al modelo
        return "vistas/invitados/noticias"; // Retorna la vista de noticias para invitados
    }

    // Muestra una noticia específica para invitados
    @GetMapping("/postNoticia/{id}")
    public String verNoticia(@PathVariable("id") Long id, Model model) {
        Noticia noticia = noticiaService.getNoticiaById(id); // Obtiene la noticia por ID
        if (noticia == null) {
            return "redirect:/noticias"; // Redirige si la noticia no existe (PENDIENTE: establecer página 404)
        }
        model.addAttribute("noticia", noticia); // Agrega la noticia al modelo
        return "vistas/invitados/postNoticia"; // Retorna la vista del elemento
    }
}