package cl.induztek.ms_pruebas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientProtocolos(@Value("${servicios.protocolos.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}