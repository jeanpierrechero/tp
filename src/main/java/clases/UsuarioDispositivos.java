/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jean Pierre
 */
@Entity
@Table(name = "usuario_dispositivos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioDispositivos.findAll", query = "SELECT u FROM UsuarioDispositivos u")
    , @NamedQuery(name = "UsuarioDispositivos.findById", query = "SELECT u FROM UsuarioDispositivos u WHERE u.id = :id")
    , @NamedQuery(name = "UsuarioDispositivos.findByApagadoAutomatico", query = "SELECT u FROM UsuarioDispositivos u WHERE u.apagadoAutomatico = :apagadoAutomatico")
    , @NamedQuery(name = "UsuarioDispositivos.findByEstado", query = "SELECT u FROM UsuarioDispositivos u WHERE u.estado = :estado")
    , @NamedQuery(name = "UsuarioDispositivos.findByEncendido", query = "SELECT u FROM UsuarioDispositivos u WHERE u.encendido = :encendido")
    , @NamedQuery(name = "UsuarioDispositivos.findByPeriodo", query = "SELECT u FROM UsuarioDispositivos u WHERE u.periodo = :periodo")
    , @NamedQuery(name = "UsuarioDispositivos.findByModulo", query = "SELECT u FROM UsuarioDispositivos u WHERE u.modulo = :modulo")})
public class UsuarioDispositivos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "apagado_automatico")
    private Boolean apagadoAutomatico;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Column(name = "encendido")
    private Boolean encendido;
    @Column(name = "periodo")
    @Temporal(TemporalType.DATE)
    private Date periodo;
    @Basic(optional = false)
    @Column(name = "modulo")
    private boolean modulo;
    @JoinColumn(name = "dispositivo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Dispositivos dispositivo;
    @JoinColumn(name = "sensor", referencedColumnName = "id")
    @ManyToOne
    private Sensores sensor;
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioDispositivo")
    private Collection<EstadosDispositivos> estadosDispositivosCollection;

    public UsuarioDispositivos() {
    }

    public UsuarioDispositivos(Integer id) {
        this.id = id;
    }

    public UsuarioDispositivos(Integer id, int estado, boolean modulo) {
        this.id = id;
        this.estado = estado;
        this.modulo = modulo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getApagadoAutomatico() {
        return apagadoAutomatico;
    }

    public void setApagadoAutomatico(Boolean apagadoAutomatico) {
        this.apagadoAutomatico = apagadoAutomatico;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Boolean getEncendido() {
        return encendido;
    }

    public void setEncendido(Boolean encendido) {
        this.encendido = encendido;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    public boolean getModulo() {
        return modulo;
    }

    public void setModulo(boolean modulo) {
        this.modulo = modulo;
    }

    public Dispositivos getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivos dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Sensores getSensor() {
        return sensor;
    }

    public void setSensor(Sensores sensor) {
        this.sensor = sensor;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<EstadosDispositivos> getEstadosDispositivosCollection() {
        return estadosDispositivosCollection;
    }

    public void setEstadosDispositivosCollection(Collection<EstadosDispositivos> estadosDispositivosCollection) {
        this.estadosDispositivosCollection = estadosDispositivosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioDispositivos)) {
            return false;
        }
        UsuarioDispositivos other = (UsuarioDispositivos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.UsuarioDispositivos[ id=" + id + " ]";
    }
    
}
