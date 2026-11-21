package cl.induztek.ms_equipos.controlador;

import cl.induztek.ms_equipos.modelo.Equipo;
import cl.induztek.ms_equipos.repositorio.EquipoRepositorio;
import cl.induztek.ms_equipos.servicio.EquipoServicio;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/equipos")
public class EquipoControlador {

    private final EquipoRepositorio repo;
    private final EquipoServicio servicio;

    public EquipoControlador(EquipoRepositorio repo, EquipoServicio servicio) {
        this.repo = repo;
        this.servicio = servicio;
    }

    @GetMapping
    public List<Equipo> listar(@RequestParam(required = false) Long instalacionId, @RequestParam(required = false) Long clienteId) {
        if (clienteId != null) return servicio.deCliente(clienteId);
        if (instalacionId != null) return repo.findByInstalacionId(instalacionId);
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtener(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Equipo crear(@RequestBody Equipo equipo) {
        return servicio.crear(equipo);
    }

    @PutMapping("/{id}")
    public Equipo actualizar(@PathVariable Long id, @RequestBody Equipo equipo) {
        return servicio.actualizar(id, equipo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/foto")
    public Equipo subirFoto(@PathVariable Long id, @RequestParam("foto") MultipartFile foto) throws IOException {
        return servicio.guardarFoto(id, foto);
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> verFoto(@PathVariable Long id) throws IOException {
        Path ruta = servicio.rutaFoto(id);
        if (!Files.exists(ruta)) return ResponseEntity.notFound().build();
        String tipo = Files.probeContentType(ruta);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(tipo != null ? tipo : "application/octet-stream"))
                .body(Files.readAllBytes(ruta));
    }
}