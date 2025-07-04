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

@Component
public class ConnectionDB implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) {
        try (Connection connection = dataSource.getConnection()) {
            String message = "Conexión a la base de datos establecida\n";
            System.out.println(message);
            logToFile(message);
        } catch (SQLException e) {
            String errorMessage = "Error al acceder a la base de datos: " + e.getMessage() + "\n";
            System.err.println(errorMessage);
            logToFile(errorMessage);
        }
    }

    private void logToFile(String message) {
        File logFile = new File("db_connection.log");
        try {
            FileUtils.writeStringToFile(logFile, message, "UTF-8", true);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }
    //Ahora no solo imprime mensajes en la consola, sino que también los registra en un archivo,
    // utilizando Apache Commons IO
}