package cl.induztek.ms_protocolos.controlador;

import cl.induztek.ms_protocolos.modelo.EdicionProtocolo;
import cl.induztek.ms_protocolos.modelo.NuevoProtocolo;
import cl.induztek.ms_protocolos.modelo.Protocolo;
import cl.induztek.ms_protocolos.modelo.ResultadoGlobal;
import cl.induztek.ms_protocolos.modelo.SolicitudAccion;
import cl.induztek.ms_protocolos.servicio.ProtocoloServicio;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protocolos")
public class ProtocoloControlador {

    private final ProtocoloServicio servicio;

    public ProtocoloControlador(ProtocoloServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<Protocolo> listar(@RequestParam(required = false) Long equipoId, @RequestParam(required = false) String rol) {
        return servicio.listar(equipoId, rol);
    }

    @GetMapping("/{id}")
    public Protocolo obtener(@PathVariable Long id) {
        return servicio.buscar(id);
    }

    @GetMapping("/{id}/acciones")
    public List<String> acciones(@PathVariable Long id, @RequestParam String rol) {
        return servicio.acciones(id, rol);
    }

    @PostMapping
    public Protocolo crear(@RequestBody NuevoProtocolo nuevo) {
        return servicio.crear(nuevo);
    }

    @PutMapping("/{id}")
    public Protocolo editar(@PathVariable Long id, @RequestBody EdicionProtocolo edicion) {
        return servicio.editar(id, edicion);
    }

    @PatchMapping("/{id}/estado")
    public Protocolo cambiarEstado(@PathVariable Long id, @RequestBody SolicitudAccion solicitud) {
        return servicio.cambiarEstado(id, solicitud);
    }

    @PatchMapping("/{id}/resultado")
    public Protocolo actualizarResultado(@PathVariable Long id, @RequestBody ResultadoGlobal resultado) {
        return servicio.actualizarResultado(id, resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}