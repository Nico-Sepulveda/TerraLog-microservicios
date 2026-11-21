package cl.induztek.ms_pruebas.modelo;

import java.util.Map;

public record SolicitudPrueba(Long protocoloId, String tipoPrueba, Long instrumentoId, Boolean realizada, String motivoNoRealizada, String comentario, Double maxContactos, Map<String, Double> valores) {
}