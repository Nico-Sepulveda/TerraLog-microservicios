package cl.induztek.ms_pruebas.cliente;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ProtocolosCliente {

    private final WebClient webClient;

    public ProtocolosCliente(@Qualifier("webClientProtocolos") WebClient webClient) {
        this.webClient = webClient;
    }

    public ProtocoloDto obtener(Long id) {
        try {
            return webClient.get().uri("/protocolos/{id}", id).retrieve().bodyToMono(ProtocoloDto.class).block();
        } catch (WebClientResponseException.NotFound ex) {
            return null;
        } catch (WebClientException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "ms-protocolos no está disponible");
        }
    }

    public void actualizarResultado(Long id, String resultado) {
        try {
            webClient.patch().uri("/protocolos/{id}/resultado", id)
                    .bodyValue(new ResultadoGlobalDto(resultado))
                    .retrieve().toBodilessEntity().block();
        } catch (WebClientException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "ms-protocolos no está disponible");
        }
    }
}