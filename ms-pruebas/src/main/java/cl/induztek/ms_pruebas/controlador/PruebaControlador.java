package cl.induztek.ms_pruebas.controlador;

import cl.induztek.ms_pruebas.modelo.Prueba;
import cl.induztek.ms_pruebas.modelo.PruebaDetalle;
import cl.induztek.ms_pruebas.modelo.SolicitudPrueba;
import cl.induztek.ms_pruebas.servicio.PruebaServicio;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pruebas")
public class PruebaControlador {

    private final PruebaServicio servicio;

    public PruebaControlador(PruebaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<Prueba> listar(@RequestParam(required = false) Long protocoloId) {
        return servicio.listar(protocoloId);
    }

    @GetMapping("/cantidad-realizadas")
    public long cantidadRealizadas(@RequestParam Long protocoloId) {
        return servicio.cantidadRealizadas(protocoloId);
    }

    @GetMapping("/{id}")
    public PruebaDetalle detalle(@PathVariable Long id) {
        return servicio.detalle(id);
    }

    @PostMapping
    public PruebaDetalle registrar(@RequestBody SolicitudPrueba solicitud) {
        return servicio.registrar(solicitud);
    }

    @PatchMapping("/{id}/comentario")
    public Prueba actualizarComentario(@PathVariable Long id, @RequestBody Map<String, String> datos) {
        return servicio.actualizarComentario(id, datos.get("comentario"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}