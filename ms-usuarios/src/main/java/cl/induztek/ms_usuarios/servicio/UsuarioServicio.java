package cl.induztek.ms_usuarios.servicio;

import cl.induztek.ms_usuarios.modelo.Usuario;
import cl.induztek.ms_usuarios.repositorio.UsuarioRepositorio;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio repo;

    public UsuarioServicio(UsuarioRepositorio repo) {
        this.repo = repo;
    }

    public Optional<Usuario> login(String correo, String clave) {
        return repo.findByCorreo(correo).filter(u -> u.isActivo() && u.getClave().equals(clave));
    }

    public Optional<Usuario> restablecer(String correo, String nuevaClave) {
        if (correo == null || nuevaClave == null || nuevaClave.length() < 6) return Optional.empty();
        return repo.findByCorreo(correo)
                .filter(Usuario::isActivo)
                .map(u -> {
                    u.setClave(nuevaClave);
                    return repo.save(u);
                });
    }
}