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
@Table(name = "contenirVente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContenirVente.findAll", query = "SELECT c FROM ContenirVente c")
    , @NamedQuery(name = "ContenirVente.findByQteVen", query = "SELECT c FROM ContenirVente c WHERE c.qteVen = :qteVen")
    , @NamedQuery(name = "ContenirVente.findByIdProd", query = "SELECT c FROM ContenirVente c WHERE c.idProd = :idProd")
    , @NamedQuery(name = "ContenirVente.findByIdVen", query = "SELECT c FROM ContenirVente c WHERE c.idVen = :idVen")})
public class ContenirVente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "qteVen")
    private int qteVen;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCon")
    private Long idCon;

    @Basic(optional = false)
    @Column(name = "idVen")
    private int idVen;
    @Basic(optional = false)
    @Column(name = "idProd")
    private int idProd;
    @Basic(optional = false)
    @Column(name = "prixProd")
    private int prixProd;
    @Basic(optional = false)
    @Column(name = "montVente")
    private double montVente;
    @Column(name = "dtVente",columnDefinition = "Date")
    private Date dtVente;

    public ContenirVente() {
    }

    public int getQteVen() {
        return qteVen;
    }

    public void setQteVen(int qteVen) {
        this.qteVen = qteVen;
    }

    @Override
    public String toString() {
        return String.valueOf(qteVen);
    }

    public Long getIdCon() {
        return idCon;
    }

    public void setIdCon(Long idCon) {
        this.idCon = idCon;
    }

    public int getIdVen() {
        return idVen;
    }

    public void setIdVen(int idVen) {
        this.idVen = idVen;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public int getPrixProd() {
        return prixProd;
    }

    public void setPrixProd(int prixProd) {
        this.prixProd = prixProd;
    }

    public double getMontVente() {
        return montVente;
    }

    public void setMontVente(double montVente) {
        this.montVente = montVente;
    }

    public Date getDtVente() {
        return dtVente;
    }

    public void setDtVente(Date dtVente) {
        this.dtVente = dtVente;
    }
    
    

}
