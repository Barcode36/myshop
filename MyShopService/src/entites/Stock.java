/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stock.findStocks", query = "SELECT s.codeStock,s.coutAchatUni,s.qteIniSTock, s.qteActuStock,s.Fournisseur,s.idStock FROM Stock s where s.idProd = :idProd order by s.idStock desc")
    , @NamedQuery(name = "Stock.findByIdProd", query = "SELECT s FROM Stock s WHERE s.idStock = :idStock")
    , @NamedQuery(name = "Stock.findByIdProdMme", query = "SELECT s FROM Stock s WHERE s.idProd = :idProd")
    , @NamedQuery(name = "Stock.findByCodeStock", query = "SELECT s FROM Stock s WHERE s.codeStock = :codeStock ")    
    , @NamedQuery(name = "Stock.findByRec", query = "SELECT s FROM Stock s WHERE (s.codeStock LIKE :codeStock OR s.dateRavi LIKE :codeStock OR s.Fournisseur LIKE :codeStock)")

})


public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idStock")
    private Integer idStock;
    @Column(name = "idProd")
    private Integer idProd;
    @Column(name = "codeStock")
    private String codeStock;
    @Column(name = "coutAchatUni")
    private Integer coutAchatUni;
    @Column(name = "qteIniSTock")
    private Integer qteIniSTock;
     @Column(name = "qteActuStock")
    private Integer qteActuStock;
    @Column(name = "dateRavi")
    private Date dateRavi;
    @Column(name = "dateExpi")
    private Date dateExpi;
     @Column(name = "Fournisseur")
    private String Fournisseur;
   
    
    public Stock() {
    }

    

    public Stock(int idStock) {
        this.idStock = idStock;
    }

    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    public String getCodeStock() {
        return codeStock;
    }

    public void setCodeStock(String codeStock) {
        this.codeStock = codeStock;
    }

    public Integer getCoutAchatUni() {
        return coutAchatUni;
    }

    public void setCoutAchatUni(Integer coutAchatUni) {
        this.coutAchatUni = coutAchatUni;
    }

    public Integer getQteIniSTock() {
        return qteIniSTock;
    }

    public void setQteIniSTock(Integer qteIniSTock) {
        this.qteIniSTock = qteIniSTock;
    }

    public Integer getQteActuStock() {
        return qteActuStock;
    }

    public void setQteActuStock(Integer qteActuStock) {
        this.qteActuStock = qteActuStock;
    }

    public Date getDateRavi() {
        return dateRavi;
    }

    public void setDateRavi(Date dateRavi) {
        this.dateRavi = dateRavi;
    }

    public Date getDateExpi() {
        return dateExpi;
    }

    public void setDateExpi(Date dateExpi) {
        this.dateExpi = dateExpi;
    }

    public String getFournisseur() {
        return Fournisseur;
    }

    public void setFournisseur(String Fournisseur) {
        this.Fournisseur = Fournisseur;
    }

    

}
