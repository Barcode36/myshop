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
@Table(name = "produit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produit.findAll", query = "SELECT p FROM Produit p WHERE p.etatProd='actif'")
    , @NamedQuery(name = "Produit.findByIdProd", query = "SELECT p FROM Produit p WHERE p.idProd = :idProd")
    , @NamedQuery(name = "Produit.findByCodeProd", query = "SELECT p FROM Produit p WHERE p.codeProd = :codeProd AND p.etatProd='actif'")
    , @NamedQuery(name = "Produit.findByLibProd", query = "SELECT p FROM Produit p WHERE p.libProd = :libProd")
    , @NamedQuery(name = "Produit.findByLibProdLike", query = "SELECT p FROM Produit p WHERE p.etatProd='actif' AND (p.libProd LIKE :libProd OR p.codeProd LIKE :libProd)")
    , @NamedQuery(name = "Produit.findByPrixUniProd", query = "SELECT p FROM Produit p WHERE p.prixUniProd = :prixUniProd")
    , @NamedQuery(name = "Produit.findByQteIniProd", query = "SELECT p FROM Produit p WHERE p.qteIniProd = :qteIniProd")})
public class Produit implements Serializable {

    @Column(name = "qteIniProd")
    private Integer qteIniProd;

    @Column(name = "codeProd")
    private String codeProd;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProd")
    private Integer idProd;
    @Column(name = "libProd")
    private String libProd;
    @Column(name = "etatProd")
    private String etatProd;
    @Column(name = "prixUniProd")
    private String prixUniProd;

    public Produit() {
    }

    public Produit(Integer idProd) {
        this.idProd = idProd;
    }

    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    public String getLibProd() {
        return libProd;
    }

    public String getEtatProd() {
        return etatProd;
    }

    public void setEtatProd(String etatProd) {
        this.etatProd = etatProd;
    }

    public void setLibProd(String libProd) {
        this.libProd = libProd;
    }

    public String getPrixUniProd() {
        return prixUniProd;
    }

    public void setPrixUniProd(String prixUniProd) {
        this.prixUniProd = prixUniProd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProd != null ? idProd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produit)) {
            return false;
        }
        Produit other = (Produit) object;
        if ((this.idProd == null && other.idProd != null) || (this.idProd != null && !this.idProd.equals(other.idProd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entites.Produit[ idProd=" + idProd + " ]";
    }

    public String getCodeProd() {
        return codeProd;
    }

    public void setCodeProd(String codeProd) {
        this.codeProd = codeProd;
    }

    public Integer getQteIniProd() {
        return qteIniProd;
    }

    public void setQteIniProd(Integer qteIniProd) {
        this.qteIniProd = qteIniProd;
    }

}
