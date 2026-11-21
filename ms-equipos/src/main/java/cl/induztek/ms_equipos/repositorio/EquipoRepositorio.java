package cl.induztek.ms_equipos.repositorio;

import cl.induztek.ms_equipos.modelo.Equipo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoRepositorio extends JpaRepository<Equipo, Long> {
    List<Equipo> findByInstalacionId(Long instalacionId);
    List<Equipo> findByInstalacionIdIn(List<Long> instalacionIds);
}