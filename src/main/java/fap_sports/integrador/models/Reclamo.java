package fap_sports.integrador.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

// Clase que representa la entidad Reclamo en la base de datos
@Entity
@Table(name = "reclamos") // Especifica el nombre de la tabla en la base de datos
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de IDs autoincrementales
    @Column(name = "rec_id") // Columna que representa el ID del reclamo
    private Long recId;

    @Column(name = "rec_motivo") // Columna para el motivo del reclamo
    private String recMotivo;

    @Column(name = "rec_descripcion") // Columna para la descripción del reclamo
    private String recDescripcion;

    @Column(name = "rec_solicitud") // Columna para la solicitud relacionada con el reclamo
    private String recSolicitud;

    @Column(name = "rec_respuesta") // Columna para la respuesta al reclamo
    private String recRespuesta;

    @Column(name = "rec_estado") // Columna para el estado del reclamo (activo, cerrado, etc.)
    private String recEstado;

    @Column(name = "rec_fecha") // Columna para la fecha del reclamo
    private LocalDate recFecha;

    @Column(name = "rec_hora") // Columna para la hora del reclamo
    private LocalTime recHora;

    @ManyToOne // Relación muchos a uno con la entidad Partido
    @JoinColumn(name = "par_id") // Columna que representa el partido al que se refiere el reclamo
    private Partido partidoReferencia; 


    // Constructores

    public Reclamo(String recMotivo, String recDescripcion, String recSolicitud, LocalDate recFecha, LocalTime recHora, Partido partidoReferencia) {
        this.recMotivo = recMotivo;
        this.recDescripcion = recDescripcion;
        this.recSolicitud = recSolicitud;
        this.recFecha = recFecha;
        this.recHora = recHora;
        this.partidoReferencia = partidoReferencia;
    }

    public Reclamo() {
    }


    // Métodos get y set

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public String getRecMotivo() {
        return recMotivo;
    }

    public void setRecMotivo(String recMotivo) {
        this.recMotivo = recMotivo;
    }

    public String getRecDescripcion() {
        return recDescripcion;
    }

    public void setRecDescripcion(String recDescripcion) {
        this.recDescripcion = recDescripcion;
    }

    public String getRecSolicitud() {
        return recSolicitud;
    }

    public void setRecSolicitud(String recSolicitud) {
        this.recSolicitud = recSolicitud;
    }

    public String getRecRespuesta() {
        return recRespuesta;
    }

    public void setRecRespuesta(String recRespuesta) {
        this.recRespuesta = recRespuesta;
    }

    public String getRecEstado() {
        return recEstado;
    }

    public void setRecEstado(String recEstado) {
        this.recEstado = recEstado;
    }

    public LocalDate getRecFecha() {
        return recFecha;
    }

    public void setRecFecha(LocalDate recFecha) {
        this.recFecha = recFecha;
    }

    public LocalTime getRecHora() {
        return recHora;
    }

    public void setRecHora(LocalTime recHora) {
        this.recHora = recHora;
    }

    public Partido getPartidoReferencia() {
        return partidoReferencia;
    }

    public void setPartidoReferencia(Partido partidoReferencia) {
        this.partidoReferencia = partidoReferencia;
    }
}