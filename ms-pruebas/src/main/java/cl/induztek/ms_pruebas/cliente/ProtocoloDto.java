package cl.induztek.ms_pruebas.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProtocoloDto(Long id, String numero, String estado, Long equipoId) {
}