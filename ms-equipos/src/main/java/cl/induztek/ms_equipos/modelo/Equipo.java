package cl.induztek.ms_equipos.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "equipos", uniqueConstraints = @UniqueConstraint(columnNames = {"instalacionId", "tag"}))
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long instalacionId;
    private String tag;
    private String tipo;
    private String marca;
    private String nroSerie;
    private int anio;
    private String datosPlaca;
    private Double maxContactos;
    private String rutaFotoPlaca;

    public Equipo() {}

    public Equipo(Long instalacionId, String tag, String tipo, String marca, String nroSerie, int anio, String datosPlaca, Double maxContactos) {
        this.instalacionId = instalacionId;
        this.tag = tag;
        this.tipo = tipo;
        this.marca = marca;
        this.nroSerie = nroSerie;
        this.anio = anio;
        this.datosPlaca = datosPlaca;
        this.maxContactos = maxContactos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getInstalacionId() { return instalacionId; }
    public void setInstalacionId(Long instalacionId) { this.instalacionId = instalacionId; }
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getNroSerie() { return nroSerie; }
    public void setNroSerie(String nroSerie) { this.nroSerie = nroSerie; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public String getDatosPlaca() { return datosPlaca; }
    public void setDatosPlaca(String datosPlaca) { this.datosPlaca = datosPlaca; }
    public Double getMaxContactos() { return maxContactos; }
    public void setMaxContactos(Double maxContactos) { this.maxContactos = maxContactos; }
    public String getRutaFotoPlaca() { return rutaFotoPlaca; }
    public void setRutaFotoPlaca(String rutaFotoPlaca) { this.rutaFotoPlaca = rutaFotoPlaca; }
}