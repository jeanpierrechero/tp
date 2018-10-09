/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.sge.geografia;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import clases.ZonaTransformadores;

/**
 *
 * @author Jean Pierre
 */
@Entity
@Table(name = "transformadores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transformadores.findAll", query = "SELECT t FROM Transformadores t")
    , @NamedQuery(name = "Transformadores.findById", query = "SELECT t FROM Transformadores t WHERE t.id = :id")
    , @NamedQuery(name = "Transformadores.findByCoordenadaX", query = "SELECT t FROM Transformadores t WHERE t.coordenadaX = :coordenadaX")
    , @NamedQuery(name = "Transformadores.findByCoordenadaY", query = "SELECT t FROM Transformadores t WHERE t.coordenadaY = :coordenadaY")})
public class Transformadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "coordenada_x")
    private int coordenadaX;
    @Basic(optional = false)
    @Column(name = "coordenada_y")
    private int coordenadaY;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transformador")
    private Collection<ZonaTransformadores> zonaTransformadoresCollection;

    public Transformadores() {
    }

    public Transformadores(Integer id) {
        this.id = id;
    }

    public Transformadores(Integer id,int coordenadaX, int coordenadaY) {
    	this.id = id;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    @XmlTransient
    public Collection<ZonaTransformadores> getZonaTransformadoresCollection() {
        return zonaTransformadoresCollection;
    }

    public void setZonaTransformadoresCollection(Collection<ZonaTransformadores> zonaTransformadoresCollection) {
        this.zonaTransformadoresCollection = zonaTransformadoresCollection;
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
        if (!(object instanceof Transformadores)) {
            return false;
        }
        Transformadores other = (Transformadores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Transformadores[ id=" + id + " ]";
    }
    
}
