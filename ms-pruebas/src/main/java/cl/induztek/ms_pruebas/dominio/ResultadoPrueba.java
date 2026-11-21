package cl.induztek.ms_pruebas.dominio;

import java.util.List;

public record ResultadoPrueba(Resultado resultado, List<ValorCalculado> calculados) {
}