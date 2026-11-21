package cl.induztek.ms_clientes.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razonSocial;
    private String rut;

    public Cliente() {}

    public Cliente(String razonSocial, String rut) {
        this.razonSocial = razonSocial;
        this.rut = rut;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
}