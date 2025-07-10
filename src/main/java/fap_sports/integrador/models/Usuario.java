package fap_sports.integrador.models;

import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class Usuario {

    // Identificador único del usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Long usuId;

    // Nombre del usuario
    @Column(name = "usu_nombres", nullable = false)
    private String nombre;

    // Apellido del usuario
    @Column(name = "usu_apellidos", nullable = false)
    private String apellido;

    // Fecha de nacimiento del usuario
    @Column(name = "usu_fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    // Correo electrónico del usuario (único y obligatorio)
    @Column(name = "usu_correo", unique = true, nullable = false)
    private String email;

    // Contraseña del usuario (única y obligatoria)
    @Column(name = "usu_contrasenia", unique = true, nullable = false)
    private String contrasenia;

    // DNI del usuario (único y obligatorio)
    @Column(name = "usu_dni", unique = true, nullable = false)
    private String dni;

    // Teléfono del usuario (único y obligatorio)
    @Column(name = "usu_telefono", unique = true, nullable = false)
    private String telefono;

    // Tipo asociado al usuario
    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    // Década asociada al usuario
    @ManyToOne
    @JoinColumn(name = "dec_id")
    private Decada decada;

    // Roles asociados al usuario
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usu_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;

    // Métodos get y set
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Decada getDecada() {
        return decada;
    }

    public void setDecada(Decada decada) {
        this.decada = decada;
    }

    public Long getUsuId() {
        return usuId;
    }

    public void setUsuId(Long usuId) {
        this.usuId = usuId;
    }
}
