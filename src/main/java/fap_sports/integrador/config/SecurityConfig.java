package fap_sports.integrador.config;

import fap_sports.integrador.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// Clase de configuración de seguridad de Spring
@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad web
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService; // Servicio personalizado para cargar los detalles del usuario

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler; // Manejador personalizado para redirección post-login
    
    // Bean para encriptar contraseñas usando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Se utiliza BCrypt para encriptar las contraseñas
    }

    // Proveedor de autenticación utilizando DaoAuthenticationProvider
    @SuppressWarnings("deprecation")
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Usa el servicio personalizado para autenticación
        authProvider.setPasswordEncoder(passwordEncoder()); // Usa el encoder configurado
        return authProvider;
    }

    // Provee el AuthenticationManager a partir de la configuración
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Provee el AuthenticationManager a partir de la configuración
    }

    // Configuración de la cadena de filtros de seguridad
    @SuppressWarnings({ "deprecation", "removal" })
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Se permite el acceso público a rutas estáticas y de registro/login
                .requestMatchers(new AntPathRequestMatcher("/invitado")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/jugadores")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/partidoInvitados")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/noticias")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/postNoticia/{id}")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/contactanos")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/comunicadoInvitados")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/registro")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll() // Permite acceso a archivos CSS
                .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll() // Permite acceso a archivos JS
                .requestMatchers(new AntPathRequestMatcher("/imagenes/**")).permitAll() // Permite acceso a imágenes
                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Página de login personalizada
                .successHandler(customAuthenticationSuccessHandler) // Usa el manejador personalizado de éxito
                .permitAll() // Permite acceso a la página de login para todos
            )
            .logout(logout -> logout
                .permitAll()); // Permite logout sin restricciones

        http.authenticationProvider(authenticationProvider()); // Registra el proveedor de autenticación personalizado
        return http.build(); // Devuelve la cadena de filtros configurada
    }
}