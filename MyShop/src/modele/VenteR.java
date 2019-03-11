/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entites.Compte;
import entites.Produit;
import entites.Vente;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Christ
 */
public class VenteR {

    private SimpleStringProperty caissier;
    private SimpleIntegerProperty totalCaissier;
    private SimpleIntegerProperty idCompte;
    private SimpleIntegerProperty idVen;

    public VenteR(Compte compte,Vente vente, Integer totVent) {
        this.caissier = new SimpleStringProperty(compte.getNomComp() + compte.getPrenomComp());
        this.totalCaissier = new SimpleIntegerProperty(totVent);
        this.idCompte = new SimpleIntegerProperty(compte.getIdComp());
        this.idVen = new SimpleIntegerProperty(vente.getIdVen());
    }
    public VenteR(Compte compte, Integer totVent) {
        this.caissier = new SimpleStringProperty(compte.getNomComp() + compte.getPrenomComp());
        this.totalCaissier = new SimpleIntegerProperty(totVent);
        this.idCompte = new SimpleIntegerProperty(compte.getIdComp());
    }

    public SimpleStringProperty getCaissier() {
        return caissier;
    }

    public void setCaissier(SimpleStringProperty caissier) {
        this.caissier = caissier;
    }

    public SimpleIntegerProperty getTotalCaissier() {
        return totalCaissier;
    }

    public void setTotalCaissier(SimpleIntegerProperty totalCaissier) {
        this.totalCaissier = totalCaissier;
    }

    public SimpleIntegerProperty getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(SimpleIntegerProperty idCompte) {
        this.idCompte = idCompte;
    }

    public SimpleIntegerProperty getIdVen() {
        return idVen;
    }

    public void setIdVen(SimpleIntegerProperty idVen) {
        this.idVen = idVen;
    }

    public SimpleIntegerProperty getidCompte() {
        return idCompte;
    }

    public void setidCompte(SimpleIntegerProperty idCompte) {
        this.idCompte = idCompte;
    }

}
