package cl.induztek.ms_clientes.controlador;

import cl.induztek.ms_clientes.modelo.Instalacion;
import cl.induztek.ms_clientes.repositorio.InstalacionRepositorio;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instalaciones")
public class InstalacionControlador {

    private final InstalacionRepositorio repo;

    public InstalacionControlador(InstalacionRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Instalacion> listar(@RequestParam(required = false) Long clienteId) {
        return clienteId == null ? repo.findAll() : repo.findByClienteId(clienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instalacion> obtener(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Instalacion crear(@RequestBody Instalacion instalacion) {
        instalacion.setId(null);
        return repo.save(instalacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instalacion> actualizar(@PathVariable Long id, @RequestBody Instalacion instalacion) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        instalacion.setId(id);
        return ResponseEntity.ok(repo.save(instalacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}