package cl.induztek.ms_instrumentos.servicio;

import cl.induztek.ms_instrumentos.modelo.Instrumento;
import java.time.LocalDate;

public final class ValidacionesInstrumento {

    private ValidacionesInstrumento() {}

    public static boolean estaVencido(LocalDate vencimiento, LocalDate hoy) {
        return hoy.isAfter(vencimiento);
    }

    public static String validarTexto(String texto, String nombre) {
        if (texto == null || texto.trim().isEmpty()) return "Ingresa " + nombre;
        if (texto.trim().length() < 2) return "Debe tener al menos 2 caracteres";
        if (texto.trim().length() > 200) return "Máximo 200 caracteres";
        return null;
    }

    public static String validarCalibracion(LocalDate vencimiento, LocalDate hoy) {
        if (vencimiento == null) return "Selecciona el instrumento utilizado";
        if (estaVencido(vencimiento, hoy)) return "Calibración vencida el " + vencimiento;
        return null;
    }

    public static String validar(Instrumento instrumento) {
        String error = validarTexto(instrumento.getNombre(), "el nombre");
        if (error != null) return error;
        error = validarTexto(instrumento.getMarcaModelo(), "la marca y modelo");
        if (error != null) return error;
        error = validarTexto(instrumento.getNroSerie(), "el número de serie");
        if (error != null) return error;
        if (instrumento.getVencimientoCalibracion() == null) return "Ingresa el vencimiento de la calibración";
        return null;
    }
}