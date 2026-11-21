package cl.induztek.ms_protocolos.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class PermisosProtocoloTest {

    @Test
    void clienteNoPuedeRegistrar() {
        assertFalse(PermisosProtocolo.puedeRegistrar("CLIENTE"));
        assertTrue(PermisosProtocolo.puedeRegistrar("TECNICO"));
    }

    @Test
    void soloIngenieroYJefeFormalizan() {
        assertTrue(PermisosProtocolo.puedeFormalizar("INGENIERO"));
        assertFalse(PermisosProtocolo.puedeFormalizar("TECNICO"));
    }

    @Test
    void tecnicoCreadorEditaSoloBorradorUObservado() {
        assertTrue(PermisosProtocolo.puedeEditar("TECNICO", "BORRADOR", true));
        assertFalse(PermisosProtocolo.puedeEditar("TECNICO", "FORMALIZADO", true));
    }

    @Test
    void ingenieroTieneTresAccionesEnProtocoloRegistrado() {
        assertEquals(List.of("FORMALIZAR", "OBSERVAR", "ANULAR"), PermisosProtocolo.acciones("INGENIERO", "REGISTRADO_EN_TERRENO"));
    }
}