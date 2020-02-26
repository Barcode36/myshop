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
    , @NamedQuery(name = "ContenirVente.findByIdVen", query = "SELECT c FROM ContenirVente c WHERE c.idVen = :idVen")
   // , @NamedQuery(name = "ContenirVente.findByIdCon", query = "SELECT c.idCon FROM ContenirVente c WHERE c.idCon = :idCon")

    , @NamedQuery(name = "ContenirVente.listMieuxVen", query = "SELECT p.libProd as nomProd,p.idProd ,sum(c.qteVen)"
            + " as qteVendue,"
            + "(sum(c.qteVen)  *  c.prixProd) as montantVente "
            + "FROM ContenirVente c , Produit p  WHERE p.idProd = c.idProd "
            + "GROUP BY c.idProd ORDER BY qteVendue"
            + " DESC "
            //+ "LIMIT 10"
            )
        
    , @NamedQuery(name = "ContenirVente.listMieuxVenByDate", query = "SELECT p.libProd ,sum(c.qteVen) as qteVendue "
       + "FROM ContenirVente c , Produit p, Vente v  WHERE p.idProd = c.idProd "
       + "AND v.dateVen >= :dateVen AND v.dateVen <= :dateVen2 "
       + "AND v.idVen = c.idVen "
       + "GROUP BY c.idProd ORDER BY qteVendue"
       + " DESC "
       //+ "LIMIT 10"
       )
        
    , @NamedQuery(name = "ContenirVente.listEnFinition", query = "SELECT p.libProd, p.qteIniProd "
            + "FROM Produit p"
            + " ORDER BY p.qteIniProd ASC")
        
    , @NamedQuery(name = "ContenirVente.findVenteByPeriode", query = "SELECT c.idProd,sum(c.qteVen) AS qteVendue FROM"
            + " ContenirVente c, Vente v WHERE c.idVen = v.idVen and v.dateVen >= :dateVen "
            + " GROUP BY c.idProd ORDER by qteVendue DESC")
        
    , @NamedQuery(name = "ContenirVente.historiqueVente", query = "SELECT  p.libProd, cv.qteVen, "
            + " cv.prixProd,cv.qteVen * cv.prixProd as mtVte,v.dateVen, c.nomClt,cv.idCon "
            + " FROM Client c, Vente v, ContenirVente cv, Produit p  "
            + "where p.idProd = cv.idProd and v.idVen = cv.idVen "
            + " and v.idClt = c.idClt and v.idComp = :idComp " 
            + "and v.dateVen >= :dateVen and v.dateVen <= :dateVen2")
        
    , @NamedQuery(name = "ContenirVente.findTotVteEffectueByPeriode",
            query = "SELECT c.idComp, c.nomComp, SUM(cv.qteVen*cv.prixProd)"
                + " FROM Vente v, ContenirVente cv, Compte c WHERE c.idComp = v.idComp "
                + " AND cv.idVen = v.idVen AND v.dateVen <= :dateVen GROUP BY c.idComp")

    , @NamedQuery(name = "ContenirVente.findTotVteEffectueByTwoPeriode",
            query = "SELECT c.idComp, c.nomComp, c.prenomComp , SUM(cv.qteVen*cv.prixProd),SUM(cv.qteVen)"
                + " FROM Vente v, ContenirVente cv, Compte c WHERE c.idComp = v.idComp "
                + " AND cv.idVen = v.idVen AND v.dateVen >= :dateVen1 AND v.dateVen <= :dateVen2 GROUP BY c.idComp")

    , @NamedQuery(name = "ContenirVente.findTotQteVendueByTwoPeriode",
           query = "SELECT v.idComp, c.nomComp,sum(cv.qteVen)"
                + " FROM ContenirVente cv, Vente v, Compte c "
                + " WHERE cv.idVen = v.idVen and c.idComp = v.idComp and v.dateVen >= :dateVen1"
                + "  and v.dateVen <= :dateVen2 and v.idComp = :idComp "
                + " group by v.idComp")

    , @NamedQuery(name = "ContenirVente.findAllVteDesc",
         query = "SELECT cv.idVen, cv.montVente, c.nomClt, c.numClt, cp.pseudoComp, cv.dtVente"
                + " FROM ContenirVente cv, Client c, Vente v, Compte cp "
                + " WHERE cv.idVen = v.idVen and c.idClt = v.idClt and v.idComp = cp.idComp "
                + " group by cv.idVen"
                + " order by cv.idVen desc")
        
    , @NamedQuery(name = "ContenirVente.findDetailsVte",
        query = "SELECT p.libProd, cv.prixProd, cv.qteVen, (cv.prixProd * cv.qteVen) "
                + " FROM ContenirVente cv, Produit p"
                + " WHERE cv.idVen = :idVen and cv.idProd = p.idProd "
                )
        
    , @NamedQuery(name = "ContenirVente.findVenteByLike", 
            query = "SELECT cv.idVen, cv.montVente, c.nomClt, c.numClt, cp.pseudoComp, cv.dtVente"
                + " FROM ContenirVente cv, Client c, Vente v, Compte cp "
                + " WHERE cv.idVen = :idVenD "
                + " and cv.idVen = v.idVen and c.idClt = v.idClt and v.idComp = cp.idComp "
                + " group by cv.idVen"
                + " order by cv.idVen desc"
    )



        /*
        select cv.idVen,  cv.prixProd, cv.montVente,cv.qteVen, cv.dtVente, p.libProd,  c.nomClt, c.numClt, cp.pseudoComp
from contenirVente cv, produit p, client c, vente v, compte cp
where cv.idVen = v.idVen 
and cv.idProd = p.idProd
and v.idClt = c.idClt
and v.idComp = cp.idComp 
order by cv.idVen desc
 ;
    
       select cv.idVen, cv.montVente, cv.dtVente,  c.nomClt, c.numClt, cp.pseudoComp
from contenirVente cv, client c, vente v, compte cp
where cv.idVen = v.idVen 

and v.idClt = c.idClt
and v.idComp = cp.idComp 
group by cv.idVen 
order by cv.idVen desc

        select idProd, prixProd, qteVen
from contenirVente
where idVen = 7151 ;
        
select v.idComp, c.nomComp,sum(cv.qteVen) from contenirVente cv, vente v, compte c
where cv.idVen = v.idVen and c.idComp = v.idComp and v.dateVen > 1557839867953  and v.dateVen < 1566316779391
 group by v.idComp        */
})
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
