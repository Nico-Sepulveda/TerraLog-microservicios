package cl.induztek.ms_protocolos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientEquipos(@Value("${servicios.equipos.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean
    public WebClient webClientPruebas(@Value("${servicios.pruebas.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}