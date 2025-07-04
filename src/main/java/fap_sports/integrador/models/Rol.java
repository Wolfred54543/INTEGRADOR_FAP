package fap_sports.integrador.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Long rolId;

    @Column(name = "rol_nombre", unique = true, nullable = false)
    private String nombre; 

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios; // Relación con Usuario

    // Métodos get y set
    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Long getId() {
        return rolId;
    }

    public void setId(Long rolId) {
        this.rolId = rolId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}