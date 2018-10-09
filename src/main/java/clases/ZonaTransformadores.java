/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import ar.com.sge.geografia.Transformadores;
import ar.com.sge.geografia.Zonas;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jean Pierre
 */
@Entity
@Table(name = "zona_transformadores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ZonaTransformadores.findAll", query = "SELECT z FROM ZonaTransformadores z")
    , @NamedQuery(name = "ZonaTransformadores.findById", query = "SELECT z FROM ZonaTransformadores z WHERE z.id = :id")})
public class ZonaTransformadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "transformador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transformadores transformador;
    @JoinColumn(name = "zona", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Zonas zona;

    public ZonaTransformadores() {
    }

    public ZonaTransformadores(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Transformadores getTransformador() {
        return transformador;
    }

    public void setTransformador(Transformadores transformador) {
        this.transformador = transformador;
    }

    public Zonas getZona() {
        return zona;
    }

    public void setZona(Zonas zona) {
        this.zona = zona;
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
        if (!(object instanceof ZonaTransformadores)) {
            return false;
        }
        ZonaTransformadores other = (ZonaTransformadores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.ZonaTransformadores[ id=" + id + " ]";
    }
    
}
