package cl.induztek.ms_protocolos.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ValidacionesProtocoloTest {

    @Test
    void temperaturaValidaNoDaError() {
        assertNull(ValidacionesProtocolo.validarTemperatura(20.0));
    }

    @Test
    void temperaturaFueraDeRangoDaError() {
        assertEquals("Debe estar entre -20 y 60 °C", ValidacionesProtocolo.validarTemperatura(70.0));
    }

    @Test
    void humedadNulaEsObligatoria() {
        assertEquals("Campo obligatorio", ValidacionesProtocolo.validarHumedad(null));
    }

    @Test
    void motivoMuyCortoDaError() {
        assertEquals("Explícalo con al menos 5 caracteres", ValidacionesProtocolo.validarMotivo("hola"));
    }

    @Test
    void formatoNumeroRellenaConCeros() {
        assertEquals("PT-2026-0007", ValidacionesProtocolo.formatoNumero(2026, 7));
    }
}