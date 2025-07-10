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

@Entity
@Table(name = "reclamos")
public class Reclamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_id")
    private Long recId;

    @Column(name = "rec_motivo")
    private String recMotivo;

    @Column(name = "rec_descripcion")
    private String recDescripcion;

    @Column(name = "rec_solicitud")
    private String recSolicitud;

    @Column(name = "rec_respuesta")
    private String recRespuesta;

    @Column(name = "rec_estado")
    private String recEstado;

    @Column(name = "rec_fecha")
    private LocalDate recFecha;

    @Column(name = "rec_hora")
    private LocalTime recHora;

    @ManyToOne
    @JoinColumn(name = "par_id")
    private Partido partidoReferencia;

    // Constructor
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