package cl.induztek.ms_pruebas.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EstadoCalibracionDto(Long id, String nombre, String vencimiento, boolean vigente, String mensaje) {
}