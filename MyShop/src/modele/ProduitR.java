/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import com.jfoenix.controls.JFXCheckBox;
import entites.Produit;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

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
    private SimpleIntegerProperty qteProdCom;
    private JFXCheckBox Suppression;
    private SimpleStringProperty total;

    public ProduitR(Produit produit) {
        this.idProd = new SimpleIntegerProperty(produit.getIdProd());
        this.libProd = new SimpleStringProperty(produit.getLibProd());
        this.prixUniProd = new SimpleStringProperty(produit.getPrixUniProd());
        this.qteIniProd = new SimpleIntegerProperty(produit.getQteIniProd());
        this.codeProd = new SimpleStringProperty(produit.getCodeProd());

    }

    public ProduitR(Produit produit, ObservableList<ProduitR> produitListVent, TableView<ProduitR> produitCaisseTable,Integer com) {
        this.idProd = new SimpleIntegerProperty(produit.getIdProd());
        this.libProd = new SimpleStringProperty(produit.getLibProd());
        this.prixUniProd = new SimpleStringProperty(produit.getPrixUniProd());
        this.qteProdCom = new SimpleIntegerProperty(com);
        this.codeProd = new SimpleStringProperty(produit.getCodeProd());
        this.total = new SimpleStringProperty(String.valueOf(Integer.parseInt(prixUniProd.getValue()) * qteProdCom.getValue()));
        this.Suppression = new JFXCheckBox("Supprimer");
        this.Suppression.setOnMousePressed(e -> {
            ProduitR pr = produitCaisseTable.getSelectionModel().getSelectedItem();
            // if (pr.getSuppression().isSelected()) {
            produitListVent.remove(pr);
            produitCaisseTable.setItems(produitListVent);
            //}
        });
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

    public JFXCheckBox getSuppression() {
        return Suppression;
    }

    public void setSuppression(JFXCheckBox Suppression) {
        this.Suppression = Suppression;
    }

    public SimpleStringProperty getTotal() {
        return total;
    }

    public void setTotal(SimpleStringProperty total) {
        this.total = total;
    }

    public SimpleIntegerProperty getQteProdCom() {
        return qteProdCom;
    }

    public void setQteProdCom(SimpleIntegerProperty qteProdCom) {
        this.qteProdCom = qteProdCom;
    }

    @Override
    public String toString() {
        return libProd.getValue();
    }

}
