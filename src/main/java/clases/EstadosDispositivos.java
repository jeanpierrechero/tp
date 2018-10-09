/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jean Pierre
 */
@Entity
@Table(name = "estados_dispositivos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadosDispositivos.findAll", query = "SELECT e FROM EstadosDispositivos e")
    , @NamedQuery(name = "EstadosDispositivos.findById", query = "SELECT e FROM EstadosDispositivos e WHERE e.id = :id")
    , @NamedQuery(name = "EstadosDispositivos.findByFechaInicio", query = "SELECT e FROM EstadosDispositivos e WHERE e.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "EstadosDispositivos.findByFechaFin", query = "SELECT e FROM EstadosDispositivos e WHERE e.fechaFin = :fechaFin")
    , @NamedQuery(name = "EstadosDispositivos.findByConsumo", query = "SELECT e FROM EstadosDispositivos e WHERE e.consumo = :consumo")})
public class EstadosDispositivos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "consumo")
    private Float consumo;
    @JoinColumn(name = "estado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estados estado;
    @JoinColumn(name = "usuario_dispositivo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UsuarioDispositivos usuarioDispositivo;

    public EstadosDispositivos() {
    }

    public EstadosDispositivos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Float getConsumo() {
        return consumo;
    }

    public void setConsumo(Float consumo) {
        this.consumo = consumo;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public UsuarioDispositivos getUsuarioDispositivo() {
        return usuarioDispositivo;
    }

    public void setUsuarioDispositivo(UsuarioDispositivos usuarioDispositivo) {
        this.usuarioDispositivo = usuarioDispositivo;
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
        if (!(object instanceof EstadosDispositivos)) {
            return false;
        }
        EstadosDispositivos other = (EstadosDispositivos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.EstadosDispositivos[ id=" + id + " ]";
    }
    
}
