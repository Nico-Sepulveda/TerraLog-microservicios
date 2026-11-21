package cl.induztek.ms_clientes.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "instalaciones")
public class Instalacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;
    private String nombre;
    private String comuna;
    private String observacionesAcceso;

    public Instalacion() {}

    public Instalacion(Long clienteId, String nombre, String comuna, String observacionesAcceso) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.comuna = comuna;
        this.observacionesAcceso = observacionesAcceso;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }
    public String getObservacionesAcceso() { return observacionesAcceso; }
    public void setObservacionesAcceso(String observacionesAcceso) { this.observacionesAcceso = observacionesAcceso; }
}