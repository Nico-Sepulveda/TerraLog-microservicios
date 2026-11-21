package cl.induztek.ms_equipos.servicio;

import cl.induztek.ms_equipos.cliente.ClientesClient;
import cl.induztek.ms_equipos.cliente.InstalacionDto;
import cl.induztek.ms_equipos.modelo.Equipo;
import cl.induztek.ms_equipos.repositorio.EquipoRepositorio;
import feign.FeignException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Year;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EquipoServicio {

    private final EquipoRepositorio repo;
    private final ClientesClient clientes;
    private final String carpetaFotos;

    public EquipoServicio(EquipoRepositorio repo, ClientesClient clientes, @Value("${terralog.carpeta-fotos}") String carpetaFotos) {
        this.repo = repo;
        this.clientes = clientes;
        this.carpetaFotos = carpetaFotos;
    }

    public Equipo crear(Equipo equipo) {
        equipo.setId(null);
        equipo.setRutaFotoPlaca(null);
        validar(equipo, null);
        return repo.save(equipo);
    }

    public Equipo actualizar(Long id, Equipo equipo) {
        Equipo actual = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipo no encontrado"));
        equipo.setId(id);
        equipo.setRutaFotoPlaca(actual.getRutaFotoPlaca());
        validar(equipo, id);
        return repo.save(equipo);
    }

    public List<Equipo> deCliente(Long clienteId) {
        List<Long> ids = instalacionesDe(clienteId).stream().map(InstalacionDto::id).toList();
        return repo.findByInstalacionIdIn(ids);
    }

    public Equipo guardarFoto(Long id, MultipartFile archivo) throws IOException {
        Equipo equipo = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipo no encontrado"));
        if (archivo.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La foto está vacía");
        String nombre = archivo.getOriginalFilename();
        String extension = nombre != null && nombre.contains(".") ? nombre.substring(nombre.lastIndexOf('.')) : ".jpg";
        Path carpeta = Path.of(carpetaFotos);
        Files.createDirectories(carpeta);
        String archivoFinal = "equipo-" + id + extension;
        Files.copy(archivo.getInputStream(), carpeta.resolve(archivoFinal), StandardCopyOption.REPLACE_EXISTING);
        equipo.setRutaFotoPlaca(archivoFinal);
        return repo.save(equipo);
    }

    public Path rutaFoto(Long id) {
        Equipo equipo = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipo no encontrado"));
        if (equipo.getRutaFotoPlaca() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El equipo no tiene foto");
        return Path.of(carpetaFotos).resolve(equipo.getRutaFotoPlaca());
    }

    private void validar(Equipo equipo, Long idActual) {
        verificarInstalacion(equipo.getInstalacionId());
        List<String> tags = repo.findByInstalacionId(equipo.getInstalacionId()).stream()
                .filter(e -> !e.getId().equals(idActual))
                .map(Equipo::getTag)
                .toList();
        String error = ValidacionesEquipo.validar(equipo, tags, Year.now().getValue());
        if (error != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
    }

    private void verificarInstalacion(Long instalacionId) {
        if (instalacionId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selecciona la instalación");
        try {
            clientes.instalacion(instalacionId);
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La instalación no existe");
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "ms-clientes no está disponible");
        }
    }

    private List<InstalacionDto> instalacionesDe(Long clienteId) {
        try {
            return clientes.instalacionesDeCliente(clienteId);
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "ms-clientes no está disponible");
        }
    }
}