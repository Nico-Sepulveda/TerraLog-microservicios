package cl.induztek.ms_pruebas;

import cl.induztek.ms_pruebas.dominio.CampoMedicion;
import cl.induztek.ms_pruebas.dominio.ResultadoPrueba;
import cl.induztek.ms_pruebas.dominio.Resultados;
import cl.induztek.ms_pruebas.dominio.TipoPrueba;
import cl.induztek.ms_pruebas.dominio.ValorCalculado;
import cl.induztek.ms_pruebas.modelo.Medicion;
import cl.induztek.ms_pruebas.modelo.Prueba;
import cl.induztek.ms_pruebas.repositorio.MedicionRepositorio;
import cl.induztek.ms_pruebas.repositorio.PruebaRepositorio;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class MsPruebasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPruebasApplication.class, args);
    }

    @Bean
    CommandLineRunner datosIniciales(PruebaRepositorio pruebas, MedicionRepositorio mediciones) {
        return args -> {
            if (pruebas.count() == 0) {
                sembrar(pruebas, mediciones, 1L, 12500.0, 31250.0);
                sembrar(pruebas, mediciones, 2L, 9800.0, 21560.0);
                sembrar(pruebas, mediciones, 3L, 6100.0, 10980.0);
            }
        };
    }

    private static void sembrar(PruebaRepositorio pruebas, MedicionRepositorio mediciones, long protocoloId, double lectura1, double lectura10) {
        Map<String, Double> valores = Map.of("lectura1", lectura1, "lectura10", lectura10, "tension", 5.0);
        ResultadoPrueba calculo = Resultados.calcular(TipoPrueba.AISLACION_10_MIN, valores, null);
        Prueba prueba = pruebas.save(new Prueba(protocoloId, TipoPrueba.AISLACION_10_MIN.name(), 1L, true, null, calculo.resultado().name(), "Devanado AT-T a 5 kV"));
        for (CampoMedicion campo : TipoPrueba.AISLACION_10_MIN.getCampos()) {
            mediciones.save(new Medicion(prueba.getId(), campo.clave(), campo.nombre(), valores.get(campo.clave()), campo.unidad(), false));
        }
        for (ValorCalculado calculado : calculo.calculados()) {
            mediciones.save(new Medicion(prueba.getId(), calculado.clave(), calculado.nombre(), calculado.valor(), calculado.unidad(), true));
        }
    }
}