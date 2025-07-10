package fap_sports.integrador.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "campeonatos")
public class Campeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cam_id") 
    private Long camId;

    @Column(name = "cam_nombre")
    private String camNombre;

    @Column(name = "cam_fecha_creacion")
    private LocalDate camFechaCreacion;

    @Column(name = "cam_estado")
    private String camEstado;

    @Column(name = "cam_total_equipos")
    private Integer camTotalEquipos;

    @ManyToOne
    @JoinColumn(name = "dec_id")
    private Decada decada;

    @ManyToMany
    @JoinTable(
        name = "campeonatos_equipos",
        joinColumns = @JoinColumn(name = "cam_id"),
        inverseJoinColumns = @JoinColumn(name = "equ_id")
    )
    private List<Equipo> equipos = new ArrayList<>();


    @OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL)
    private List<CampeonatoEquipo> equiposParticipantes;


    // Getters y setters
    
    public Long getCamId() {
        return camId;
    }

    public void setCamId(Long camId) {
        this.camId = camId;
    }

    public String getCamNombre() {
        return camNombre;
    }

    public void setCamNombre(String camNombre) {
        this.camNombre = camNombre;
    }

    public LocalDate getCamFechaCreacion() {
        return camFechaCreacion;
    }

    public void setCamFechaCreacion(LocalDate camFechaCreacion) {
        this.camFechaCreacion = camFechaCreacion;
    }

    public String getCamEstado() {
        return camEstado;
    }

    public void setCamEstado(String camEstado) {
        this.camEstado = camEstado;
    }

    public Decada getDecada() {
        return decada;
    }

    public void setDecada(Decada decada) {
        this.decada = decada;
    }

    public Integer getCamTotalEquipos() {
        return camTotalEquipos;
    }

    public void setCamTotalEquipos(Integer camTotalEquipos) {
        this.camTotalEquipos = camTotalEquipos;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public List<CampeonatoEquipo> getEquiposParticipantes() {
        return equiposParticipantes;
    }

    public void setEquiposParticipantes(List<CampeonatoEquipo> equiposParticipantes) {
        this.equiposParticipantes = equiposParticipantes;
    }

}

