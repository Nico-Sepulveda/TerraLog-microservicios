package cl.induztek.ms_instrumentos;

import cl.induztek.ms_instrumentos.modelo.Instrumento;
import cl.induztek.ms_instrumentos.repositorio.InstrumentoRepositorio;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsInstrumentosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsInstrumentosApplication.class, args);
    }

    @Bean
    CommandLineRunner datosIniciales(InstrumentoRepositorio repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
                    new Instrumento("Megóhmetro 5 kV", "Megger MIT525", "MIT-0045821", LocalDate.of(2027, 3, 15)),
                    new Instrumento("Megóhmetro 10 kV", "Megger MIT1025", "MIT-0071233", LocalDate.of(2026, 10, 10)),
                    new Instrumento("Medidor de relación (TTR)", "Megger TTR300", "TTR-30044", LocalDate.of(2027, 6, 2)),
                    new Instrumento("Micro-ohmímetro", "Megger DLRO10X", "DLR-10882", LocalDate.of(2026, 8, 20)),
                    new Instrumento("Equipo de inyección secundaria", "Omicron CMC 356", "CMC-HB411", LocalDate.of(2027, 1, 5))
                ));
            }
        };
    }
}