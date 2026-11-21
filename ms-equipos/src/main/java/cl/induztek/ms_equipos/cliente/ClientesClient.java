package cl.induztek.ms_equipos.cliente;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-clientes", url = "${servicios.clientes.url}")
public interface ClientesClient {

    @GetMapping("/instalaciones/{id}")
    InstalacionDto instalacion(@PathVariable("id") Long id);

    @GetMapping("/instalaciones")
    List<InstalacionDto> instalacionesDeCliente(@RequestParam("clienteId") Long clienteId);
}