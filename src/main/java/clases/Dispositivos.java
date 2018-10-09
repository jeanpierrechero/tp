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
@Table(name = "dispositivos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dispositivos.findAll", query = "SELECT d FROM Dispositivos d")
    , @NamedQuery(name = "Dispositivos.findById", query = "SELECT d FROM Dispositivos d WHERE d.id = :id")
    , @NamedQuery(name = "Dispositivos.findByNombreDispositivo", query = "SELECT d FROM Dispositivos d WHERE d.nombreDispositivo = :nombreDispositivo")
    , @NamedQuery(name = "Dispositivos.findByInteligente", query = "SELECT d FROM Dispositivos d WHERE d.inteligente = :inteligente")
    , @NamedQuery(name = "Dispositivos.findByBajoConsumo", query = "SELECT d FROM Dispositivos d WHERE d.bajoConsumo = :bajoConsumo")
    , @NamedQuery(name = "Dispositivos.findByConsumoKwh", query = "SELECT d FROM Dispositivos d WHERE d.consumoKwh = :consumoKwh")
    , @NamedQuery(name = "Dispositivos.findByConsumoMin", query = "SELECT d FROM Dispositivos d WHERE d.consumoMin = :consumoMin")
    , @NamedQuery(name = "Dispositivos.findByConsumoMax", query = "SELECT d FROM Dispositivos d WHERE d.consumoMax = :consumoMax")})
public class Dispositivos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre_dispositivo")
    private String nombreDispositivo;
    @Basic(optional = false)
    @Column(name = "inteligente")
    private String inteligente;
    @Basic(optional = false)
    @Column(name = "bajo_consumo")
    private String bajoConsumo;
    @Basic(optional = false)
    @Column(name = "consumo_kwh")
    private float consumoKwh;
    @Column(name = "consumo_min")
    private Integer consumoMin;
    @Column(name = "consumo_max")
    private Integer consumoMax;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dispositivo")
    private Collection<UsuarioDispositivos> usuarioDispositivosCollection;

    public Dispositivos() {
    }

    public Dispositivos(Integer id) {
        this.id = id;
    }

    public Dispositivos(Integer id, String nombreDispositivo, String inteligente, String bajoConsumo, float consumoKwh) {
        this.id = id;
        this.nombreDispositivo = nombreDispositivo;
        this.inteligente = inteligente;
        this.bajoConsumo = bajoConsumo;
        this.consumoKwh = consumoKwh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreDispositivo() {
        return nombreDispositivo;
    }

    public void setNombreDispositivo(String nombreDispositivo) {
        this.nombreDispositivo = nombreDispositivo;
    }

    public String getInteligente() {
        return inteligente;
    }

    public void setInteligente(String inteligente) {
        this.inteligente = inteligente;
    }

    public String getBajoConsumo() {
        return bajoConsumo;
    }

    public void setBajoConsumo(String bajoConsumo) {
        this.bajoConsumo = bajoConsumo;
    }

    public float getConsumoKwh() {
        return consumoKwh;
    }

    public void setConsumoKwh(float consumoKwh) {
        this.consumoKwh = consumoKwh;
    }

    public Integer getConsumoMin() {
        return consumoMin;
    }

    public void setConsumoMin(Integer consumoMin) {
        this.consumoMin = consumoMin;
    }

    public Integer getConsumoMax() {
        return consumoMax;
    }

    public void setConsumoMax(Integer consumoMax) {
        this.consumoMax = consumoMax;
    }

    @XmlTransient
    public Collection<UsuarioDispositivos> getUsuarioDispositivosCollection() {
        return usuarioDispositivosCollection;
    }

    public void setUsuarioDispositivosCollection(Collection<UsuarioDispositivos> usuarioDispositivosCollection) {
        this.usuarioDispositivosCollection = usuarioDispositivosCollection;
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
        if (!(object instanceof Dispositivos)) {
            return false;
        }
        Dispositivos other = (Dispositivos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Dispositivos[ id=" + id + " ]";
    }
    
}
