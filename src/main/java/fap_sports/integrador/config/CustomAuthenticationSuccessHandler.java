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

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Extrae los roles del usuario autenticado
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Redirecciona al usuario según su rol
        if (roles.contains("ADMINISTRADOR")) {
            response.sendRedirect("/admin");
        } else if (roles.contains("DELEGADO")) {
            response.sendRedirect("/reclamoFormulario");
        } else if (roles.contains("INVITADO")) {
            response.sendRedirect("/invitado");
        } else if (roles.contains("MIEMBRO DE MESA")) {
            response.sendRedirect("/miembroMesa");
        } else {
            // Ruta por defecto si no se encuentra un rol conocido
            response.sendRedirect("/invitado");
        }
    }
}
