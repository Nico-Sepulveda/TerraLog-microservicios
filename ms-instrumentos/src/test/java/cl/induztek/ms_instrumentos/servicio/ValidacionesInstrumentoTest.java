package cl.induztek.ms_instrumentos.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ValidacionesInstrumentoTest {

    private final LocalDate hoy = LocalDate.of(2026, 11, 21);

    @Test
    void instrumentoConFechaPasadaEstaVencido() {
        assertTrue(ValidacionesInstrumento.estaVencido(LocalDate.of(2026, 8, 20), hoy));
    }

    @Test
    void instrumentoQueVenceHoyAunNoEstaVencido() {
        assertFalse(ValidacionesInstrumento.estaVencido(hoy, hoy));
    }

    @Test
    void textoVacioPideIngresarlo() {
        assertEquals("Ingresa el nombre", ValidacionesInstrumento.validarTexto("", "el nombre"));
    }

    @Test
    void calibracionVencidaDaError() {
        assertEquals("Calibración vencida el 2026-08-20", ValidacionesInstrumento.validarCalibracion(LocalDate.of(2026, 8, 20), hoy));
    }

    @Test
    void calibracionVigenteNoDaError() {
        assertNull(ValidacionesInstrumento.validarCalibracion(LocalDate.of(2027, 1, 15), hoy));
    }
}