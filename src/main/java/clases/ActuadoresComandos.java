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
@Table(name = "actuadores_comandos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActuadoresComandos.findAll", query = "SELECT a FROM ActuadoresComandos a")
    , @NamedQuery(name = "ActuadoresComandos.findById", query = "SELECT a FROM ActuadoresComandos a WHERE a.id = :id")})
public class ActuadoresComandos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "actuador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Actuadores actuador;
    @JoinColumn(name = "comando", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Comandos comando;

    public ActuadoresComandos() {
    }

    public ActuadoresComandos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Actuadores getActuador() {
        return actuador;
    }

    public void setActuador(Actuadores actuador) {
        this.actuador = actuador;
    }

    public Comandos getComando() {
        return comando;
    }

    public void setComando(Comandos comando) {
        this.comando = comando;
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
        if (!(object instanceof ActuadoresComandos)) {
            return false;
        }
        ActuadoresComandos other = (ActuadoresComandos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.ActuadoresComandos[ id=" + id + " ]";
    }
    
}
