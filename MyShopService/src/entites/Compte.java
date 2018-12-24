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
@Table(name = "compte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compte.findAll", query = "SELECT c FROM Compte c")
    , @NamedQuery(name = "Compte.findByIdComp", query = "SELECT c FROM Compte c WHERE c.idComp = :idComp")
    , @NamedQuery(name = "Compte.findByNomComp", query = "SELECT c FROM Compte c WHERE c.nomComp = :nomComp")
    , @NamedQuery(name = "Compte.findByPrenomComp", query = "SELECT c FROM Compte c WHERE c.prenomComp = :prenomComp")
    , @NamedQuery(name = "Compte.findByPseudoComp", query = "SELECT c FROM Compte c WHERE c.pseudoComp = :pseudoComp")
    , @NamedQuery(name = "Compte.findByPseudoCompAndMdpComp", query = "SELECT c FROM Compte c WHERE c.pseudoComp = :pseudoComp AND c.mdpComp = :mdpComp")
    , @NamedQuery(name = "Compte.findByMdpComp", query = "SELECT c FROM Compte c WHERE c.mdpComp = :mdpComp")})
public class Compte implements Serializable {

    @Column(name = "etatComp")
    private String etatComp;

    @Column(name = "idTypComp")
    private Integer idTypComp;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComp")
    private Integer idComp;
    @Column(name = "nomComp")
    private String nomComp;
    @Column(name = "prenomComp")
    private String prenomComp;
    @Column(name = "pseudoComp")
    private String pseudoComp;
    @Column(name = "mdpComp")
    private String mdpComp;

    public Compte() {
    }

    public Compte(Integer idComp) {
        this.idComp = idComp;
    }

    public Integer getIdComp() {
        return idComp;
    }

    public void setIdComp(Integer idComp) {
        this.idComp = idComp;
    }

    public String getNomComp() {
        return nomComp;
    }

    public void setNomComp(String nomComp) {
        this.nomComp = nomComp;
    }

    public String getPrenomComp() {
        return prenomComp;
    }

    public void setPrenomComp(String prenomComp) {
        this.prenomComp = prenomComp;
    }

    public String getPseudoComp() {
        return pseudoComp;
    }

    public void setPseudoComp(String pseudoComp) {
        this.pseudoComp = pseudoComp;
    }

    public String getMdpComp() {
        return mdpComp;
    }

    public void setMdpComp(String mdpComp) {
        this.mdpComp = mdpComp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComp != null ? idComp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compte)) {
            return false;
        }
        Compte other = (Compte) object;
        if ((this.idComp == null && other.idComp != null) || (this.idComp != null && !this.idComp.equals(other.idComp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entites.Compte[ idComp=" + idComp + " ]";
    }

    public Integer getIdTypComp() {
        return idTypComp;
    }

    public void setIdTypComp(Integer idTypComp) {
        this.idTypComp = idTypComp;
    }

    public String getEtatComp() {
        return etatComp;
    }

    public void setEtatComp(String etatComp) {
        this.etatComp = etatComp;
    }

}
