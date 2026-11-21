package cl.induztek.ms_equipos.cliente;

public record InstalacionDto(Long id, Long clienteId, String nombre, String comuna, String observacionesAcceso) {
}