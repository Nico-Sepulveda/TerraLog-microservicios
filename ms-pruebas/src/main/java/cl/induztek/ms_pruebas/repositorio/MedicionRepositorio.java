package cl.induztek.ms_pruebas.repositorio;

import cl.induztek.ms_pruebas.modelo.Medicion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicionRepositorio extends JpaRepository<Medicion, Long> {
    List<Medicion> findByPruebaId(Long pruebaId);
    void deleteByPruebaId(Long pruebaId);
}