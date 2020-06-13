package cl.gorilas.user.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Usuarios.
 */
@Document(collection = "usuarios")
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("nombre_completo")
    private String nombreCompleto;

    @Field("correo_electronico")
    private String correoElectronico;

    @Field("documento")
    private String documento;

    @Field("abono")
    private Long abono;

    @Field("estado")
    private String estado;

    @Field("turnos_disponibles")
    private Integer turnosDisponibles;

    @Field("turnos_disponibles_leyenda")
    private String turnosDisponiblesLeyenda;

    @Field("turnos_disponibles_leyenda_html")
    private String turnosDisponiblesLeyendaHtml;

    @Field("abonos_vencimiento_html")
    private String abonosVencimientoHtml;

    @Field("fecha_prox_apto_fisico")
    private Instant fechaProxAptoFisico;

    @Field("fecha_prox_apto_fisico_string")
    private Instant fechaProxAptoFisicoString;

    @Field("dias_restantes")
    private Float diasRestantes;

    @Field("tiene_deuda")
    private Boolean tieneDeuda;

    @Field("saldo")
    private Integer saldo;

    @Field("domicilio")
    private String domicilio;

    @Field("barrio")
    private String barrio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public Usuarios nombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        return this;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Usuarios correoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
        return this;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDocumento() {
        return documento;
    }

    public Usuarios documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Long getAbono() {
        return abono;
    }

    public Usuarios abono(Long abono) {
        this.abono = abono;
        return this;
    }

    public void setAbono(Long abono) {
        this.abono = abono;
    }

    public String getEstado() {
        return estado;
    }

    public Usuarios estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getTurnosDisponibles() {
        return turnosDisponibles;
    }

    public Usuarios turnosDisponibles(Integer turnosDisponibles) {
        this.turnosDisponibles = turnosDisponibles;
        return this;
    }

    public void setTurnosDisponibles(Integer turnosDisponibles) {
        this.turnosDisponibles = turnosDisponibles;
    }

    public String getTurnosDisponiblesLeyenda() {
        return turnosDisponiblesLeyenda;
    }

    public Usuarios turnosDisponiblesLeyenda(String turnosDisponiblesLeyenda) {
        this.turnosDisponiblesLeyenda = turnosDisponiblesLeyenda;
        return this;
    }

    public void setTurnosDisponiblesLeyenda(String turnosDisponiblesLeyenda) {
        this.turnosDisponiblesLeyenda = turnosDisponiblesLeyenda;
    }

    public String getTurnosDisponiblesLeyendaHtml() {
        return turnosDisponiblesLeyendaHtml;
    }

    public Usuarios turnosDisponiblesLeyendaHtml(String turnosDisponiblesLeyendaHtml) {
        this.turnosDisponiblesLeyendaHtml = turnosDisponiblesLeyendaHtml;
        return this;
    }

    public void setTurnosDisponiblesLeyendaHtml(String turnosDisponiblesLeyendaHtml) {
        this.turnosDisponiblesLeyendaHtml = turnosDisponiblesLeyendaHtml;
    }

    public String getAbonosVencimientoHtml() {
        return abonosVencimientoHtml;
    }

    public Usuarios abonosVencimientoHtml(String abonosVencimientoHtml) {
        this.abonosVencimientoHtml = abonosVencimientoHtml;
        return this;
    }

    public void setAbonosVencimientoHtml(String abonosVencimientoHtml) {
        this.abonosVencimientoHtml = abonosVencimientoHtml;
    }

    public Instant getFechaProxAptoFisico() {
        return fechaProxAptoFisico;
    }

    public Usuarios fechaProxAptoFisico(Instant fechaProxAptoFisico) {
        this.fechaProxAptoFisico = fechaProxAptoFisico;
        return this;
    }

    public void setFechaProxAptoFisico(Instant fechaProxAptoFisico) {
        this.fechaProxAptoFisico = fechaProxAptoFisico;
    }

    public Instant getFechaProxAptoFisicoString() {
        return fechaProxAptoFisicoString;
    }

    public Usuarios fechaProxAptoFisicoString(Instant fechaProxAptoFisicoString) {
        this.fechaProxAptoFisicoString = fechaProxAptoFisicoString;
        return this;
    }

    public void setFechaProxAptoFisicoString(Instant fechaProxAptoFisicoString) {
        this.fechaProxAptoFisicoString = fechaProxAptoFisicoString;
    }

    public Float getDiasRestantes() {
        return diasRestantes;
    }

    public Usuarios diasRestantes(Float diasRestantes) {
        this.diasRestantes = diasRestantes;
        return this;
    }

    public void setDiasRestantes(Float diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

    public Boolean isTieneDeuda() {
        return tieneDeuda;
    }

    public Usuarios tieneDeuda(Boolean tieneDeuda) {
        this.tieneDeuda = tieneDeuda;
        return this;
    }

    public void setTieneDeuda(Boolean tieneDeuda) {
        this.tieneDeuda = tieneDeuda;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public Usuarios saldo(Integer saldo) {
        this.saldo = saldo;
        return this;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Usuarios domicilio(String domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getBarrio() {
        return barrio;
    }

    public Usuarios barrio(String barrio) {
        this.barrio = barrio;
        return this;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuarios usuarios = (Usuarios) o;
        if (usuarios.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuarios.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Usuarios{" +
            "id=" + getId() +
            ", nombreCompleto='" + getNombreCompleto() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", abono=" + getAbono() +
            ", estado='" + getEstado() + "'" +
            ", turnosDisponibles=" + getTurnosDisponibles() +
            ", turnosDisponiblesLeyenda='" + getTurnosDisponiblesLeyenda() + "'" +
            ", turnosDisponiblesLeyendaHtml='" + getTurnosDisponiblesLeyendaHtml() + "'" +
            ", abonosVencimientoHtml='" + getAbonosVencimientoHtml() + "'" +
            ", fechaProxAptoFisico='" + getFechaProxAptoFisico() + "'" +
            ", fechaProxAptoFisicoString='" + getFechaProxAptoFisicoString() + "'" +
            ", diasRestantes=" + getDiasRestantes() +
            ", tieneDeuda='" + isTieneDeuda() + "'" +
            ", saldo=" + getSaldo() +
            ", domicilio='" + getDomicilio() + "'" +
            ", barrio='" + getBarrio() + "'" +
            "}";
    }
}
