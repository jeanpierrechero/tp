/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

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

/**
 *
 * @author Jean Pierre
 */
@Entity
@Table(name = "adaptadores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adaptadores.findAll", query = "SELECT a FROM Adaptadores a")
    , @NamedQuery(name = "Adaptadores.findById", query = "SELECT a FROM Adaptadores a WHERE a.id = :id")
    , @NamedQuery(name = "Adaptadores.findByDescripcion", query = "SELECT a FROM Adaptadores a WHERE a.descripcion = :descripcion")})
public class Adaptadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adaptador")
    private Collection<ComandosAdaptadores> comandosAdaptadoresCollection;

    public Adaptadores() {
    }

    public Adaptadores(Integer id) {
        this.id = id;
    }

    public Adaptadores(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<ComandosAdaptadores> getComandosAdaptadoresCollection() {
        return comandosAdaptadoresCollection;
    }

    public void setComandosAdaptadoresCollection(Collection<ComandosAdaptadores> comandosAdaptadoresCollection) {
        this.comandosAdaptadoresCollection = comandosAdaptadoresCollection;
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
        if (!(object instanceof Adaptadores)) {
            return false;
        }
        Adaptadores other = (Adaptadores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Adaptadores[ id=" + id + " ]";
    }
    
}
