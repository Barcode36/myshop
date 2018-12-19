/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entites.Produit;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Christ
 */
public class ProduitR {

    private SimpleIntegerProperty idProd;
    private SimpleStringProperty codeProd;
    private SimpleStringProperty libProd;
    private SimpleStringProperty prixUniProd;
    private SimpleIntegerProperty qteIniProd;

    public ProduitR(Produit produit) {
        this.idProd = new SimpleIntegerProperty(produit.getIdProd());
        this.libProd = new SimpleStringProperty(produit.getLibProd());
        this.prixUniProd = new SimpleStringProperty(produit.getPrixUniProd());
        this.qteIniProd = new SimpleIntegerProperty(produit.getQteIniProd());
        this.codeProd = new SimpleStringProperty(produit.getCodeProd());
    }

    public SimpleIntegerProperty getIdProd() {
        return idProd;
    }

    public void setIdProd(SimpleIntegerProperty idProd) {
        this.idProd = idProd;
    }

    public SimpleStringProperty getLibProd() {
        return libProd;
    }

    public void setLibProd(SimpleStringProperty libProd) {
        this.libProd = libProd;
    }

    public SimpleStringProperty getPrixUniProd() {
        return prixUniProd;
    }

    public void setPrixUniProd(SimpleStringProperty prixUniProd) {
        this.prixUniProd = prixUniProd;
    }

    public SimpleIntegerProperty getQteIniProd() {
        return qteIniProd;
    }

    public void setQteIniProd(SimpleIntegerProperty qteIniProd) {
        this.qteIniProd = qteIniProd;
    }

    public SimpleStringProperty getCodeProd() {
        return codeProd;
    }

    public void setCodeProd(SimpleStringProperty codeProd) {
        this.codeProd = codeProd;
    }

    @Override
    public String toString() {
        return libProd.getValue();
    }

}
