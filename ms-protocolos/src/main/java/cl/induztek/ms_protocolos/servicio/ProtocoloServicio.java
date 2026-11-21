package cl.induztek.ms_protocolos.servicio;

import cl.induztek.ms_protocolos.cliente.EquiposCliente;
import cl.induztek.ms_protocolos.cliente.PruebasCliente;
import cl.induztek.ms_protocolos.modelo.EdicionProtocolo;
import cl.induztek.ms_protocolos.modelo.NuevoProtocolo;
import cl.induztek.ms_protocolos.modelo.Protocolo;
import cl.induztek.ms_protocolos.modelo.ResultadoGlobal;
import cl.induztek.ms_protocolos.modelo.SolicitudAccion;
import cl.induztek.ms_protocolos.repositorio.ProtocoloRepositorio;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProtocoloServicio {

    private final ProtocoloRepositorio repo;
    private final EquiposCliente equipos;
    private final PruebasCliente pruebas;

    public ProtocoloServicio(ProtocoloRepositorio repo, EquiposCliente equipos, PruebasCliente pruebas) {
        this.repo = repo;
        this.equipos = equipos;
        this.pruebas = pruebas;
    }

    public List<Protocolo> listar(Long equipoId, String rol) {
        List<Protocolo> lista = equipoId == null ? repo.findAll() : repo.findByEquipoIdOrderByFechaHoraDesc(equipoId);
        if ("CLIENTE".equals(rol)) {
            return lista.stream().filter(p -> PermisosProtocolo.ESTADOS_VISIBLES_CLIENTE.contains(p.getEstado())).toList();
        }
        return lista;
    }

    public Protocolo buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Protocolo no encontrado"));
    }

    public List<String> acciones(Long id, String rol) {
        return PermisosProtocolo.acciones(rol, buscar(id).getEstado());
    }

    public Protocolo crear(NuevoProtocolo nuevo) {
        if (!PermisosProtocolo.puedeRegistrar(nuevo.rol())) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tu rol no puede crear protocolos");
        if (nuevo.equipoId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selecciona el equipo");
        if (nuevo.tecnicoId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta el técnico");
        validarCondiciones(nuevo.temperatura(), nuevo.humedad());
        if (!equipos.existe(nuevo.equipoId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El equipo no existe");
        int anio = Year.now().getValue();
        int correlativo = (int) repo.count() + 1;
        String numero = ValidacionesProtocolo.formatoNumero(anio, correlativo);
        while (repo.existsByNumero(numero)) {
            correlativo++;
            numero = ValidacionesProtocolo.formatoNumero(anio, correlativo);
        }
        Protocolo protocolo = new Protocolo(numero, nuevo.equipoId(), nuevo.tecnicoId(), LocalDateTime.now(), nuevo.temperatura(), nuevo.humedad(), "BORRADOR");
        return repo.save(protocolo);
    }

    public Protocolo editar(Long id, EdicionProtocolo edicion) {
        Protocolo protocolo = buscar(id);
        boolean esCreador = protocolo.getTecnicoId().equals(edicion.usuarioId());
        if (!PermisosProtocolo.puedeEditar(edicion.rol(), protocolo.getEstado(), esCreador)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes editar este protocolo");
        }
        validarCondiciones(edicion.temperatura(), edicion.humedad());
        protocolo.setTemperatura(edicion.temperatura());
        protocolo.setHumedad(edicion.humedad());
        return repo.save(protocolo);
    }

    public Protocolo cambiarEstado(Long id, SolicitudAccion solicitud) {
        Protocolo protocolo = buscar(id);
        if (solicitud.accion() == null || !PermisosProtocolo.acciones(solicitud.rol(), protocolo.getEstado()).contains(solicitud.accion())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para esa acción en el estado actual");
        }
        switch (solicitud.accion()) {
            case "REGISTRAR_EN_TERRENO" -> {
                if (pruebas.cantidadRealizadas(id) == 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registra al menos una prueba realizada antes de pasar a Registrado en terreno");
                }
                protocolo.setEstado("REGISTRADO_EN_TERRENO");
            }
            case "FORMALIZAR" -> {
                protocolo.setEstado("FORMALIZADO");
                protocolo.setFormalizadoPorId(solicitud.usuarioId());
                protocolo.setFechaFormalizacion(LocalDateTime.now());
            }
            case "OBSERVAR" -> {
                validarMotivo(solicitud.texto());
                protocolo.setEstado("OBSERVADO");
                protocolo.setMotivo(solicitud.texto().trim());
            }
            case "ANULAR" -> {
                validarMotivo(solicitud.texto());
                protocolo.setEstado("ANULADO");
                protocolo.setMotivo(solicitud.texto().trim());
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Acción desconocida");
        }
        return repo.save(protocolo);
    }

    public Protocolo actualizarResultado(Long id, ResultadoGlobal resultado) {
        Protocolo protocolo = buscar(id);
        protocolo.setResultadoGlobal(resultado.resultadoGlobal());
        return repo.save(protocolo);
    }

    public void eliminar(Long id) {
        Protocolo protocolo = buscar(id);
        if (!"BORRADOR".equals(protocolo.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se puede eliminar un protocolo en borrador");
        }
        repo.delete(protocolo);
    }

    private void validarCondiciones(Double temperatura, Double humedad) {
        String error = ValidacionesProtocolo.validarTemperatura(temperatura);
        if (error == null) error = ValidacionesProtocolo.validarHumedad(humedad);
        if (error != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
    }

    private void validarMotivo(String texto) {
        String error = ValidacionesProtocolo.validarMotivo(texto);
        if (error != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
    }
}