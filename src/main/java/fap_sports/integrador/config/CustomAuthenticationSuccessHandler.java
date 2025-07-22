package fap_sports.integrador.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

// Anotación que indica que esta clase es un componente de Spring
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // Método que se ejecuta cuando la autenticación es exitosa
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Extrae los roles del usuario autenticado utilizando AuthorityUtils
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Redirecciona al usuario según su rol
        if (roles.contains("ADMINISTRADOR")) {
            // Redirige a la página del administrador
            response.sendRedirect("/admin");
        } else if (roles.contains("DELEGADO")) {
            // Redirige a la página del formulario de reclamos
            response.sendRedirect("/reclamoFormulario");
        } else if (roles.contains("INVITADO")) {
            // Redirige a la página del invitados ya no requiere un cuenta para hacer la redireccion
            response.sendRedirect("/invitado");
        } else if (roles.contains("MIEMBRO DE MESA")) {
            // Redirige a la página del miembro de mesa
            response.sendRedirect("/registrarResultados");
        } else {
            // Ruta por defecto si no se encuentra un rol conocido // modificado para que la redireccion se haga a la pagina de invitados
            response.sendRedirect("/invitado");
        }
    }
}