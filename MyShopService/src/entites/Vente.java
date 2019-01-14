/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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
@Table(name = "vente")@XmlRootElement
@NamedQueries({

    @NamedQuery(name = "Vente.findAll", query = "SELECT v FROM Vente v")
    , @NamedQuery(name = "Vente.findByIdComp", query = "SELECT v FROM Vente v WHERE v.idComp = :idComp ORDER BY v.dateVen")
    , @NamedQuery(name = "Vente.findByIdClt", query = "SELECT v FROM Vente v WHERE v.idClt = :idClt")
    , @NamedQuery(name = "Vente.findByMoisEnCours", query = "SELECT v FROM Vente v WHERE FUNCTION('MONTH',v.ventePK.dateVen)= :mois AND FUNCTION('YEAR',v.ventePK.dateVen) = :year")
    , @NamedQuery(name = "Vente.findByDateVen", query = "SELECT v FROM Vente v WHERE FUNCTION('MONTH',v.ventePK.dateVen) = :mois")
    , @NamedQuery(name = "Vente.findByDateVenMois", query = "SELECT v FROM Vente v WHERE FUNCTION('MONTH',v.ventePK.dateVen) = :mois")})
public class Vente implements Serializable {

    @Basic(optional = false)
    @Column(name = "idComp")
    private Integer idComp;
    @Basic(optional = false)
    @Column(name = "idClt")
    private Integer idClt;
    @Column(name = "dateVen",columnDefinition = "Date")
    private Date dateVen;
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idVen")
    private Integer idVen;

    private static final long serialVersionUID = 1L;
//    @Column(name = "idComp")
//    private Integer idComp;
//    @Column(name = "idProd")
//    private Integer idProd;
//    @Column(name = "idClt")
//    private Integer idClt;
//    @Id
//    @Column(name = "dateVen")
//    private String dateVen;
    

    public Vente() {
    }

//    public Vente(String dateVen) {
//        this.dateVen = dateVen;
//    }
//
//    public Integer getIdComp() {
//        return idComp;
//    }
//
//    public void setIdComp(Integer idComp) {
//        this.idComp = idComp;
//    }
//
//    public Integer getIdProd() {
//        return idProd;
//    }
//
//    public void setIdProd(Integer idProd) {
//        this.idProd = idProd;
//    }
//
//    public Integer getIdClt() {
//        return idClt;
//    }
//
//    public void setIdClt(Integer idClt) {
//        this.idClt = idClt;
//    }
//
//    public String getDateVen() {
//        return dateVen;
//    }
//
//    public void setDateVen(String dateVen) {
//        this.dateVen = dateVen;
//    }

    public Vente(Integer idVen) {
        this.idVen = idVen;
    }

    public Vente(Integer idVen, Integer idComp, Integer idClt) {
        this.idVen = idVen;
        this.idComp = idComp;
        this.idClt = idClt;
    }

    public Integer getIdComp() {
        return idComp;
    }

    public void setIdComp(Integer idComp) {
        this.idComp = idComp;
    }

    public Integer getIdClt() {
        return idClt;
    }

    public void setIdClt(Integer idClt) {
        this.idClt = idClt;
    }

    public Date getDateVen() {
        return dateVen;
    }

    public void setDateVen(Date dateVen) {
        this.dateVen = dateVen;
    }

    public Integer getIdVen() {
        return idVen;
    }

    public void setIdVen(Integer idVen) {
        this.idVen = idVen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVen != null ? idVen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vente)) {
            return false;
        }
        Vente other = (Vente) object;
        if ((this.idVen == null && other.idVen != null) || (this.idVen != null && !this.idVen.equals(other.idVen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entites.Vente[ idVen=" + idVen + " ]";
    }

}
