package cl.induztek.ms_clientes.repositorio;

import cl.induztek.ms_clientes.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}