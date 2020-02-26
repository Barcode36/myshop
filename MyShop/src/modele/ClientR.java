/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entites.Client;
import javafx.beans.property.SimpleDoubleProperty;
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
    private SimpleStringProperty numClt;
     //ajout du nbre de points - bes
    private SimpleDoubleProperty nbPoints;

    public ClientR() {
    }

    public ClientR(Client c) {
        this.idClt = new SimpleIntegerProperty(c.getIdClt());
        this.nomClt = new SimpleStringProperty(c.getNomClt());
        this.adrClt = new SimpleStringProperty(c.getAdrClt());
        this.numClt = new SimpleStringProperty(c.getNumClt());
         //ajout du nbre de points - bes
        this.nbPoints = new SimpleDoubleProperty(c.getNbPoints());
    }

    public SimpleIntegerProperty getIdClt() {
        return idClt;
    }

    public SimpleStringProperty getNumClt() {
        return numClt;
    }

    public void setNumClt(SimpleStringProperty numClt) {
        this.numClt = numClt;
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

    //ajout du nbre de points - bes
    public SimpleDoubleProperty getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(SimpleDoubleProperty nbPoints) {
        this.nbPoints = nbPoints;
    }

    
    @Override
    public String toString() {
        return nomClt.getValue();
    }

}
