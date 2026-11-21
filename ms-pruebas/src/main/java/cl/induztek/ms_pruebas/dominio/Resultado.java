package cl.induztek.ms_pruebas.dominio;

import java.util.Comparator;
import java.util.List;

public enum Resultado {
    APROBADO(0),
    OBSERVADO(1),
    RECHAZADO(2);

    private final int gravedad;

    Resultado(int gravedad) {
        this.gravedad = gravedad;
    }

    public int getGravedad() {
        return gravedad;
    }

    public static Resultado desde(String texto) {
        for (Resultado r : values()) {
            if (r.name().equals(texto)) return r;
        }
        return null;
    }

    public static Resultado peor(List<Resultado> lista) {
        return lista.stream().max(Comparator.comparingInt(Resultado::getGravedad)).orElse(null);
    }
}