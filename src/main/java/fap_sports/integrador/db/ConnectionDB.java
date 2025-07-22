package fap_sports.integrador.db;

//Apache Commons IO para manejar archivos
import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

// Componente que maneja la conexión a la base de datos
@Component
public class ConnectionDB implements CommandLineRunner {

    @Autowired
    private DataSource dataSource; // Inyección del DataSource para obtener conexiones a la base de datos

    // Método que se ejecuta al iniciar la aplicación
    @Override
    public void run(String... args) {
        try (Connection connection = dataSource.getConnection()) { // Intenta obtener una conexión
            String message = "Conexión a la base de datos establecida\n"; // Mensaje de éxito
            System.out.println(message); // Imprime el mensaje en la consola
            logToFile(message); // Registra el mensaje en un archivo
        } catch (SQLException e) {
            String errorMessage = "Error al acceder a la base de datos: " + e.getMessage() + "\n"; // Mensaje de error
            System.err.println(errorMessage); // Imprime el error en la consola
            logToFile(errorMessage); // Registra el error en un archivo
        }
    }

    // Método para registrar mensajes en un archivo de log
    private void logToFile(String message) {
        File logFile = new File("db_connection.log"); // Archivo de log
        try {
            // Escribe el mensaje en el archivo, en codificación UTF-8, y en modo append
            FileUtils.writeStringToFile(logFile, message, "UTF-8", true);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage()); // Manejo de errores al escribir en el log
        }
    }
    // Ahora no solo imprime mensajes en la consola, sino que también los registra en un archivo,
    // utilizando Apache Commons IO
}