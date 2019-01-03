/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "concerner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Concerner.findAll", query = "SELECT c FROM Concerner c")
    , @NamedQuery(name = "Concerner.findByIdProd", query = "SELECT c FROM Concerner c WHERE c.concernerPK.idProd = :idProd")
    , @NamedQuery(name = "Concerner.findByNumLiv", query = "SELECT c FROM Concerner c WHERE c.concernerPK.numLiv = :numLiv")
    , @NamedQuery(name = "Concerner.findByQteLiv", query = "SELECT c FROM Concerner c WHERE c.qteLiv = :qteLiv")})
public class Concerner implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ConcernerPK concernerPK;
    @Column(name = "qteLiv")
    private Integer qteLiv;

    public Concerner() {
    }

    public ConcernerPK getConcernerPK() {
        return concernerPK;
    }

    public void setConcernerPK(ConcernerPK concernerPK) {
        this.concernerPK = concernerPK;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.concernerPK);
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
        final Concerner other = (Concerner) obj;
        if (!Objects.equals(this.concernerPK, other.concernerPK)) {
            return false;
        }
        return true;
    }

    public Integer getQteLiv() {
        return qteLiv;
    }

    public void setQteLiv(Integer qteLiv) {
        this.qteLiv = qteLiv;
    }

}
