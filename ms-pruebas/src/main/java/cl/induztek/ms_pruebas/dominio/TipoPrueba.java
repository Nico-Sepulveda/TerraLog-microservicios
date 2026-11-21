package cl.induztek.ms_pruebas.dominio;

import java.util.List;

public enum TipoPrueba {
    AISLACION_10_MIN("Resistencia de aislación (1 y 10 min)", List.of(
            new CampoMedicion("lectura1", "Lectura a 1 min", "MΩ", 0.1, 1000000.0, false),
            new CampoMedicion("lectura10", "Lectura a 10 min", "MΩ", 0.1, 1000000.0, false),
            new CampoMedicion("tension", "Tensión de prueba", "kV", 0.1, 15.0, false))),
    AISLACION_1_MIN("Resistencia de aislación (1 min)", List.of(
            new CampoMedicion("lectura1", "Lectura a 1 min", "MΩ", 0.1, 1000000.0, false),
            new CampoMedicion("tension", "Tensión de prueba", "kV", 0.1, 15.0, false))),
    TTR("Relación de transformación (TTR)", List.of(
            new CampoMedicion("relacionTeorica", "Relación teórica", "", 0.1, 1000.0, false),
            new CampoMedicion("relacionMedida", "Relación medida", "", 0.1, 1000.0, false),
            new CampoMedicion("posicion", "Posición del cambiador", "", 1.0, 33.0, false))),
    DEVANADOS("Resistencia de devanados", List.of(
            new CampoMedicion("faseA", "Fase A", "mΩ", 0.1, 100000.0, false),
            new CampoMedicion("faseB", "Fase B", "mΩ", 0.1, 100000.0, false),
            new CampoMedicion("faseC", "Fase C", "mΩ", 0.1, 100000.0, false))),
    CONTACTOS("Resistencia de contactos", List.of(
            new CampoMedicion("polo1", "Polo 1", "µΩ", 1.0, 10000.0, false),
            new CampoMedicion("polo2", "Polo 2", "µΩ", 1.0, 10000.0, false),
            new CampoMedicion("polo3", "Polo 3", "µΩ", 1.0, 10000.0, false))),
    OPERACION_RELE("Prueba de operación del relé", List.of(
            new CampoMedicion("ajuste", "Ajuste de corriente de operación", "A", 0.1, 100.0, false),
            new CampoMedicion("corrienteMedida", "Corriente medida de operación", "A", 0.1, 100.0, true),
            new CampoMedicion("tiempoEsperado", "Tiempo esperado", "ms", 1.0, 10000.0, false),
            new CampoMedicion("tiempoMedido", "Tiempo medido", "ms", 1.0, 10000.0, true)));

    private final String nombre;
    private final List<CampoMedicion> campos;

    TipoPrueba(String nombre, List<CampoMedicion> campos) {
        this.nombre = nombre;
        this.campos = campos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<CampoMedicion> getCampos() {
        return campos;
    }

    public static TipoPrueba desde(String texto) {
        for (TipoPrueba t : values()) {
            if (t.name().equals(texto)) return t;
        }
        return null;
    }
}