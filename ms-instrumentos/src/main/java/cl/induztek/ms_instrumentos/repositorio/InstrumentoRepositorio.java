package cl.induztek.ms_instrumentos.repositorio;

import cl.induztek.ms_instrumentos.modelo.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentoRepositorio extends JpaRepository<Instrumento, Long> {
}