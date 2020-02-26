/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entites.TypeCompte;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Christ
 */
public class TypeCompteR {

    private SimpleIntegerProperty idTyp;
    private SimpleStringProperty libTyp;

    public TypeCompteR(TypeCompte typeCompte) {
        this.idTyp = new SimpleIntegerProperty(typeCompte.getIdTyp());
        this.libTyp = new SimpleStringProperty(typeCompte.getLibTyp());
    }

    public SimpleIntegerProperty getIdTyp() {
        return idTyp;
    }

    public void setIdTyp(SimpleIntegerProperty idTyp) {
        this.idTyp = idTyp;
    }

    public SimpleStringProperty getLibTyp() {
        return libTyp;
    }

    public void setLibTyp(SimpleStringProperty libTyp) {
        this.libTyp = libTyp;
    }

    @Override
    public String toString() {
        return libTyp.getValue();
    }

}
