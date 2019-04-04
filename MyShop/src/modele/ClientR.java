/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entites.Client;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Christ
 */
public class ClientR {

    private SimpleIntegerProperty idClt;
    private SimpleStringProperty nomClt;
    private SimpleStringProperty adrClt;

    public ClientR() {
    }

    public ClientR(Client c) {
        this.idClt = new SimpleIntegerProperty(c.getIdClt());
        this.nomClt = new SimpleStringProperty(c.getNomClt());
        this.adrClt = new SimpleStringProperty(c.getAdrClt());
    }

    public SimpleIntegerProperty getIdClt() {
        return idClt;
    }

    public void setIdClt(SimpleIntegerProperty idClt) {
        this.idClt = idClt;
    }

    public SimpleStringProperty getNomClt() {
        return nomClt;
    }

    public void setNomClt(SimpleStringProperty nomClt) {
        this.nomClt = nomClt;
    }

    public SimpleStringProperty getAdrClt() {
        return adrClt;
    }

    public void setAdrClt(SimpleStringProperty adrClt) {
        this.adrClt = adrClt;
    }

    @Override
    public String toString() {
        return nomClt.getValue();
    }

}
