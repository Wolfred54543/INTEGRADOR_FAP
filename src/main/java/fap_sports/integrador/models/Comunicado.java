package fap_sports.integrador.models;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "comunicados") // Mapea esta clase a la tabla 'comunicados' en la base de datos
public class Comunicado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de IDs autoincrementales
    @Column(name = "com_id") // Columna que representa el ID del comunicado
    private Long comId;

    @Column(name = "com_tipo") // Columna para el título del comunicado
    private String comTipo; 

    @Column(name = "com_titulo") // Columna para el título del comunicado
    private String comTitulo; 

    @Column(name = "com_fecha_publicacion") // Columna para el título del comunicado
    private LocalDate comFechaPublicacion;

    @Column(name = "com_contenido") // Columna para la descripción del comunicado
    private String comContenido;

    @Column(name = "com_documento") // Columna para almacenar la ruta o URL de la imagen asociada al comunicado
    private String comDocumento;

    @Column(name = "com_estado") // Columna para almacenar la ruta o URL de la imagen asociada al comunicado
    private String comEstado;

    // Constructor vacío requerido por JPA
    public Comunicado() {
    }

    // Metodos getters y setters
    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }

    public String getComTipo() {
        return comTipo;
    }

    public void setComTipo(String comTipo) {
        this.comTipo = comTipo;
    }

    public String getComTitulo() {
        return comTitulo;
    }

    public void setComTitulo(String comTitulo) {
        this.comTitulo = comTitulo;
    }

    public LocalDate getComFechaPublicacion() {
        return comFechaPublicacion;
    }

    public void setComFechaPublicacion(LocalDate comFechaPublicacion) {
        this.comFechaPublicacion = comFechaPublicacion;
    }

    public String getComContenido() {
        return comContenido;
    }

    public void setComContenido(String comContenido) {
        this.comContenido = comContenido;
    }

    public String getComDocumento() {
        return comDocumento;
    }

    public void setComDocumento(String comDocumento) {
        this.comDocumento = comDocumento;
    }

    public String getComEstado() {
        return comEstado;
    }

    public void setComEstado(String comEstado) {
        this.comEstado = comEstado;
    }
    
    

}
