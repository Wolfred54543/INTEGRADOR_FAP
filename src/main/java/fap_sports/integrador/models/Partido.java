package fap_sports.integrador.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "partidos")
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "par_id") 
    private Long parId;

    // Fecha del partido
    @Column(name = "par_fecha")
    private LocalDate parFecha;

    @Column(name = "par_hora")
    private LocalTime parHora;

    @ManyToOne
    @JoinColumn(name = "equipo_local_id")
    private Equipo equipoLocal;

    @ManyToOne
    @JoinColumn(name = "equipo_visitante_id")
    private Equipo equipoVisitante;

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

    
}