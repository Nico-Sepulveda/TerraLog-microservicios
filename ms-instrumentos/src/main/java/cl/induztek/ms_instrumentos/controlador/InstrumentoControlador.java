package cl.induztek.ms_instrumentos.controlador;

import cl.induztek.ms_instrumentos.modelo.EstadoCalibracion;
import cl.induztek.ms_instrumentos.modelo.Instrumento;
import cl.induztek.ms_instrumentos.repositorio.InstrumentoRepositorio;
import cl.induztek.ms_instrumentos.servicio.InstrumentoServicio;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instrumentos")
public class InstrumentoControlador {

    private final InstrumentoRepositorio repo;
    private final InstrumentoServicio servicio;

    public InstrumentoControlador(InstrumentoRepositorio repo, InstrumentoServicio servicio) {
        this.repo = repo;
        this.servicio = servicio;
    }

    @GetMapping
    public List<Instrumento> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrumento> obtener(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/calibracion")
    public EstadoCalibracion calibracion(@PathVariable Long id) {
        return servicio.calibracion(id);
    }

    @PostMapping
    public Instrumento crear(@RequestBody Instrumento instrumento) {
        return servicio.crear(instrumento);
    }

    @PutMapping("/{id}")
    public Instrumento actualizar(@PathVariable Long id, @RequestBody Instrumento instrumento) {
        return servicio.actualizar(id, instrumento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}