package cl.induztek.ms_protocolos.cliente;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PruebasCliente {

    private final WebClient webClient;

    public PruebasCliente(@Qualifier("webClientPruebas") WebClient webClient) {
        this.webClient = webClient;
    }

    public long cantidadRealizadas(Long protocoloId) {
        try {
            Long cantidad = webClient.get()
                    .uri("/pruebas/cantidad-realizadas?protocoloId={id}", protocoloId)
                    .retrieve()
                    .bodyToMono(Long.class)
                    .block();
            return cantidad == null ? 0 : cantidad;
        } catch (WebClientException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "ms-pruebas no está disponible");
        }
    }
}