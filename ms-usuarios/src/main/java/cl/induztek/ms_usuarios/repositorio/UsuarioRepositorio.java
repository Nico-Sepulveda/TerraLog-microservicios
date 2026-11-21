package cl.induztek.ms_usuarios.repositorio;

import cl.induztek.ms_usuarios.modelo.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
}