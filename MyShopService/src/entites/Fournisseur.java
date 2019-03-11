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
@Table(name = "fournisseur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fournisseur.findAll", query = "SELECT f FROM Fournisseur f")
    , @NamedQuery(name = "Fournisseur.findByIdFour", query = "SELECT f FROM Fournisseur f WHERE f.idFour = :idFour")
    , @NamedQuery(name = "Fournisseur.findByNomFour", query = "SELECT f FROM Fournisseur f WHERE f.nomFour = :nomFour")})
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idFour")
    private Integer idFour;
    @Column(name = "nomFour")
    private String nomFour;

    public Fournisseur() {
    }

    public Fournisseur(Integer idFour) {
        this.idFour = idFour;
    }

    public Integer getIdFour() {
        return idFour;
    }

    public void setIdFour(Integer idFour) {
        this.idFour = idFour;
    }

    public String getNomFour() {
        return nomFour;
    }

    public void setNomFour(String nomFour) {
        this.nomFour = nomFour;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFour != null ? idFour.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fournisseur)) {
            return false;
        }
        Fournisseur other = (Fournisseur) object;
        if ((this.idFour == null && other.idFour != null) || (this.idFour != null && !this.idFour.equals(other.idFour))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entites.Fournisseur[ idFour=" + idFour + " ]";
    }
    
}
