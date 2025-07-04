package fap_sports.integrador.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos")
public class Tipo {
    // Identificador único del tipo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_id") 
    private Long tipoId;

    // Nombre del tipo
    @Column(name = "tipo_nombre")
    private String tipoNombre;

    // Métodos get y set
    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public String getTipoNombre() {
        return tipoNombre; 
    }

    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre; 
    }
}
