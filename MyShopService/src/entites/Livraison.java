/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "livraison")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Livraison.findAll", query = "SELECT l FROM Livraison l")
    , @NamedQuery(name = "Livraison.findByNumLiv", query = "SELECT l FROM Livraison l WHERE l.numLiv = :numLiv")
    , @NamedQuery(name = "Livraison.findByNumFour", query = "SELECT l FROM Livraison l WHERE l.numFour = :numFour")})
public class Livraison implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "numLiv")
    private Integer numLiv;
    @Column(name = "numFour")
    private Integer numFour;

    public Livraison() {
    }

    public Livraison(Integer numLiv) {
        this.numLiv = numLiv;
    }

    public Integer getNumLiv() {
        return numLiv;
    }

    public void setNumLiv(Integer numLiv) {
        this.numLiv = numLiv;
    }

    public Integer getNumFour() {
        return numFour;
    }

    public void setNumFour(Integer numFour) {
        this.numFour = numFour;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numLiv != null ? numLiv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livraison)) {
            return false;
        }
        Livraison other = (Livraison) object;
        if ((this.numLiv == null && other.numLiv != null) || (this.numLiv != null && !this.numLiv.equals(other.numLiv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entites.Livraison[ numLiv=" + numLiv + " ]";
    }
    
}
