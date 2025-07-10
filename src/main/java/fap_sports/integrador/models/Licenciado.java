package fap_sports.integrador.models;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "licenciados")
public class Licenciado {
    // Identificador único del licenciado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lic_id") 
    private Long licId;

    // Nombre del licenciado
    @Column(name = "lic_nombre")
    private String licNombre; 

    // Apellido del licenciado
    @Column(name = "lic_apellido")
    private String licApellido;

    // DNI del licenciado
    @Column(name = "lic_dni")
    private String licDni;

    // Teléfono del licenciado
    @Column(name = "lic_telefono")
    private String licTelefono;

    // Correo electrónico del licenciado
    @Column(name = "lic_correo")
    private String licCorreo;

    // Fecha de nacimiento del licenciado
    @Column(name = "lic_fecha_nacimiento")
    private LocalDate licFechaNacimiento;

    // Tipo asociado al licenciado
    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    // Década asociada al licenciado
    @ManyToOne
    @JoinColumn(name = "dec_id")
    private Decada decada;

    // Métodos get y set
    public Long getLicId() {
        return licId;
    }

    public void setLicId(Long licId) {
        this.licId = licId;
    }

    public String getLicNombre() {
        return licNombre;
    }

    public void setLicNombre(String licNombre) {
        this.licNombre = licNombre;
    }

    public String getLicApellido() {
        return licApellido;
    }

    public void setLicApellido(String licApellido) {
        this.licApellido = licApellido;
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

    public String getLicDni() {
        return licDni;
    }

    public void setLicDni(String licDni) {
        this.licDni = licDni;
    }

    public String getLicTelefono() {
        return licTelefono;
    }

    public void setLicTelefono(String licTelefono) {
        this.licTelefono = licTelefono;
    }

    public String getLicCorreo() {
        return licCorreo;
    }

    public void setLicCorreo(String licCorreo) {
        this.licCorreo = licCorreo;
    }

    public LocalDate getLicFechaNacimiento() {
        return licFechaNacimiento;
    }

    public void setLicFechaNacimiento(LocalDate licFechaNacimiento) {
        this.licFechaNacimiento = licFechaNacimiento;
    }

}
