/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

/**
 *
 * @author Christ
 */
@Embeddable
public class ContenirVentePK implements Serializable {

    @Column(name = "idVen")
    private Integer idVen;
    @Column(name = "idProd")
    private Integer idProd;

    public ContenirVentePK() {
    }

    public ContenirVentePK(Integer idVen, Integer idProd) {
        this.idVen = idVen;
        this.idProd = idProd;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.idVen);
        hash = 17 * hash + Objects.hashCode(this.idProd);
        return hash;
    }

    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    public Integer getIdVen() {
        return idVen;
    }

    public void setIdVen(Integer idVen) {
        this.idVen = idVen;
    }

}
