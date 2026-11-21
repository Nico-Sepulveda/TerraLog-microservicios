package cl.induztek.ms_equipos.servicio;

import cl.induztek.ms_equipos.modelo.Equipo;
import java.util.List;
import java.util.regex.Pattern;

public final class ValidacionesEquipo {

    private static final Pattern TAG = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9/\\-]*$");
    private static final List<String> TIPOS = List.of("TRANSFORMADOR", "INTERRUPTOR", "CELDA_MT", "CABLE_PODER", "RELE_PROTECCION");

    private ValidacionesEquipo() {}

    public static String validarTag(String tag, List<String> existentes) {
        if (tag == null || tag.trim().isEmpty()) return "Ingresa el TAG del equipo";
        String limpio = tag.trim();
        if (limpio.length() < 2 || limpio.length() > 15) return "Debe tener entre 2 y 15 caracteres";
        if (!TAG.matcher(limpio).matches()) return "Solo letras, números, guion y barra (ej. TR-01)";
        if (existentes.stream().anyMatch(x -> x.equalsIgnoreCase(limpio))) return "Ya existe un equipo con ese TAG en la instalación";
        return null;
    }

    public static String validarTipo(String tipo) {
        return tipo != null && TIPOS.contains(tipo) ? null : "Selecciona el tipo de equipo";
    }

    public static String validarAnio(int anio, int anioActual) {
        return anio < 1950 || anio > anioActual ? "Debe estar entre 1950 y " + anioActual : null;
    }

    public static boolean usaContactos(String tipo) {
        return "INTERRUPTOR".equals(tipo) || "CELDA_MT".equals(tipo);
    }

    public static String validarMaxContactos(Double valor) {
        if (valor == null) return "Campo obligatorio";
        return valor < 1 || valor > 10000 ? "Debe estar entre 1 y 10000 µΩ" : null;
    }

    public static String validar(Equipo equipo, List<String> tagsExistentes, int anioActual) {
        String error = validarTag(equipo.getTag(), tagsExistentes);
        if (error != null) return error;
        error = validarTipo(equipo.getTipo());
        if (error != null) return error;
        error = validarAnio(equipo.getAnio(), anioActual);
        if (error != null) return error;
        if (usaContactos(equipo.getTipo())) return validarMaxContactos(equipo.getMaxContactos());
        return null;
    }
}