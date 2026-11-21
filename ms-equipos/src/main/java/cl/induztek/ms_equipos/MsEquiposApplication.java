package cl.induztek.ms_equipos;

import cl.induztek.ms_equipos.modelo.Equipo;
import cl.induztek.ms_equipos.repositorio.EquipoRepositorio;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class MsEquiposApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsEquiposApplication.class, args);
    }

    @Bean
    CommandLineRunner datosIniciales(EquipoRepositorio repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
                    new Equipo(1L, "TR-01", "TRANSFORMADOR", "Rhona", "RH-18733", 2012, "1.500 kVA, 12 / 0,4 kV", null),
                    new Equipo(1L, "TR-02", "TRANSFORMADOR", "Rhona", "RH-21904", 2017, "1.000 kVA, 12 / 0,4 kV", null),
                    new Equipo(4L, "CEL-MT-03", "CELDA_MT", "Ormazabal", "OR-77812", 2018, "12 kV, 630 A", 60.0),
                    new Equipo(3L, "INT-52-1", "INTERRUPTOR", "Schneider", "SE-449021", 2015, "23 kV, 1.250 A", 45.0)
                ));
            }
        };
    }
}