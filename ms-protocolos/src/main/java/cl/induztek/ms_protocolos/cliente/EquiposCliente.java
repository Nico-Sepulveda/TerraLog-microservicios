package cl.induztek.ms_protocolos.cliente;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Component
public class EquiposCliente {

    private final WebClient webClient;

    public EquiposCliente(@Qualifier("webClientEquipos") WebClient webClient) {
        this.webClient = webClient;
    }

    public boolean existe(Long equipoId) {
        try {
            webClient.get().uri("/equipos/{id}", equipoId).retrieve().toBodilessEntity().block();
            return true;
        } catch (WebClientResponseException.NotFound ex) {
            return false;
        } catch (WebClientException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "ms-equipos no está disponible");
        }
    }
}