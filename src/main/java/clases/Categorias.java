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
@Table(name = "categorias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorias.findAll", query = "SELECT c FROM Categorias c")
    , @NamedQuery(name = "Categorias.findById", query = "SELECT c FROM Categorias c WHERE c.id = :id")
    , @NamedQuery(name = "Categorias.findByDescripcion", query = "SELECT c FROM Categorias c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Categorias.findByValorFijo", query = "SELECT c FROM Categorias c WHERE c.valorFijo = :valorFijo")
    , @NamedQuery(name = "Categorias.findByValorVariable", query = "SELECT c FROM Categorias c WHERE c.valorVariable = :valorVariable")
    , @NamedQuery(name = "Categorias.findByConsumoMinimo", query = "SELECT c FROM Categorias c WHERE c.consumoMinimo = :consumoMinimo")
    , @NamedQuery(name = "Categorias.findByConsumoMaximo", query = "SELECT c FROM Categorias c WHERE c.consumoMaximo = :consumoMaximo")})
public class Categorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "valor_fijo")
    private float valorFijo;
    @Basic(optional = false)
    @Column(name = "valor_variable")
    private float valorVariable;
    @Column(name = "consumo_minimo")
    private Integer consumoMinimo;
    @Column(name = "consumo_maximo")
    private Integer consumoMaximo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private Collection<Usuarios> usuariosCollection;

    public Categorias() {
    }

    public Categorias(Integer id) {
        this.id = id;
    }

    public Categorias(Integer id, String descripcion, float valorFijo, float valorVariable) {
        this.id = id;
        this.descripcion = descripcion;
        this.valorFijo = valorFijo;
        this.valorVariable = valorVariable;
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

    public float getValorFijo() {
        return valorFijo;
    }

    public void setValorFijo(float valorFijo) {
        this.valorFijo = valorFijo;
    }

    public float getValorVariable() {
        return valorVariable;
    }

    public void setValorVariable(float valorVariable) {
        this.valorVariable = valorVariable;
    }

    public Integer getConsumoMinimo() {
        return consumoMinimo;
    }

    public void setConsumoMinimo(Integer consumoMinimo) {
        this.consumoMinimo = consumoMinimo;
    }

    public Integer getConsumoMaximo() {
        return consumoMaximo;
    }

    public void setConsumoMaximo(Integer consumoMaximo) {
        this.consumoMaximo = consumoMaximo;
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
        if (!(object instanceof Categorias)) {
            return false;
        }
        Categorias other = (Categorias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Categorias[ id=" + id + " ]";
    }
    
}
