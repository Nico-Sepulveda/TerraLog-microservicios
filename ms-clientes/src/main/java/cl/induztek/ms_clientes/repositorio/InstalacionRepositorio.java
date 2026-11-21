package cl.induztek.ms_clientes.repositorio;

import cl.induztek.ms_clientes.modelo.Instalacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstalacionRepositorio extends JpaRepository<Instalacion, Long> {
    List<Instalacion> findByClienteId(Long clienteId);
}