/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Christ
 */
@Embeddable
public class ConcernerPK implements Serializable {

    @Column(name = "numLiv")
    private Integer numLiv;
    @Column(name = "idProd")
    private Integer idProd;

    public ConcernerPK() {
    }

    public ConcernerPK(Integer numLiv, Integer idProd) {
        this.numLiv = numLiv;
        this.idProd = idProd;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.numLiv);
        hash = 61 * hash + Objects.hashCode(this.idProd);
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
        final ConcernerPK other = (ConcernerPK) obj;
        if (!Objects.equals(this.numLiv, other.numLiv)) {
            return false;
        }
        if (!Objects.equals(this.idProd, other.idProd)) {
            return false;
        }
        return true;
    }

    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    public Integer getNumLiv() {
        return numLiv;
    }

    public void setNumLiv(Integer numLiv) {
        this.numLiv = numLiv;
    }

}
