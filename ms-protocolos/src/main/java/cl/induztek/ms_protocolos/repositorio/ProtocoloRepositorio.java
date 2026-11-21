package cl.induztek.ms_protocolos.repositorio;

import cl.induztek.ms_protocolos.modelo.Protocolo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProtocoloRepositorio extends JpaRepository<Protocolo, Long> {
    List<Protocolo> findByEquipoIdOrderByFechaHoraDesc(Long equipoId);
    boolean existsByNumero(String numero);
}