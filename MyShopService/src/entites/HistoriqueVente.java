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
@Table(name = "historiqueVente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoriqueVente.findAll", query = "SELECT hv FROM HistoriqueVente hv")
    , @NamedQuery(name = "HistoriqueVente.findBySmeRecue", query = "SELECT hv FROM HistoriqueVente hv WHERE hv.smeRecue = :smeRecue")
    , @NamedQuery(name = "HistoriqueVente.findBySmeRendue", query = "SELECT hv FROM HistoriqueVente hv WHERE hv.smeRendue = :smeRendue")
    
    , @NamedQuery(name = "HistoriqueVente.findByIdVen",
                 query = "SELECT cv.idVen, cp.pseudoComp, cv.dtVente, c.nomClt, c.numClt, hv.smeRecue, hv.smeRendue "
                + " FROM ContenirVente cv, Client c, Vente v, Compte cp, HistoriqueVente hv "
                + " WHERE cv.idVen = :idVen "
                + " and cv.idVen = v.idVen and c.idClt = v.idClt and v.idComp = cp.idComp and hv.idCon = cv.idCon "
                
    )

})

public class HistoriqueVente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHis")
    private Long idHis;

    @Basic(optional = false)
    @Column(name = "idCon")
    private Long idCon;
    @Basic(optional = false)
    @Column(name = "smeRecue")
    private Integer smeRecue;
    @Basic(optional = false)
    @Column(name = "smeRendue")
    private Integer smeRendue;
    
    
    public HistoriqueVente() {
    }

    public Long getIdHis() {
        return idHis;
    }

    public void setIdHis(Long idHis) {
        this.idHis = idHis;
    }

    public Long getIdCon() {
        return idCon;
    }

    public void setIdCon(Long idCon) {
        this.idCon = idCon;
    }

    public double getSmeRecue() {
        return smeRecue;
    }

    public void setSmeRecue(Integer smeRecue) {
        this.smeRecue = smeRecue;
    }

    public double getSmeRendue() {
        return smeRendue;
    }

    public void setSmeRendue(Integer smeRendue) {
        this.smeRendue = smeRendue;
    }

    
    

}
