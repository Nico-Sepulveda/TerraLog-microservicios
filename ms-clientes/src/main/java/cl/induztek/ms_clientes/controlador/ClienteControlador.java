package cl.induztek.ms_clientes.controlador;

import cl.induztek.ms_clientes.modelo.Cliente;
import cl.induztek.ms_clientes.repositorio.ClienteRepositorio;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteControlador {

    private final ClienteRepositorio repo;

    public ClienteControlador(ClienteRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Cliente> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        cliente.setId(null);
        return repo.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        cliente.setId(id);
        return ResponseEntity.ok(repo.save(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}