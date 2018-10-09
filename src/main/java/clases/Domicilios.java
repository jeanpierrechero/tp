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
@Table(name = "domicilios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Domicilios.findAll", query = "SELECT d FROM Domicilios d")
    , @NamedQuery(name = "Domicilios.findById", query = "SELECT d FROM Domicilios d WHERE d.id = :id")
    , @NamedQuery(name = "Domicilios.findByNombreDomicilio", query = "SELECT d FROM Domicilios d WHERE d.nombreDomicilio = :nombreDomicilio")
    , @NamedQuery(name = "Domicilios.findByCoordenadaX", query = "SELECT d FROM Domicilios d WHERE d.coordenadaX = :coordenadaX")
    , @NamedQuery(name = "Domicilios.findByCoordenadaY", query = "SELECT d FROM Domicilios d WHERE d.coordenadaY = :coordenadaY")})
public class Domicilios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre_domicilio")
    private String nombreDomicilio;
    @Basic(optional = false)
    @Column(name = "coordenada_x")
    private int coordenadaX;
    @Basic(optional = false)
    @Column(name = "coordenada_y")
    private int coordenadaY;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "domicilio")
    private Collection<Usuarios> usuariosCollection;

    public Domicilios() {
    }

    public Domicilios(Integer id) {
        this.id = id;
    }

    public Domicilios(Integer id, int coordenadaX, int coordenadaY) {
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

    public String getNombreDomicilio() {
        return nombreDomicilio;
    }

    public void setNombreDomicilio(String nombreDomicilio) {
        this.nombreDomicilio = nombreDomicilio;
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
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
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
        if (!(object instanceof Domicilios)) {
            return false;
        }
        Domicilios other = (Domicilios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Domicilios[ id=" + id + " ]";
    }
    
}
