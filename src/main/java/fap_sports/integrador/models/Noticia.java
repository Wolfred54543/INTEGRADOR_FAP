package fap_sports.integrador.models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "noticias") // Mapea esta clase a la tabla 'comunicados' en la base de datos
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de IDs autoincrementales
    @Column(name = "not_id") // Columna que representa el ID del comunicado
    private Long notId;

    @Column(name = "not_titulo") // Columna para el título del comunicado
    private String notTitulo; 

    @Column(name = "not_subtitulo") // Columna para el subtitulo del comunicado
    private String notSubtitulo;

    @Column(name = "not_descripcion") // Columna para la descripción del comunicado
    private String notDescripcion;


    @Column(name = "not_imagen") // Columna para almacenar la ruta o URL de la imagen asociada al comunicado
    private String notImagen;

    @Column(name = "not_autor") // Columna para almacenar el nombre del autor
    private String notAutor;

    @Column(name = "not_tipo")
    private String notTipo; // Puede ser "principal" o "secundaria"

    @Temporal(TemporalType.DATE)
    @Column(name = "not_fecha_creacion")
    private Date notFechaCreacion;

    @PrePersist
    protected void onCreate() {
        if (this.notFechaCreacion == null) {
            this.notFechaCreacion = new Date(); // Asigna la fecha actual
        }
    }

    // Métodos get y set
    public String getNotTipo() {
        return notTipo;
    }

    public void setNotTipo(String notTipo) {
        this.notTipo = notTipo;
    }

    public Long getNotId() {
        return notId;
    }

    public void setNotId(Long notId) {
        this.notId = notId;
    }

    public String getNotTitulo() {
        return notTitulo;
    }

    public void setNotTitulo(String notTitulo) {
        this.notTitulo = notTitulo;
    }

    public String getNotSubtitulo() {
        return notSubtitulo;
    }

    public void setNotSubtitulo(String notSubtitulo) {
        this.notSubtitulo = notSubtitulo;
    }

    public String getNotImagen() {
        return notImagen;
    }

    public void setNotImagen(String notImagen) {
        this.notImagen = notImagen;
    }

    public String getNotAutor() {
        return notAutor;
    }

    public void setNotAutor(String notAutor) {
        this.notAutor = notAutor;
    }

    public String getNotDescripcion() {
        return notDescripcion;
    }

    public void setNotDescripcion(String notDescripcion) {
        this.notDescripcion = notDescripcion;
    }

    public Date getNotFechaCreacion() {
        return notFechaCreacion;
    }

    public void setNotFechaCreacion(Date notFechaCreacion) {
        this.notFechaCreacion = notFechaCreacion;
    }
}
