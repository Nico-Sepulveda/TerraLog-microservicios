package cl.induztek.ms_pruebas.dominio;

public record CampoMedicion(String clave, String nombre, String unidad, double min, double max, boolean soloSiOpera) {
}