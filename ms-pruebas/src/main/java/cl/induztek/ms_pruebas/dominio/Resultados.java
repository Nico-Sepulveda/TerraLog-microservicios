package cl.induztek.ms_pruebas.dominio;

import java.util.List;
import java.util.Map;

public final class Resultados {

    private Resultados() {}

    public static ResultadoPrueba calcular(TipoPrueba tipo, Map<String, Double> v, Double maxContactos) {
        return switch (tipo) {
            case AISLACION_10_MIN -> {
                double lectura1 = v.get("lectura1");
                double ip = v.get("lectura10") / lectura1;
                Resultado resultado;
                if (lectura1 < 100.0 || ip < 1.0) resultado = Resultado.RECHAZADO;
                else if (ip < 2.0) resultado = Resultado.OBSERVADO;
                else resultado = Resultado.APROBADO;
                yield new ResultadoPrueba(resultado, List.of(new ValorCalculado("ip", "Índice de polarización", ip, "")));
            }
            case AISLACION_1_MIN -> {
                Resultado resultado = v.get("lectura1") < 100.0 ? Resultado.RECHAZADO : Resultado.APROBADO;
                yield new ResultadoPrueba(resultado, List.of());
            }
            case TTR -> {
                double teorica = v.get("relacionTeorica");
                double error = Math.abs(v.get("relacionMedida") - teorica) / teorica * 100;
                Resultado resultado;
                if (error <= 0.5) resultado = Resultado.APROBADO;
                else if (error <= 1.0) resultado = Resultado.OBSERVADO;
                else resultado = Resultado.RECHAZADO;
                yield new ResultadoPrueba(resultado, List.of(new ValorCalculado("error", "Error de relación", error, "%")));
            }
            case DEVANADOS -> new ResultadoPrueba(null, List.of());
            case CONTACTOS -> {
                if (maxContactos == null || maxContactos <= 0.0) {
                    yield new ResultadoPrueba(null, List.of());
                }
                double mayor = Math.max(v.get("polo1"), Math.max(v.get("polo2"), v.get("polo3")));
                double porcentaje = mayor / maxContactos * 100;
                Resultado resultado;
                if (porcentaje <= 100.0) resultado = Resultado.APROBADO;
                else if (porcentaje <= 120.0) resultado = Resultado.OBSERVADO;
                else resultado = Resultado.RECHAZADO;
                yield new ResultadoPrueba(resultado, List.of(new ValorCalculado("porcentajeMaximo", "Mayor polo vs. máximo de placa", porcentaje, "%")));
            }
            case OPERACION_RELE -> {
                Double operaValor = v.get("opera");
                boolean opera = operaValor == null || operaValor != 0.0;
                if (!opera) {
                    yield new ResultadoPrueba(Resultado.RECHAZADO, List.of());
                }
                double esperado = v.get("tiempoEsperado");
                double diferencia = Math.abs(v.get("tiempoMedido") - esperado) / esperado * 100;
                Resultado resultado;
                if (diferencia <= 5.0) resultado = Resultado.APROBADO;
                else if (diferencia <= 10.0) resultado = Resultado.OBSERVADO;
                else resultado = Resultado.RECHAZADO;
                yield new ResultadoPrueba(resultado, List.of(new ValorCalculado("diferenciaTiempo", "Diferencia de tiempo", diferencia, "%")));
            }
        };
    }
}