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
public class VentePK implements Serializable {

    @Column(name = "idComp")
    private Integer idComp;
    @Column(name = "idProd")
    private Integer idProd;
    @Column(name = "idClt")
    private Integer idClt;

    @Column(name = "dateVen", columnDefinition = "Date")
    private Date dateVen;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.idComp);
        hash = 17 * hash + Objects.hashCode(this.idProd);
        hash = 17 * hash + Objects.hashCode(this.idClt);
        hash = 17 * hash + Objects.hashCode(this.dateVen);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VentePK other = (VentePK) obj;
        if (!Objects.equals(this.dateVen, other.dateVen)) {
            return false;
        }
        if (!Objects.equals(this.idComp, other.idComp)) {
            return false;
        }
        if (!Objects.equals(this.idProd, other.idProd)) {
            return false;
        }
        if (!Objects.equals(this.idClt, other.idClt)) {
            return false;
        }
        return true;
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

    public Date getDateVen() {
        return dateVen;
    }

    public void setDateVen(Date dateVen) {
        this.dateVen = dateVen;
    }

}
