/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Christ
 */
@Entity
@Table(name = "vente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vente.findAll", query = "SELECT v FROM Vente v")
    , @NamedQuery(name = "Vente.findByIdComp", query = "SELECT v FROM Vente v WHERE v.idComp = :idComp")
    , @NamedQuery(name = "Vente.findByIdProd", query = "SELECT v FROM Vente v WHERE v.idProd = :idProd")
    , @NamedQuery(name = "Vente.findByIdClt", query = "SELECT v FROM Vente v WHERE v.idClt = :idClt")
    , @NamedQuery(name = "Vente.findByDateVen", query = "SELECT v FROM Vente v WHERE v.dateVen = :dateVen")
    , @NamedQuery(name = "Vente.findByQteVen", query = "SELECT v FROM Vente v WHERE v.qteVen = :qteVen")})
public class Vente implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected VentePK ventePK;
    @Column(name = "dateVen")
    private String dateVen;
    @Column(name = "qteVen")
    private Integer qteVen;

    public Vente() {
    }

    public Vente(String dateVen) {
        this.dateVen = dateVen;
    }

    public VentePK getVentePK() {
        return ventePK;
    }

    public void setVentePK(VentePK ventePK) {
        this.ventePK = ventePK;
    }

    public String getDateVen() {
        return dateVen;
    }

    public void setDateVen(String dateVen) {
        this.dateVen = dateVen;
    }

    public Integer getQteVen() {
        return qteVen;
    }

    public void setQteVen(Integer qteVen) {
        this.qteVen = qteVen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dateVen != null ? dateVen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vente)) {
            return false;
        }
        Vente other = (Vente) object;
        if ((this.dateVen == null && other.dateVen != null) || (this.dateVen != null && !this.dateVen.equals(other.dateVen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entites.Vente[ dateVen=" + dateVen + " ]";
    }

}
