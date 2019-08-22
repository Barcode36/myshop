/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entites.Client;
import entites.Compte;
import entites.Produit;
import entites.Vente;
import java.util.Date;
import javafx.beans.property.SimpleDoubleProperty;
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
    private SimpleDoubleProperty montVente;
    private SimpleStringProperty client;
     private SimpleStringProperty dtVte;

    public VenteR(Compte compte,Vente vente, Integer totVent) {
        this.caissier = new SimpleStringProperty(compte.getNomComp() + compte.getPrenomComp());
        this.totalCaissier = new SimpleIntegerProperty(totVent);
        this.idCompte = new SimpleIntegerProperty(compte.getIdComp());
        this.idVen = new SimpleIntegerProperty(vente.getIdVen());
    }
    
    public VenteR(Compte compte,Vente vente, Double montVente,Client c, Date dte) {
        this.caissier = new SimpleStringProperty(compte.getNomComp() + compte.getPrenomComp());
        this.totalCaissier = new SimpleIntegerProperty(0);
        this.idCompte = new SimpleIntegerProperty(compte.getIdComp());
        this.idVen = new SimpleIntegerProperty(vente.getIdVen());
        this.montVente = new SimpleDoubleProperty(montVente);
        this.client = new SimpleStringProperty(c.getNomClt());
        this.dtVte = new SimpleStringProperty(dte.toString());
    }
    
    public VenteR(Compte compte, Integer totVent) {
        this.caissier = new SimpleStringProperty(compte.getNomComp()+" "
                + "" + compte.getPrenomComp());
        this.totalCaissier = new SimpleIntegerProperty(totVent);
        this.idCompte = new SimpleIntegerProperty(compte.getIdComp());
    }
    
    

    /*public VenteR(Vente vt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

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

    public SimpleDoubleProperty getMontVente() {
        return montVente;
    }

    public void setMontVente(SimpleDoubleProperty montVente) {
        this.montVente = montVente;
    }

    public SimpleStringProperty getClient() {
        return client;
    }

    public void setClient(SimpleStringProperty client) {
        this.client = client;
    }

    public SimpleStringProperty getDtVte() {
        return dtVte;
    }

    public void setDtVte(SimpleStringProperty dtVte) {
        this.dtVte = dtVte;
    }

    
    
}
