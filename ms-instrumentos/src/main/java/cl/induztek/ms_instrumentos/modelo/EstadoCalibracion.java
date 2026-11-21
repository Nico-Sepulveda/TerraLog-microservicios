package cl.induztek.ms_instrumentos.modelo;

import java.time.LocalDate;

public record EstadoCalibracion(Long id, String nombre, LocalDate vencimiento, boolean vigente, String mensaje) {
}