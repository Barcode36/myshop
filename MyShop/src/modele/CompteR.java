/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entites.Compte;
import entites.TypeCompte;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Christ
 */
public class CompteR {

    private SimpleIntegerProperty idComp;
    private SimpleStringProperty nomComp;
    private SimpleStringProperty prenomComp;
    private SimpleStringProperty pseudoComp;
    private SimpleStringProperty mdpComp;
    private SimpleStringProperty libTypComp;
    private SimpleStringProperty etatComp;

    public CompteR(Compte compte, TypeCompte typeCompte) {
        this.idComp = new SimpleIntegerProperty(compte.getIdComp());
        this.nomComp = new SimpleStringProperty(compte.getNomComp());
        this.prenomComp = new SimpleStringProperty(compte.getPrenomComp());
        this.pseudoComp = new SimpleStringProperty(compte.getPseudoComp());
        this.mdpComp = new SimpleStringProperty(compte.getMdpComp());
        this.libTypComp = new SimpleStringProperty(typeCompte.getLibTyp());
        this.etatComp = new SimpleStringProperty(compte.getEtatComp());
    }

    public CompteR(Compte compte) {
        this.idComp = new SimpleIntegerProperty(compte.getIdComp());
        this.nomComp = new SimpleStringProperty(compte.getNomComp());
        this.prenomComp = new SimpleStringProperty(compte.getPrenomComp());
        this.pseudoComp = new SimpleStringProperty(compte.getPseudoComp());
        this.mdpComp = new SimpleStringProperty(compte.getMdpComp());
    }

    public SimpleStringProperty getNomComp() {
        return nomComp;
    }

    public void setNomComp(SimpleStringProperty nomComp) {
        this.nomComp = nomComp;
    }

    public SimpleStringProperty getEtatComp() {
        return etatComp;
    }

    public void setEtatComp(SimpleStringProperty etatComp) {
        this.etatComp = etatComp;
    }

    public SimpleStringProperty getPrenomComp() {
        return prenomComp;
    }

    public void setPrenomComp(SimpleStringProperty prenomComp) {
        this.prenomComp = prenomComp;
    }

    public SimpleStringProperty getPseudoComp() {
        return pseudoComp;
    }

    public void setPseudoComp(SimpleStringProperty pseudoComp) {
        this.pseudoComp = pseudoComp;
    }

    public SimpleStringProperty getMdpComp() {
        return mdpComp;
    }

    public void setMdpComp(SimpleStringProperty mdpComp) {
        this.mdpComp = mdpComp;
    }

    public SimpleIntegerProperty getIdComp() {
        return idComp;
    }

    public void setIdComp(SimpleIntegerProperty idComp) {
        this.idComp = idComp;
    }

    public SimpleStringProperty getLibTypComp() {
        return libTypComp;
    }

    public void setLibTypComp(SimpleStringProperty libTypComp) {
        this.libTypComp = libTypComp;
    }

    @Override
    public String toString() {
        return nomComp.getValue() + " " + prenomComp.getValue();
    }

}
