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
@Table(name = "typeCompte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeCompte.findAll", query = "SELECT t FROM TypeCompte t")
    , @NamedQuery(name = "TypeCompte.findByIdTyp", query = "SELECT t FROM TypeCompte t WHERE t.idTyp = :idTyp")
    , @NamedQuery(name = "TypeCompte.findByLibTyp", query = "SELECT t FROM TypeCompte t WHERE t.libTyp = :libTyp")})
public class TypeCompte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTyp")
    private Integer idTyp;
    @Column(name = "libTyp")
    private String libTyp;

    public TypeCompte() {
    }

    public TypeCompte(Integer idTyp) {
        this.idTyp = idTyp;
    }

    public Integer getIdTyp() {
        return idTyp;
    }

    public void setIdTyp(Integer idTyp) {
        this.idTyp = idTyp;
    }

    public String getLibTyp() {
        return libTyp;
    }

    public void setLibTyp(String libTyp) {
        this.libTyp = libTyp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTyp != null ? idTyp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeCompte)) {
            return false;
        }
        TypeCompte other = (TypeCompte) object;
        if ((this.idTyp == null && other.idTyp != null) || (this.idTyp != null && !this.idTyp.equals(other.idTyp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entites.TypeCompte[ idTyp=" + idTyp + " ]";
    }

}
