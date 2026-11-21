package cl.induztek.ms_usuarios.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String rut;

    @Column(unique = true, nullable = false)
    private String correo;

    private String clave;
    private String rol;
    private boolean activo = true;
    private Long clienteId;

    public Usuario() {}

    public Usuario(String nombre, String rut, String correo, String clave, String rol, Long clienteId) {
        this.nombre = nombre;
        this.rut = rut;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
        this.clienteId = clienteId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}