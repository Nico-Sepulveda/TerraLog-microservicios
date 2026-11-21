package cl.induztek.ms_protocolos.servicio;

import java.util.ArrayList;
import java.util.List;

public final class PermisosProtocolo {

    public static final List<String> ESTADOS_VISIBLES_CLIENTE = List.of("REGISTRADO_EN_TERRENO", "OBSERVADO", "FORMALIZADO");

    private PermisosProtocolo() {}

    public static boolean puedeRegistrar(String rol) {
        return rol != null && !"CLIENTE".equals(rol);
    }

    public static boolean puedeFormalizar(String rol) {
        return "INGENIERO".equals(rol) || "JEFE_TECNICO".equals(rol);
    }

    public static boolean puedeEditar(String rol, String estado, boolean esCreador) {
        if (!puedeRegistrar(rol)) return false;
        if (!"BORRADOR".equals(estado) && !"OBSERVADO".equals(estado)) return false;
        return esCreador || puedeFormalizar(rol);
    }

    public static List<String> acciones(String rol, String estado) {
        List<String> lista = new ArrayList<>();
        if (!puedeRegistrar(rol)) return lista;
        if ("BORRADOR".equals(estado) || "OBSERVADO".equals(estado)) lista.add("REGISTRAR_EN_TERRENO");
        if ("REGISTRADO_EN_TERRENO".equals(estado) && puedeFormalizar(rol)) {
            lista.add("FORMALIZAR");
            lista.add("OBSERVAR");
        }
        boolean sePuedeAnular = !"FORMALIZADO".equals(estado) && !"ANULADO".equals(estado);
        if (sePuedeAnular && puedeFormalizar(rol)) lista.add("ANULAR");
        return lista;
    }
}