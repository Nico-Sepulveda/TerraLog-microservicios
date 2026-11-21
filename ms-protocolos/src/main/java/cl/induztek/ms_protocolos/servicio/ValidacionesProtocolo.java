package cl.induztek.ms_protocolos.servicio;

public final class ValidacionesProtocolo {

    private ValidacionesProtocolo() {}

    private static String validarRango(Double valor, double min, double max, String unidad) {
        if (valor == null) return "Campo obligatorio";
        if (valor.isNaN() || valor.isInfinite()) return "Ingresa un número válido";
        if (valor < min || valor > max) return "Debe estar entre " + (int) min + " y " + (int) max + " " + unidad;
        return null;
    }

    public static String validarTemperatura(Double temperatura) {
        return validarRango(temperatura, -20, 60, "°C");
    }

    public static String validarHumedad(Double humedad) {
        return validarRango(humedad, 0, 100, "%");
    }

    public static String validarMotivo(String texto) {
        if (texto == null || texto.trim().isEmpty()) return "Escribe el motivo";
        if (texto.trim().length() < 5) return "Explícalo con al menos 5 caracteres";
        if (texto.trim().length() > 200) return "Máximo 200 caracteres";
        return null;
    }

    public static String formatoNumero(int anio, int correlativo) {
        return String.format("PT-%d-%04d", anio, correlativo);
    }
}