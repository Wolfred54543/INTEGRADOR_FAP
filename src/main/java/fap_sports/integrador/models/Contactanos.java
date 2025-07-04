package fap_sports.integrador.models;

import jakarta.persistence.*;

@Entity
@Table(name = "contactanos")
public class Contactanos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de IDs autoincrementales
    @Column(name = "con_id") // Columna que representa el ID del contacto
    private Long conId;

    @Column(name = "con_nombre") // Columna para el nombre
    private String conNombre; 

    @Column(name = "con_apellido") // Columna para el apellido
    private String conApellido; 

    @Column(name = "con_email") // Columna para el email
    private String conEmail;

    @Column(name = "con_telefono") // Columna para almacenar el numero de telefono
    private String conTelefono;

    @Column(name = "con_mensaje") // Columna para almacenar el mensaje
    private String conMensaje;

    // Métodos get y set

    public Long getConId() {
        return conId;
    }

    public void setConId(Long conId) {
        this.conId = conId;
    }

    public String getConNombre() {
        return conNombre;
    }

    public void setConNombre(String conNombre) {
        this.conNombre = conNombre;
    }

    public String getConApellido() {
        return conApellido;
    }

    public void setConApellido(String conApellido) {
        this.conApellido = conApellido;
    }

    public String getConEmail() {
        return conEmail;
    }

    public void setConEmail(String conEmail) {
        this.conEmail = conEmail;
    }

    public String getConTelefono() {
        return conTelefono;
    }

    public void setConTelefono(String conTelefono) {
        this.conTelefono = conTelefono;
    }

    public String getConMensaje() {
        return conMensaje;
    }

    public void setConMensaje(String conMensaje) {
        this.conMensaje = conMensaje;
    }
    
}
