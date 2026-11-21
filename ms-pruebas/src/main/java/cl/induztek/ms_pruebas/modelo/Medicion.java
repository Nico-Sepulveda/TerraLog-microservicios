package cl.induztek.ms_pruebas.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "mediciones")
public class Medicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pruebaId;
    private String clave;
    private String nombre;
    private Double valor;
    private String unidad;
    private boolean calculado;

    public Medicion() {}

    public Medicion(Long pruebaId, String clave, String nombre, Double valor, String unidad, boolean calculado) {
        this.pruebaId = pruebaId;
        this.clave = clave;
        this.nombre = nombre;
        this.valor = valor;
        this.unidad = unidad;
        this.calculado = calculado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPruebaId() { return pruebaId; }
    public void setPruebaId(Long pruebaId) { this.pruebaId = pruebaId; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public boolean isCalculado() { return calculado; }
    public void setCalculado(boolean calculado) { this.calculado = calculado; }
}