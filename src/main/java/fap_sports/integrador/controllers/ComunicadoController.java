package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Comunicado;
import fap_sports.integrador.services.ComunicadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService; // Servicio para manejar la lógica de comunicados

    // Ruta donde se almacenarán los documentos adjuntos (Opcional)
    private final String UPLOAD_DIR = "src/main/resources/static/documents/administrador/comunicados/";

    // Controlador para Mostrar los comunicados del administrador
    @GetMapping("/comunicados")
    public String mostrarComunicados(Model model) {
        List<Comunicado> comunicados = comunicadoService.getAllComunicados(); // Obtiene todos los comunicados
        model.addAttribute("comunicados", comunicados); // Agrega la lista de comunicados al modelo
        model.addAttribute("comunicado", new Comunicado()); // Agrega un nuevo objeto Comunicado al modelo
        return "vistas/administrador/comunicado/comunicados"; // Retorna la vista para mostrar comunicados
    }

    // Método para registrar un nuevo comunicado
    @PostMapping("/comunicados")
    public String registrarComunicado(@ModelAttribute Comunicado comunicado,
                                      @RequestParam("archivo") MultipartFile archivo,
                                      Model model) {
        try {
            // Verifica si se ha adjuntado un archivo
            if (!archivo.isEmpty()) {
                String nombreArchivo = archivo.getOriginalFilename(); // Obtiene el nombre del archivo
                Path rutaArchivo = Paths.get(UPLOAD_DIR + nombreArchivo); // Define la ruta completa del archivo
                Files.createDirectories(rutaArchivo.getParent()); // Crea los directorios si no existen
                Files.write(rutaArchivo, archivo.getBytes()); // Escribe el archivo en la ruta especificada
                comunicado.setComDocumento(nombreArchivo); // Establece el nombre del documento en el comunicado
            }

            comunicado.setComFechaPublicacion(LocalDate.now()); // Establece la fecha de publicación del comunicado
            comunicadoService.registrarComunicado(comunicado); // Registra el comunicado en la base de datos
            model.addAttribute("mensaje", "Comunicado registrado correctamente"); // Mensaje de éxito

        } catch (IOException e) {
            model.addAttribute("error", "Error al guardar el archivo"); // Mensaje de error en caso de excepción
        }

        return "redirect:/comunicados"; // Redirige a la lista de comunicados
    }

    // Método para eliminar un comunicado
    @PostMapping("/comunicados/eliminar/{id}")
    public String eliminarComunicado(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            comunicadoService.eliminarComunicado(id);
            redirectAttributes.addAttribute("success", "Comunicado eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "No se puede eliminar el comunicado porque tiene un documento adjunto");
        }
        return "redirect:/comunicados"; // Redirige a la lista de comunicados
    }

    // Método para editar un comunicado existente
    @GetMapping("/comunicados/editar/{id}")
    public String editarComunicado(@PathVariable Long id, Model model) {
        Comunicado comunicado = comunicadoService.getComunicadoById(id); // Obtiene el comunicado por ID
        model.addAttribute("comunicado", comunicado); // Pasa el comunicado al formulario de edición
        model.addAttribute("comunicados", comunicadoService.getAllComunicados()); // Pasa la lista de comunicados al modelo
        return "vistas/administrador/comunicado/comunicados"; // Retorna la vista para editar comunicados
    }

    // Método para guardar el comunicado editado
    @PostMapping("/comunicados/guardar")
    public String guardarComunicado(@ModelAttribute Comunicado comunicado,
                                    @RequestParam("archivo") MultipartFile archivo) {
        try {
            if (!archivo.isEmpty()) {
                String nombreArchivo = archivo.getOriginalFilename();
                Path rutaArchivo = Paths.get(UPLOAD_DIR + nombreArchivo);
                Files.createDirectories(rutaArchivo.getParent());
                Files.write(rutaArchivo, archivo.getBytes());
                comunicado.setComDocumento(nombreArchivo);
            }

            comunicadoService.saveComunicado(comunicado);
        } catch (IOException e) {
            e.printStackTrace(); // Mostrar error en consola para debug
        }

        return "redirect:/comunicados";
    }


    // Método para actualizar un comunicado existente
    @PostMapping("/comunicados/actualizar")
    public String actualizarComunicado(@ModelAttribute Comunicado comunicado,
                                       @RequestParam("archivo") MultipartFile archivo) {
        try {
            // Verifica si se ha adjuntado un archivo
            if (!archivo.isEmpty()) {
                String nombreArchivo = archivo.getOriginalFilename(); // Obtiene el nombre del archivo
                Path rutaArchivo = Paths.get(UPLOAD_DIR + nombreArchivo); // Define la ruta completa del archivo
                Files.createDirectories(rutaArchivo.getParent()); // Crea los directorios si no existen
                Files.write(rutaArchivo, archivo.getBytes()); // Escribe el archivo en la ruta especificada
                comunicado.setComDocumento(nombreArchivo); // Establece el nombre del documento en el comunicado
            }
            comunicadoService.actualizarComunicado(comunicado); // Actualiza el comunicado en la base de datos

        } catch (IOException e) {
            // Manejo de errores (opcional)
        }

        return "redirect:/comunicados"; // Redirige a la lista de comunicados
    }

    // Método para publicar un comunicado
    @PostMapping("/comunicados/publicar/{id}")
    public String publicarComunicado(@PathVariable Long id) {
        comunicadoService.publicarComunicado(id); // Publica el comunicado por ID
        return "redirect:/comunicados"; // Redirige a la lista de comunicados
    }
}