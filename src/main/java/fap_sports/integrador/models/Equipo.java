package fap_sports.integrador.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "equipos")
public class Equipo {
    // Identificador único del equipo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equ_id") 
    private Long equId;

    // Nombre del equipo
    @Column(name = "equ_nombre")
    private String equNombre; 

    // Descripción del equipo
    @Column(name = "equ_descripcion")
    private String equDescripcion;

    // URL del logo del equipo
    @Column(name = "equ_url_logo")
    private String equLogo;

    // Década a la que pertenece el equipo
    @ManyToOne
    @JoinColumn(name = "dec_id")
    private Decada decada;

    // Usuario delegado asignado al equipo
    @ManyToOne
    @JoinColumn(name = "delegado_id")
    private Usuario delegado;

    // Roles asociados al equipo
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usu_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;

    // Métodos get y set
    public Long getEquId() {
        return equId;
    }

    public void setEquId(Long equId) {
        this.equId = equId;
    }

    public String getEquNombre() {
        return equNombre;
    }

    public void setEquNombre(String equNombre) {
        this.equNombre = equNombre;
    }

    public String getEquDescripcion() {
        return equDescripcion;
    }

    public void setEquDescripcion(String equDescripcion) {
        this.equDescripcion = equDescripcion;
    }

    public String getEquLogo() {
        return equLogo;
    }

    public void setEquLogo(String equLogo) {
        this.equLogo = equLogo;
    }

    public Decada getDecada() {
        return decada;
    }

    public void setDecada(Decada decada) {
        this.decada = decada;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Usuario getDelegado() {
        return delegado;
    }

    public void setDelegado(Usuario delegado) {
        this.delegado = delegado;
    }
}
