package fap_sports.integrador.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

// Clase que representa la entidad Campeonato en la base de datos
@Entity
@Table(name = "campeonatos") // Especifica el nombre de la tabla en la base de datos
public class Campeonato {

    @Id // Indica que este es el identificador de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el ID automáticamente
    @Column(name = "cam_id") // Especifica el nombre de la columna en la tabla
    private Long camId; // ID único del campeonato

    @Column(name = "cam_nombre") // Especifica el nombre de la columna
    private String camNombre; // Nombre del campeonato

    @Column(name = "cam_fecha_creacion") // Especifica el nombre de la columna
    private LocalDate camFechaCreacion; // Fecha de creación del campeonato

    @Column(name = "cam_estado") // Especifica el nombre de la columna
    private String camEstado; // Estado del campeonato (por ejemplo, activo, inactivo)

    @Column(name = "cam_total_equipos") // Especifica el nombre de la columna
    private Integer camTotalEquipos; // Total de equipos en el campeonato

    @ManyToOne // Relación muchos a uno con la entidad Decada
    @JoinColumn(name = "dec_id") // Especifica la columna que establece la relación
    private Decada decada; // Década a la que pertenece el campeonato

    @ManyToMany // Relación muchos a muchos con la entidad Equipo
    @JoinTable(
        name = "campeonatos_equipos", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "cam_id"), // Columna que referencia al campeonato
        inverseJoinColumns = @JoinColumn(name = "equ_id") // Columna que referencia al equipo
    )
    private List<Equipo> equipos = new ArrayList<>(); // Lista de equipos que participan en el campeonato

    @OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL) // Relación uno a muchos con CampeonatoEquipo
    private List<CampeonatoEquipo> equiposParticipantes; // Equipos participantes en el campeonato

    
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

