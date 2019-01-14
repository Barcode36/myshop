/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import entites.ContenirVente;
import entites.ContenirVentePK;
import entites.Produit;
import entites.Vente;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private SimpleStringProperty dateVen;
    private JFXCheckBox Suppression;
    private SimpleStringProperty total;
    private JFXTextField qteCom;

    public ProduitR(Produit produit) {
        this.idProd = new SimpleIntegerProperty(produit.getIdProd());
        this.libProd = new SimpleStringProperty(produit.getLibProd());
        this.prixUniProd = new SimpleStringProperty(produit.getPrixUniProd());
        this.qteIniProd = new SimpleIntegerProperty(produit.getQteIniProd());
        this.codeProd = new SimpleStringProperty(produit.getCodeProd());

    }

    public ProduitR(Produit produit, Vente vente, ContenirVente contenirVente) {
        this.idProd = new SimpleIntegerProperty(produit.getIdProd());
        DateFormat dateFormat = new SimpleDateFormat("d-MM-yyy");
        String dateh = dateFormat.format(vente.getDateVen()).toString();
        this.dateVen = new SimpleStringProperty(dateh);
        this.libProd = new SimpleStringProperty(produit.getLibProd());
        this.prixUniProd = new SimpleStringProperty(produit.getPrixUniProd());
        this.qteProdCom = new SimpleIntegerProperty(contenirVente.getQteVen());
        this.codeProd = new SimpleStringProperty(produit.getCodeProd());
        this.total = new SimpleStringProperty(String.valueOf(Integer.parseInt(produit.getPrixUniProd()) * contenirVente.getQteVen()));
    }

    public ProduitR(Produit produit, ObservableList<ProduitR> produitListVent, TableView<ProduitR> produitCaisseTable, Integer com) {
        this.idProd = new SimpleIntegerProperty(produit.getIdProd());
        this.libProd = new SimpleStringProperty(produit.getLibProd());
        this.prixUniProd = new SimpleStringProperty(produit.getPrixUniProd());
        this.qteProdCom = new SimpleIntegerProperty(com);
        this.codeProd = new SimpleStringProperty(produit.getCodeProd());
        this.total = new SimpleStringProperty(String.valueOf(Integer.parseInt(prixUniProd.getValue()) * qteProdCom.getValue()));
        this.Suppression = new JFXCheckBox("Supprimer");
        this.qteCom = new JFXTextField();
        this.qteCom.setText(String.valueOf(com));

        this.Suppression.setOnMouseClicked(e -> {
//            Boolean sup = false;
//            if (false == sup) {
//                ProduitR pr = produitCaisseTable.getSelectionModel().getSelectedItem();
//                System.out.println(pr);
//                Boolean find = false;
//                int t = 0;
//                for (int i = 0; i < produitListVent.size(); i++) {
//                    if (produitListVent.get(i).getIdProd().getValue() == pr.getIdProd().getValue()) {
//                        t = i;
//                        find = true;
//                    }
//                }
//                if (find == true) {
//                    produitListVent.remove(t);
//                    produitCaisseTable.setItems(produitListVent);
//                }
//            }
//
//            sup = true;
        });
    }

    public SimpleStringProperty getDateVen() {
        return dateVen;
    }

    public void setDateVen(SimpleStringProperty dateVen) {
        this.dateVen = dateVen;
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

    public JFXTextField getQteCom() {
        return qteCom;
    }

    public void setQteCom(JFXTextField qteCom) {
        this.qteCom = qteCom;
    }

    @Override
    public String toString() {
        return libProd.getValue();
    }

}
