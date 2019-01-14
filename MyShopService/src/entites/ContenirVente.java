/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "contenirVente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContenirVente.findAll", query = "SELECT c FROM ContenirVente c")
    , @NamedQuery(name = "ContenirVente.findByIdProd", query = "SELECT c FROM ContenirVente c WHERE c.contenirVentePK.idProd = :idProd")
    , @NamedQuery(name = "ContenirVente.findByQteVen", query = "SELECT c FROM ContenirVente c WHERE c.qteVen = :qteVen")
    , @NamedQuery(name = "ContenirVente.findByIdVen", query = "SELECT c FROM ContenirVente c WHERE c.contenirVentePK.idVen = :idVen")})
public class ContenirVente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "qteVen")
    private int qteVen;
    @EmbeddedId
    private ContenirVentePK contenirVentePK;

    public ContenirVente() {
    }

    public ContenirVente(ContenirVentePK contenirVentePK) {
        this.contenirVentePK = contenirVentePK;
    }

    public int getQteVen() {
        return qteVen;
    }

    public ContenirVentePK getContenirVentePK() {
        return contenirVentePK;
    }

    public void setContenirVentePK(ContenirVentePK contenirVentePK) {
        this.contenirVentePK = contenirVentePK;
    }

    public void setQteVen(int qteVen) {
        this.qteVen = qteVen;
    }

    @Override
    public String toString() {
        return String.valueOf(qteVen);
    }

}
