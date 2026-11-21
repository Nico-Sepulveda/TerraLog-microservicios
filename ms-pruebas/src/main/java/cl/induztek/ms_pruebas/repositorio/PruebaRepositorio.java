package cl.induztek.ms_pruebas.repositorio;

import cl.induztek.ms_pruebas.modelo.Prueba;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PruebaRepositorio extends JpaRepository<Prueba, Long> {
    List<Prueba> findByProtocoloId(Long protocoloId);
    long countByProtocoloIdAndRealizadaTrue(Long protocoloId);
}