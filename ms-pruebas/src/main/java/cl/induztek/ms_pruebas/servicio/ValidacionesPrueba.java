package cl.induztek.ms_pruebas.servicio;

import cl.induztek.ms_pruebas.dominio.CampoMedicion;
import cl.induztek.ms_pruebas.dominio.TipoPrueba;
import java.util.Map;

public final class ValidacionesPrueba {

    private ValidacionesPrueba() {}

    private static String numero(double d) {
        return d == Math.rint(d) ? String.valueOf((long) d) : String.valueOf(d);
    }

    public static String validarNumero(Double valor, double min, double max, String unidad) {
        if (valor == null) return "Campo obligatorio";
        if (valor.isNaN() || valor.isInfinite()) return "Ingresa un número válido";
        if (valor < min || valor > max) return ("Debe estar entre " + numero(min) + " y " + numero(max) + " " + unidad).trim();
        return null;
    }

    public static boolean opera(Map<String, Double> valores) {
        Double v = valores == null ? null : valores.get("opera");
        return v == null || v != 0.0;
    }

    public static String validarValores(TipoPrueba tipo, Map<String, Double> valores) {
        boolean opera = opera(valores);
        for (CampoMedicion campo : tipo.getCampos()) {
            if (campo.soloSiOpera() && !opera) continue;
            Double valor = valores == null ? null : valores.get(campo.clave());
            String error = validarNumero(valor, campo.min(), campo.max(), campo.unidad());
            if (error != null) return campo.nombre() + ": " + error;
        }
        return null;
    }

    public static String validarMotivo(String texto) {
        if (texto == null || texto.trim().isEmpty()) return "Escribe el motivo";
        if (texto.trim().length() < 5) return "Explícalo con al menos 5 caracteres";
        if (texto.trim().length() > 200) return "Máximo 200 caracteres";
        return null;
    }

    public static String validarComentario(String texto) {
        if (texto != null && texto.trim().length() > 200) return "Máximo 200 caracteres";
        return null;
    }

    public static boolean estadoAdmitePruebas(String estado) {
        return "BORRADOR".equals(estado) || "OBSERVADO".equals(estado);
    }
}