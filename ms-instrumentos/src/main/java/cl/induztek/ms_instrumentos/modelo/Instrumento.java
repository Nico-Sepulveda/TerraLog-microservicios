package cl.induztek.ms_instrumentos.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "instrumentos")
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String marcaModelo;
    private String nroSerie;
    private LocalDate vencimientoCalibracion;

    public Instrumento() {}

    public Instrumento(String nombre, String marcaModelo, String nroSerie, LocalDate vencimientoCalibracion) {
        this.nombre = nombre;
        this.marcaModelo = marcaModelo;
        this.nroSerie = nroSerie;
        this.vencimientoCalibracion = vencimientoCalibracion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMarcaModelo() { return marcaModelo; }
    public void setMarcaModelo(String marcaModelo) { this.marcaModelo = marcaModelo; }
    public String getNroSerie() { return nroSerie; }
    public void setNroSerie(String nroSerie) { this.nroSerie = nroSerie; }
    public LocalDate getVencimientoCalibracion() { return vencimientoCalibracion; }
    public void setVencimientoCalibracion(LocalDate vencimientoCalibracion) { this.vencimientoCalibracion = vencimientoCalibracion; }
}