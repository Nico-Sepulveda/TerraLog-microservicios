package cl.induztek.ms_instrumentos.servicio;

import cl.induztek.ms_instrumentos.modelo.EstadoCalibracion;
import cl.induztek.ms_instrumentos.modelo.Instrumento;
import cl.induztek.ms_instrumentos.repositorio.InstrumentoRepositorio;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InstrumentoServicio {

    private final InstrumentoRepositorio repo;

    public InstrumentoServicio(InstrumentoRepositorio repo) {
        this.repo = repo;
    }

    public Instrumento crear(Instrumento instrumento) {
        instrumento.setId(null);
        validar(instrumento);
        return repo.save(instrumento);
    }

    public Instrumento actualizar(Long id, Instrumento instrumento) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instrumento no encontrado");
        instrumento.setId(id);
        validar(instrumento);
        return repo.save(instrumento);
    }

    public EstadoCalibracion calibracion(Long id) {
        Instrumento instrumento = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instrumento no encontrado"));
        LocalDate hoy = LocalDate.now();
        String error = ValidacionesInstrumento.validarCalibracion(instrumento.getVencimientoCalibracion(), hoy);
        return new EstadoCalibracion(instrumento.getId(), instrumento.getNombre(), instrumento.getVencimientoCalibracion(), error == null, error == null ? "Calibración vigente" : error);
    }

    private void validar(Instrumento instrumento) {
        String error = ValidacionesInstrumento.validar(instrumento);
        if (error != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
    }
}