package cl.induztek.ms_pruebas.servicio;

import cl.induztek.ms_pruebas.cliente.EstadoCalibracionDto;
import cl.induztek.ms_pruebas.cliente.InstrumentosClient;
import cl.induztek.ms_pruebas.cliente.ProtocoloDto;
import cl.induztek.ms_pruebas.cliente.ProtocolosCliente;
import cl.induztek.ms_pruebas.dominio.CampoMedicion;
import cl.induztek.ms_pruebas.dominio.Resultado;
import cl.induztek.ms_pruebas.dominio.ResultadoPrueba;
import cl.induztek.ms_pruebas.dominio.Resultados;
import cl.induztek.ms_pruebas.dominio.TipoPrueba;
import cl.induztek.ms_pruebas.dominio.ValorCalculado;
import cl.induztek.ms_pruebas.modelo.Medicion;
import cl.induztek.ms_pruebas.modelo.Prueba;
import cl.induztek.ms_pruebas.modelo.PruebaDetalle;
import cl.induztek.ms_pruebas.modelo.SolicitudPrueba;
import cl.induztek.ms_pruebas.repositorio.MedicionRepositorio;
import cl.induztek.ms_pruebas.repositorio.PruebaRepositorio;
import feign.FeignException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PruebaServicio {

    private final PruebaRepositorio pruebas;
    private final MedicionRepositorio mediciones;
    private final InstrumentosClient instrumentos;
    private final ProtocolosCliente protocolos;

    public PruebaServicio(PruebaRepositorio pruebas, MedicionRepositorio mediciones, InstrumentosClient instrumentos, ProtocolosCliente protocolos) {
        this.pruebas = pruebas;
        this.mediciones = mediciones;
        this.instrumentos = instrumentos;
        this.protocolos = protocolos;
    }

    public List<Prueba> listar(Long protocoloId) {
        return protocoloId == null ? pruebas.findAll() : pruebas.findByProtocoloId(protocoloId);
    }

    public PruebaDetalle detalle(Long id) {
        Prueba prueba = buscar(id);
        return new PruebaDetalle(prueba, mediciones.findByPruebaId(id));
    }

    public long cantidadRealizadas(Long protocoloId) {
        return pruebas.countByProtocoloIdAndRealizadaTrue(protocoloId);
    }

    @Transactional
    public PruebaDetalle registrar(SolicitudPrueba solicitud) {
        TipoPrueba tipo = TipoPrueba.desde(solicitud.tipoPrueba());
        if (tipo == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de prueba no válido");
        if (solicitud.protocoloId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta el protocolo");
        verificarProtocolo(solicitud.protocoloId());
        String errorComentario = ValidacionesPrueba.validarComentario(solicitud.comentario());
        if (errorComentario != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorComentario);

        boolean realizada = solicitud.realizada() == null || solicitud.realizada();
        Map<String, Double> valores = solicitud.valores();
        ResultadoPrueba calculo = null;

        if (realizada) {
            String error = ValidacionesPrueba.validarValores(tipo, valores);
            if (error != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
            verificarInstrumento(solicitud.instrumentoId());
            calculo = Resultados.calcular(tipo, valores, solicitud.maxContactos());
        } else {
            String error = ValidacionesPrueba.validarMotivo(solicitud.motivoNoRealizada());
            if (error != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        }

        String resultado = calculo != null && calculo.resultado() != null ? calculo.resultado().name() : null;
        Prueba prueba = pruebas.save(new Prueba(
                solicitud.protocoloId(),
                tipo.name(),
                realizada ? solicitud.instrumentoId() : null,
                realizada,
                realizada ? null : solicitud.motivoNoRealizada().trim(),
                resultado,
                solicitud.comentario()));

        if (realizada) {
            for (CampoMedicion campo : tipo.getCampos()) {
                Double valor = valores.get(campo.clave());
                if (valor != null) mediciones.save(new Medicion(prueba.getId(), campo.clave(), campo.nombre(), valor, campo.unidad(), false));
            }
            if (tipo == TipoPrueba.OPERACION_RELE) {
                mediciones.save(new Medicion(prueba.getId(), "opera", "Opera", ValidacionesPrueba.opera(valores) ? 1.0 : 0.0, "", false));
            }
            for (ValorCalculado calculado : calculo.calculados()) {
                mediciones.save(new Medicion(prueba.getId(), calculado.clave(), calculado.nombre(), calculado.valor(), calculado.unidad(), true));
            }
        }

        recalcularResultadoGlobal(solicitud.protocoloId());
        return new PruebaDetalle(prueba, mediciones.findByPruebaId(prueba.getId()));
    }

    public Prueba actualizarComentario(Long id, String comentario) {
        String error = ValidacionesPrueba.validarComentario(comentario);
        if (error != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        Prueba prueba = buscar(id);
        verificarProtocolo(prueba.getProtocoloId());
        prueba.setComentario(comentario);
        return pruebas.save(prueba);
    }

    @Transactional
    public void eliminar(Long id) {
        Prueba prueba = buscar(id);
        verificarProtocolo(prueba.getProtocoloId());
        mediciones.deleteByPruebaId(id);
        pruebas.delete(prueba);
        recalcularResultadoGlobal(prueba.getProtocoloId());
    }

    private Prueba buscar(Long id) {
        return pruebas.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prueba no encontrada"));
    }

    private void verificarProtocolo(Long protocoloId) {
        ProtocoloDto protocolo = protocolos.obtener(protocoloId);
        if (protocolo == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El protocolo no existe");
        if (!ValidacionesPrueba.estadoAdmitePruebas(protocolo.estado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El protocolo no admite cambios de pruebas en estado " + protocolo.estado());
        }
    }

    private void verificarInstrumento(Long instrumentoId) {
        if (instrumentoId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selecciona el instrumento utilizado");
        try {
            EstadoCalibracionDto estado = instrumentos.calibracion(instrumentoId);
            if (!estado.vigente()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, estado.mensaje());
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El instrumento no existe");
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "ms-instrumentos no está disponible");
        }
    }

    private void recalcularResultadoGlobal(Long protocoloId) {
        List<Resultado> resultados = pruebas.findByProtocoloId(protocoloId).stream()
                .filter(Prueba::isRealizada)
                .map(p -> Resultado.desde(p.getResultado()))
                .filter(Objects::nonNull)
                .toList();
        Resultado peor = Resultado.peor(resultados);
        protocolos.actualizarResultado(protocoloId, peor == null ? null : peor.name());
    }
}