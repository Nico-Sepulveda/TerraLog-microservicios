package cl.induztek.ms_protocolos.modelo;

public record NuevoProtocolo(Long equipoId, Long tecnicoId, String rol, Double temperatura, Double humedad) {
}