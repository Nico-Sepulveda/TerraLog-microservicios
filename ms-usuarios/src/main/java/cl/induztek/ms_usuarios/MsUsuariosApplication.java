package cl.induztek.ms_usuarios;

import cl.induztek.ms_usuarios.modelo.Usuario;
import cl.induztek.ms_usuarios.repositorio.UsuarioRepositorio;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUsuariosApplication.class, args);
    }

    @Bean
    CommandLineRunner datosIniciales(UsuarioRepositorio repo) {
        return args -> {
            if (repo.count() == 0) {
                String c = "terralog123";
                repo.saveAll(List.of(
                    new Usuario("Felipe Araneda Quiroz", "16.739.208-3", "faraneda@induztek.cl", c, "TECNICO", null),
                    new Usuario("Camila Becerra Vidal", "18.452.961-0", "cbecerra@induztek.cl", c, "TECNICO", null),
                    new Usuario("Joaquín Salinas Bravo", "17.093.847-K", "jsalinas@induztek.cl", c, "TECNICO", null),
                    new Usuario("Valentina Ortega Campos", "19.318.624-6", "vortega@induztek.cl", c, "TECNICO", null),
                    new Usuario("Isabel Mardones Cifuentes", "14.862.570-0", "imardones@induztek.cl", c, "INGENIERO", null),
                    new Usuario("Rodrigo Pizarro Mena", "15.927.413-6", "rpizarro@induztek.cl", c, "INGENIERO", null),
                    new Usuario("Héctor Bustamante Toro", "13.486.092-8", "hbustamante@induztek.cl", c, "JEFE_TECNICO", null),
                    new Usuario("Paola Villalobos Cárdenas", "16.204.835-K", "pvillalobos@envpacifico.cl", c, "CLIENTE", 1L),
                    new Usuario("Gonzalo Sepúlveda Ibarra", "18.776.351-7", "gsepulveda@losaromos.cl", c, "CLIENTE", 4L),
                    new Usuario("Natalia Escobar Fierro", "12.950.684-9", "nescobar@cerroalto.cl", c, "CLIENTE", 3L)
                ));
            }
        };
    }
}