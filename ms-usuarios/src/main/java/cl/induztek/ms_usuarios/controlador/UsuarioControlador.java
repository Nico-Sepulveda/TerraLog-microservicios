package cl.induztek.ms_usuarios.controlador;

import cl.induztek.ms_usuarios.modelo.Usuario;
import cl.induztek.ms_usuarios.repositorio.UsuarioRepositorio;
import cl.induztek.ms_usuarios.servicio.UsuarioServicio;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioRepositorio repo;
    private final UsuarioServicio servicio;

    public UsuarioControlador(UsuarioRepositorio repo, UsuarioServicio servicio) {
        this.repo = repo;
        this.servicio = servicio;
    }

    @GetMapping
    public List<Usuario> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        usuario.setId(null);
        return repo.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        usuario.setId(id);
        return ResponseEntity.ok(repo.save(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> datos) {
        return servicio.login(datos.get("correo"), datos.get("clave"))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}