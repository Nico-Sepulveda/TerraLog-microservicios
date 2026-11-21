package cl.induztek.ms_protocolos;

import cl.induztek.ms_protocolos.modelo.Protocolo;
import cl.induztek.ms_protocolos.repositorio.ProtocoloRepositorio;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsProtocolosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProtocolosApplication.class, args);
    }

    @Bean
    CommandLineRunner datosIniciales(ProtocoloRepositorio repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
                    historial("PT-2024-0312", 1L, LocalDateTime.of(2024, 9, 14, 10, 30), 18.0, 55.0, "FORMALIZADO", "APROBADO"),
                    historial("PT-2025-0288", 2L, LocalDateTime.of(2025, 9, 13, 10, 30), 21.0, 48.0, "FORMALIZADO", "APROBADO"),
                    historial("PT-2026-0401", 1L, LocalDateTime.of(2026, 9, 26, 10, 30), 19.0, 60.0, "REGISTRADO_EN_TERRENO", "OBSERVADO")
                ));
            }
        };
    }

    private static Protocolo historial(String numero, Long tecnicoId, LocalDateTime fecha, double temperatura, double humedad, String estado, String resultado) {
        Protocolo protocolo = new Protocolo(numero, 1L, tecnicoId, fecha, temperatura, humedad, estado);
        protocolo.setResultadoGlobal(resultado);
        if ("FORMALIZADO".equals(estado)) {
            protocolo.setFormalizadoPorId(5L);
            protocolo.setFechaFormalizacion(fecha.plusDays(1));
        }
        return protocolo;
    }
}