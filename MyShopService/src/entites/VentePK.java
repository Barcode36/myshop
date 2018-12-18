/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 *
 * @author Christ
 */
@Embeddable
public class VentePK implements Serializable{
    @Column(name = "idComp")
    private Integer idComp;
    @Column(name = "idProd")
    private Integer idProd;
    @Column(name = "idClt")
    private Integer idClt;

    public VentePK(Integer idComp, Integer idProd, Integer idClt) {
        this.idComp = idComp;
        this.idProd = idProd;
        this.idClt = idClt;
    }

    public VentePK() {
    }

    public Integer getIdComp() {
        return idComp;
    }

    public void setIdComp(Integer idComp) {
        this.idComp = idComp;
    }

    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    public Integer getIdClt() {
        return idClt;
    }

    public void setIdClt(Integer idClt) {
        this.idClt = idClt;
    }
    
    
}
