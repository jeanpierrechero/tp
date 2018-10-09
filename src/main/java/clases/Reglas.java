/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

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
@Table(name = "reglas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reglas.findAll", query = "SELECT r FROM Reglas r")
    , @NamedQuery(name = "Reglas.findById", query = "SELECT r FROM Reglas r WHERE r.id = :id")
    , @NamedQuery(name = "Reglas.findByOperador", query = "SELECT r FROM Reglas r WHERE r.operador = :operador")
    , @NamedQuery(name = "Reglas.findByValor", query = "SELECT r FROM Reglas r WHERE r.valor = :valor")
    , @NamedQuery(name = "Reglas.findByAccion", query = "SELECT r FROM Reglas r WHERE r.accion = :accion")})
public class Reglas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "operador")
    private String operador;
    @Basic(optional = false)
    @Column(name = "valor")
    private float valor;
    @Basic(optional = false)
    @Column(name = "accion")
    private String accion;
    @JoinColumn(name = "actuador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Actuadores actuador;

    public Reglas() {
    }

    public Reglas(Integer id) {
        this.id = id;
    }

    public Reglas(Integer id, String operador, float valor, String accion) {
        this.id = id;
        this.operador = operador;
        this.valor = valor;
        this.accion = accion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Actuadores getActuador() {
        return actuador;
    }

    public void setActuador(Actuadores actuador) {
        this.actuador = actuador;
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
        if (!(object instanceof Reglas)) {
            return false;
        }
        Reglas other = (Reglas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Reglas[ id=" + id + " ]";
    }
    
}
