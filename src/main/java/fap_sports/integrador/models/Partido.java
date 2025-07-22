package fap_sports.integrador.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

// Clase que representa la entidad Partido en la base de datos
@Entity
@Table(name = "partidos") // Especifica el nombre de la tabla en la base de datos
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de IDs autoincrementales
    @Column(name = "par_id") // Columna que representa el ID del partido
    private Long parId;

    // Fecha del partido
    @Column(name = "par_fecha") // Columna para la fecha del partido
    private LocalDate parFecha;

    @Column(name = "par_hora") // Columna para la hora del partido
    private LocalTime parHora;

    @Column(name = "par_puntaje_local") // Columna para la hora del partido
    private Integer parPuntajeLocal;

    @Column(name = "par_puntaje_visitante") // Columna para la hora del partido
    private Integer parPuntajeVisitante;

    @ManyToOne // Relación muchos a uno con la entidad Equipo
    @JoinColumn(name = "equipo_local_id") // Columna que representa el equipo local
    private Equipo equipoLocal;

    @ManyToOne // Relación muchos a uno con la entidad Equipo
    @JoinColumn(name = "equipo_visitante_id") // Columna que representa el equipo visitante
    private Equipo equipoVisitante;

    @ManyToOne // Relación muchos a uno con la entidad Campeonato
    @JoinColumn(name = "cam_id") // Columna que representa el campeonato al que pertenece el partido
    private Campeonato campeonato; 
    
    @Column(name = "par_estado") // Columna para el estado del partido
    private String parEstado = "PROGRAMADO";

    //Constructor del estado del partido por defecto
    public Partido() {
        this.parEstado = "PROGRAMADO"; // Valor por defecto en el constructor
    }

    @Column(name = "cam_puntos_local")
    private Integer camPuntosLocal = 0;

    @Column(name = "cam_puntos_visitante")
    private Integer camPuntosVisitante = 0;


    // Métodos get y set
    
    public Long getParId() {
        return parId;
    }

    public void setParId(Long parId) {
        this.parId = parId;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public LocalDate getParFecha() {
        return parFecha;
    }

    public void setParFecha(LocalDate parFecha) {
        this.parFecha = parFecha;
    }

    public LocalTime getParHora() {
        return parHora;
    }

    public void setParHora(LocalTime parHora) {
        this.parHora = parHora;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Integer getParPuntajeLocal() {
        return parPuntajeLocal;
    }

    public void setParPuntajeLocal(Integer parPuntajeLocal) {
        this.parPuntajeLocal = parPuntajeLocal;
    }

    public Integer getParPuntajeVisitante() {
        return parPuntajeVisitante;
    }

    public void setParPuntajeVisitante(Integer parPuntajeVisitante) {
        this.parPuntajeVisitante = parPuntajeVisitante;
    }

    public String getParEstado() {
        return parEstado;
    }

    public void setParEstado(String parEstado) {
        this.parEstado = parEstado;
    }
    public Integer getCamPuntosLocal() {
    return camPuntosLocal;
    }

    public void setCamPuntosLocal(Integer camPuntosLocal) {
        this.camPuntosLocal = camPuntosLocal;
    }

    public Integer getCamPuntosVisitante() {
        return camPuntosVisitante;
    }

    public void setCamPuntosVisitante(Integer camPuntosVisitante) {
        this.camPuntosVisitante = camPuntosVisitante;
    }

    public void calcularPuntos() {
    if (parPuntajeLocal == null || parPuntajeVisitante == null) {
        camPuntosLocal = 0;
        camPuntosVisitante = 0;
        return;
    }

    if (parPuntajeLocal > parPuntajeVisitante) {
        camPuntosLocal = 3;
        camPuntosVisitante = 0;
    } else if (parPuntajeLocal < parPuntajeVisitante) {
        camPuntosLocal = 0;
        camPuntosVisitante = 3;
    } else {
        camPuntosLocal = 1;
        camPuntosVisitante = 1;
    }
}

}
