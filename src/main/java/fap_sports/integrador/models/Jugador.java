package fap_sports.integrador.models;


import jakarta.persistence.*;

@Entity
@Table(name = "jugadores")
public class Jugador {
    // Identificador único del jugador
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jug_id")
    private Long jugId;

    // Nombre del jugador
    @Column(name = "jug_nombre")
    private String jugNombre;

    // Apellido del jugador
    @Column(name = "jug_apellido")
    private String jugApellido;

    // DNI del jugador
    @Column(name = "jug_dni")
    private String jugDni;

    // Década a la que pertenece el jugador
    @ManyToOne
    @JoinColumn(name = "dec_id")
    private Decada decada;

    // Equipo al que pertenece el jugador
    @ManyToOne
    @JoinColumn(name = "equ_id")
    private Equipo equipo;

    // Métodos get y set
    public Long getJugId() {
        return jugId;
    }

    public void setJugId(Long jugId) {
        this.jugId = jugId;
    }

    public String getJugNombre() {
        return jugNombre;
    }

    public void setJugNombre(String jugNombre) {
        this.jugNombre = jugNombre;
    }

    public String getJugApellido() {
        return jugApellido;
    }

    public void setJugApellido(String jugApellido) {
        this.jugApellido = jugApellido;
    }

    public String getJugDni() {
        return jugDni;
    }

    public void setJugDni(String jugDni) {
        this.jugDni = jugDni;
    }

    public Decada getDecada() {
        return decada;
    }

    public void setDecada(Decada decada) {
        this.decada = decada;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}

