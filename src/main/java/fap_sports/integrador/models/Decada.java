package fap_sports.integrador.models;

import jakarta.persistence.*;

@Entity
@Table(name = "decadas")
public class Decada {
    // Identificador único de la década
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dec_id")
    private Long decId;

    // Año de inicio de la década
    @Column(name = "anio_inicio")
    private int anioInicio;

    // Mes asociado a la década (opcional o para algún uso específico)
    @Column(name = "dec_mes")
    private String decMes;

    // Constructor vacío necesario para JPA
    public Decada() {}

    // Constructor para inicializar con el año de inicio
    public Decada(int anioInicio) {
        this.anioInicio = anioInicio;
    }

    // Métodos get y set
    public Long getDecId() {
        return decId;
    }

    public void setDecId(Long decId) {
        this.decId = decId;
    }

    public int getAnioInicio() {
        return anioInicio;
    }

    public void setAnioInicio(int anioInicio) {
        this.anioInicio = anioInicio;
    }

    public String getDecMes() {
        return decMes;
    }

    public void setDecMes(String decMes) {
        this.decMes = decMes;
    }
}
