package fap_sports.integrador.models;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo") // Campo para el tipo de incidencia
    private String tipo; // Ej: Gol, Falta, Amarilla, Roja, Sanción

    @Column(name = "cantidad") // Campo para la cantidad de incidencias
    private int cantidad;

    @Column(name = "inc_descripcion") // Campo para la descripción
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "jug_id") // Relación con Jugador
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "par_id") // Relación con Partido
    private Partido partido;

    private LocalDate fechaRegistro; // Fecha de registro de la incidencia

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}