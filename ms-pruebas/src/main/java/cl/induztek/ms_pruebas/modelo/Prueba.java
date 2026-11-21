package cl.induztek.ms_pruebas.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "pruebas")
public class Prueba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long protocoloId;
    private String tipoPrueba;
    private Long instrumentoId;
    private boolean realizada;
    private String motivoNoRealizada;
    private String resultado;
    private String comentario;

    public Prueba() {}

    public Prueba(Long protocoloId, String tipoPrueba, Long instrumentoId, boolean realizada, String motivoNoRealizada, String resultado, String comentario) {
        this.protocoloId = protocoloId;
        this.tipoPrueba = tipoPrueba;
        this.instrumentoId = instrumentoId;
        this.realizada = realizada;
        this.motivoNoRealizada = motivoNoRealizada;
        this.resultado = resultado;
        this.comentario = comentario;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProtocoloId() { return protocoloId; }
    public void setProtocoloId(Long protocoloId) { this.protocoloId = protocoloId; }
    public String getTipoPrueba() { return tipoPrueba; }
    public void setTipoPrueba(String tipoPrueba) { this.tipoPrueba = tipoPrueba; }
    public Long getInstrumentoId() { return instrumentoId; }
    public void setInstrumentoId(Long instrumentoId) { this.instrumentoId = instrumentoId; }
    public boolean isRealizada() { return realizada; }
    public void setRealizada(boolean realizada) { this.realizada = realizada; }
    public String getMotivoNoRealizada() { return motivoNoRealizada; }
    public void setMotivoNoRealizada(String motivoNoRealizada) { this.motivoNoRealizada = motivoNoRealizada; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}