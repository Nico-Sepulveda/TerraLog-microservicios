package cl.induztek.ms_pruebas.modelo;

import java.util.List;

public record PruebaDetalle(Prueba prueba, List<Medicion> mediciones) {
}