/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c WHERE c.etatClt='actif'")
    , @NamedQuery(name = "Client.findByIdClt", query = "SELECT c FROM Client c WHERE c.idClt = :idClt")
    , @NamedQuery(name = "Client.findByRec", query = "SELECT c FROM Client c WHERE c.etatClt='actif' AND (c.nomClt LIKE :nomClt OR c.numClt LIKE :nomClt)")
    , @NamedQuery(name = "Client.findByNomClt", query = "SELECT c FROM Client c WHERE c.nomClt = :nomClt AND c.etatClt='actif'")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClt")
    private Integer idClt;
    @Column(name = "nomClt")
    private String nomClt;
    @Column(name = "adrClt")
    private String adrClt;
    @Column(name = "etatClt")
    private String etatClt;
    @Column(name = "numClt")
    private String numClt;

    public Client() {
    }

    public Client(Integer idClt) {
        this.idClt = idClt;
    }

    public Integer getIdClt() {
        return idClt;
    }

    public String getNumClt() {
        return numClt;
    }

    public void setNumClt(String numClt) {
        this.numClt = numClt;
    }

    public String getEtatClt() {
        return etatClt;
    }

    public void setEtatClt(String etatClt) {
        this.etatClt = etatClt;
    }

    public void setIdClt(Integer idClt) {
        this.idClt = idClt;
    }

    public String getNomClt() {
        return nomClt;
    }

    public void setNomClt(String nomClt) {
        this.nomClt = nomClt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClt != null ? idClt.hashCode() : 0);
        return hash;
    }

    public String getAdrClt() {
        return adrClt;
    }

    public void setAdrClt(String adrClt) {
        this.adrClt = adrClt;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.idClt == null && other.idClt != null) || (this.idClt != null && !this.idClt.equals(other.idClt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entites.Client[ idClt=" + idClt + " ]";
    }

}
