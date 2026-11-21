package cl.induztek.ms_clientes;

import cl.induztek.ms_clientes.modelo.Cliente;
import cl.induztek.ms_clientes.modelo.Instalacion;
import cl.induztek.ms_clientes.repositorio.ClienteRepositorio;
import cl.induztek.ms_clientes.repositorio.InstalacionRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsClientesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsClientesApplication.class, args);
    }

    @Bean
    CommandLineRunner datosIniciales(ClienteRepositorio clientes, InstalacionRepositorio instalaciones) {
        return args -> {
            if (clientes.count() == 0) {
                String[][] datos = {
                    {"Envasadora del Pacífico S.A.", "76.482.915-8", "Planta Lampa", "Lampa", "Inducción obligatoria de 30 min"},
                    {"Sanitaria Valle Central S.A.", "96.731.540-0", "Planta Elevadora Sur", "San Bernardo", "Sala eléctrica subterránea, sin señal"},
                    {"Minera Cerro Alto SpA", "77.105.362-9", "Faena Til Til", "Til Til", "Sin señal en toda la faena"},
                    {"Clínica Los Aromos Ltda.", "76.903.218-5", "Edificio Clínico", "La Florida", "Cortes solo domingos 06:00–10:00"},
                    {"Cementos Lomas Blancas S.A.", "99.524.170-6", "Planta Molienda", "Puente Alto", "Uso obligatorio de respirador"},
                    {"Data Center Andes Norte SpA", "78.214.635-1", "Site Huechuraba", "Huechuraba", "Acompañamiento permanente del cliente"}
                };
                for (String[] d : datos) {
                    Cliente c = clientes.save(new Cliente(d[0], d[1]));
                    instalaciones.save(new Instalacion(c.getId(), d[2], d[3], d[4]));
                }
            }
        };
    }
}