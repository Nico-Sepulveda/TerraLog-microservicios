package cl.induztek.ms_equipos.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ValidacionesEquipoTest {

    @Test
    void tagValidoNoDaError() {
        assertNull(ValidacionesEquipo.validarTag("TR-01", List.of()));
    }

    @Test
    void tagVacioPideIngresarlo() {
        assertEquals("Ingresa el TAG del equipo", ValidacionesEquipo.validarTag("  ", List.of()));
    }

    @Test
    void tagRepetidoSeDetectaSinImportarMayusculas() {
        assertEquals("Ya existe un equipo con ese TAG en la instalación", ValidacionesEquipo.validarTag("tr-01", List.of("TR-01")));
    }

    @Test
    void anioFueraDeRangoDaError() {
        assertEquals("Debe estar entre 1950 y 2026", ValidacionesEquipo.validarAnio(1900, 2026));
    }

    @Test
    void soloInterruptorYCeldaUsanContactos() {
        assertTrue(ValidacionesEquipo.usaContactos("INTERRUPTOR"));
        assertFalse(ValidacionesEquipo.usaContactos("TRANSFORMADOR"));
    }
}