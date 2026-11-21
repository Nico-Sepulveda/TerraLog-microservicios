package cl.induztek.ms_protocolos.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "protocolos")
public class Protocolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numero;

    private Long equipoId;
    private Long tecnicoId;
    private LocalDateTime fechaHora;
    private Double temperatura;
    private Double humedad;
    private String estado;
    private String resultadoGlobal;
    private String motivo;
    private String comentarioIngeniero;
    private Long formalizadoPorId;
    private LocalDateTime fechaFormalizacion;

    public Protocolo() {}

    public Protocolo(String numero, Long equipoId, Long tecnicoId, LocalDateTime fechaHora, Double temperatura, Double humedad, String estado) {
        this.numero = numero;
        this.equipoId = equipoId;
        this.tecnicoId = tecnicoId;
        this.fechaHora = fechaHora;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public Long getEquipoId() { return equipoId; }
    public void setEquipoId(Long equipoId) { this.equipoId = equipoId; }
    public Long getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Long tecnicoId) { this.tecnicoId = tecnicoId; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }
    public Double getHumedad() { return humedad; }
    public void setHumedad(Double humedad) { this.humedad = humedad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getResultadoGlobal() { return resultadoGlobal; }
    public void setResultadoGlobal(String resultadoGlobal) { this.resultadoGlobal = resultadoGlobal; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getComentarioIngeniero() { return comentarioIngeniero; }
    public void setComentarioIngeniero(String comentarioIngeniero) { this.comentarioIngeniero = comentarioIngeniero; }
    public Long getFormalizadoPorId() { return formalizadoPorId; }
    public void setFormalizadoPorId(Long formalizadoPorId) { this.formalizadoPorId = formalizadoPorId; }
    public LocalDateTime getFechaFormalizacion() { return fechaFormalizacion; }
    public void setFechaFormalizacion(LocalDateTime fechaFormalizacion) { this.fechaFormalizacion = fechaFormalizacion; }
}