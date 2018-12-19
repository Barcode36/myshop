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
    , @NamedQuery(name = "Vente.findByIdComp", query = "SELECT v FROM Vente v WHERE v.ventePK.idComp = :idComp")
    , @NamedQuery(name = "Vente.findByIdProd", query = "SELECT v FROM Vente v WHERE v.ventePK.idProd = :idProd")
    , @NamedQuery(name = "Vente.findByIdClt", query = "SELECT v FROM Vente v WHERE v.ventePK.idClt = :idClt")
    , @NamedQuery(name = "Vente.findByDateVen", query = "SELECT v FROM Vente v WHERE v.ventePK.dateVen = :dateVen")
    , @NamedQuery(name = "Vente.findByQteVen", query = "SELECT v FROM Vente v WHERE v.qteVen = :qteVen")})
public class Vente implements Serializable {

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
    @EmbeddedId
    protected VentePK ventePK;
    @Column(name = "qteVen")
    private Integer qteVen;

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
    public Integer getQteVen() {
        return qteVen;
    }

    public void setQteVen(Integer qteVen) {
        this.qteVen = qteVen;
    }

    public VentePK getVentePK() {
        return ventePK;
    }

    public void setVentePK(VentePK ventePK) {
        this.ventePK = ventePK;
    }

}
