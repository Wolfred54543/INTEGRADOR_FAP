package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Comunicado;
import fap_sports.integrador.services.ComunicadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService;

    // Ruta donde se almacenarán los documentos adjuntos
    private final String UPLOAD_DIR = "src/main/resources/static/documents/comunicados/";

    //Controlador para Mostrar los comunicados del administrador
    @GetMapping("/comunicados")
    public String mostrarComunicados(Model model) {
        List<Comunicado> comunicados = comunicadoService.getAllComunicados();
        model.addAttribute("comunicados", comunicados);
        model.addAttribute("comunicado", new Comunicado());
        return "vistas/comunicados"; // Ruta del HTML
    }

    @PostMapping("/comunicados")
    public String registrarComunicado(@ModelAttribute Comunicado comunicado,
                                      @RequestParam("archivo") MultipartFile archivo,
                                      Model model) {
        try {
            if (!archivo.isEmpty()) {
                String nombreArchivo = archivo.getOriginalFilename();
                Path rutaArchivo = Paths.get(UPLOAD_DIR + nombreArchivo);
                Files.createDirectories(rutaArchivo.getParent());
                Files.write(rutaArchivo, archivo.getBytes());
                comunicado.setComDocumento(nombreArchivo);
            }

            comunicado.setComFechaPublicacion(LocalDate.now());
            comunicadoService.registrarComunicado(comunicado);
            model.addAttribute("mensaje", "✅ Comunicado registrado correctamente");

        } catch (IOException e) {
            model.addAttribute("error", "❌ Error al guardar el archivo");
        }

        return "redirect:/comunicados";
    }

    @PostMapping("/comunicados/eliminar/{id}")
    public String eliminarComunicado(@PathVariable("id") Long id) {
        comunicadoService.eliminarComunicado(id);
        return "redirect:/comunicados";
    }

@GetMapping("/comunicados/editar/{id}")
public String editarComunicado(@PathVariable Long id, Model model) {
    Comunicado comunicado = comunicadoService.getComunicadoById(id);
    model.addAttribute("comunicado", comunicado); // se pasa al formulario
    model.addAttribute("comunicados", comunicadoService.getAllComunicados()); // pasa a la lista
    return "vistas/comunicados"; // HTML de la vista
}

@PostMapping("/comunicados/guardar")
public String guardarComunicado(@ModelAttribute Comunicado comunicado,
                                @RequestParam("archivo") MultipartFile archivo) throws IOException {
    if (!archivo.isEmpty()) {
        comunicado.setComDocumento(archivo.getOriginalFilename());
    }
    comunicadoService.saveComunicado(comunicado);
    return "redirect:/comunicados"; // vuelve al formulario
}


    @PostMapping("/comunicados/actualizar")
    public String actualizarComunicado(@ModelAttribute Comunicado comunicado,
                                       @RequestParam("archivo") MultipartFile archivo) {
        try {
            if (!archivo.isEmpty()) {
                String nombreArchivo = archivo.getOriginalFilename();
                Path rutaArchivo = Paths.get(UPLOAD_DIR + nombreArchivo);
                Files.createDirectories(rutaArchivo.getParent());
                Files.write(rutaArchivo, archivo.getBytes());
                comunicado.setComDocumento(nombreArchivo);
            }
            comunicadoService.actualizarComunicado(comunicado);

        } catch (IOException e) {
            // Manejo de errores (opcional)
        }

        return "redirect:/comunicados";
    }

    @PostMapping("/comunicados/publicar/{id}")
    public String publicarComunicado(@PathVariable Long id) {
        comunicadoService.publicarComunicado(id);
        return "redirect:/comunicados"; // nos regresa a la vista que estamos usando
    }

}
