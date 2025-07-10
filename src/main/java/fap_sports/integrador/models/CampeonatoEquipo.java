package fap_sports.integrador.models;

import jakarta.persistence.*;

@Entity
@Table(name = "campeonato_equipos")
public class CampeonatoEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cam_equ_id") 
    private Long camEquId;

    @ManyToOne
    @JoinColumn(name = "cam_id")
    private Campeonato campeonato;

    @ManyToOne
    @JoinColumn(name = "equ_id")
    private Equipo equipo;

    
    // Getters y setters
    
    public Long getCamEquId() {
        return camEquId;
    }

    public void setCamEquId(Long camEquId) {
        this.camEquId = camEquId;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }


}
