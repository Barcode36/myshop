/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import controller.CaissePaneController;
import entites.Client;
import entites.ContenirVente;
import entites.Produit;
import entites.Vente;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import service.IProduitService;
import service.imp.ProduitService;

/**
 *
 * @author Christ
 */
public class ProduitR {

    private SimpleIntegerProperty idProd;
    private SimpleStringProperty codeProd;
    private SimpleStringProperty libProd;
    private SimpleStringProperty prixUniProd;
    private SimpleStringProperty cltAch;
    private SimpleIntegerProperty qteIniProd;
    private SimpleIntegerProperty qteProdCom;
    private SimpleStringProperty dateVen;
    private JFXCheckBox Suppression;
    private SimpleStringProperty total;
    private JFXTextField qteCom;
    public static JFXTextField qteComm;
    private Integer to;
    
    private Vente Vte;

    public ProduitR(Produit produit) {
        this.idProd = new SimpleIntegerProperty(produit.getIdProd());
        this.libProd = new SimpleStringProperty(produit.getLibProd());
        this.prixUniProd = new SimpleStringProperty(produit.getPrixUniProd());
        this.qteIniProd = new SimpleIntegerProperty(produit.getQteIniProd());
        this.codeProd = new SimpleStringProperty(produit.getCodeProd());

    }

    public ProduitR(Produit produit, Vente vente, ContenirVente contenirVente) {
        this.idProd = new SimpleIntegerProperty(produit.getIdProd());
        DateFormat dateFormat = new SimpleDateFormat("d-MM-yyy HH:mm:ss");
        String dateh = dateFormat.format(vente.getDateVen()).toString();
        this.dateVen = new SimpleStringProperty(dateh);
        this.libProd = new SimpleStringProperty(produit.getLibProd());
        this.prixUniProd = new SimpleStringProperty(String.valueOf(contenirVente.getPrixProd()));
        this.qteProdCom = new SimpleIntegerProperty(contenirVente.getQteVen());
        this.codeProd = new SimpleStringProperty(produit.getCodeProd());
        this.total = new SimpleStringProperty(String.valueOf(Integer.parseInt(produit.getPrixUniProd()) * contenirVente.getQteVen()));
    }

    public ProduitR(Produit produit, Vente vente, ContenirVente contenirVente, Client c) {
        this.cltAch = new SimpleStringProperty(c.getNomClt());
        this.idProd = new SimpleIntegerProperty(produit.getIdProd());
        DateFormat dateFormat = new SimpleDateFormat("d-MM-yyy HH:mm:ss");
        String dateh = dateFormat.format(vente.getDateVen()).toString();
        this.dateVen = new SimpleStringProperty(dateh);
        this.libProd = new SimpleStringProperty(produit.getLibProd());
        this.prixUniProd = new SimpleStringProperty(String.valueOf(contenirVente.getPrixProd()));
        this.qteProdCom = new SimpleIntegerProperty(contenirVente.getQteVen());
        this.codeProd = new SimpleStringProperty(produit.getCodeProd());
        this.total = new SimpleStringProperty(String.valueOf(Integer.parseInt(produit.getPrixUniProd()) * contenirVente.getQteVen()));
    }
    
    public ProduitR( Vente vente, ContenirVente contenirVente, Client c) {
        this.cltAch = new SimpleStringProperty(c.getNomClt());
        this.idProd = new SimpleIntegerProperty(contenirVente.getIdProd());
        DateFormat dateFormat = new SimpleDateFormat("d-MM-yyy HH:mm:ss");
        String dateh = dateFormat.format(vente.getDateVen()).toString();
        this.dateVen = new SimpleStringProperty(dateh);
        this.Vte = vente;
        this.prixUniProd = new SimpleStringProperty(String.valueOf(contenirVente.getPrixProd()));
        this.qteProdCom = new SimpleIntegerProperty(contenirVente.getQteVen());
        Produit p = new Produit(contenirVente.getIdProd());
        IProduitService produitService = new ProduitService();
        Produit produit = produitService.findById(p);
        this.total = new SimpleStringProperty(String.valueOf(Integer.parseInt(produit.getPrixUniProd()) * contenirVente.getQteVen()));
    }

    public ProduitR(Produit produit, int tot) {
        this.libProd = new SimpleStringProperty(produit.getLibProd());
        this.to = tot;
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
//        this.qteCom.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (!qteCom.getText().equals("") ) {
//                    total = new SimpleStringProperty(String.valueOf(Integer.parseInt(prixUniProd.getValue()) * Integer.parseInt(qteCom.getText())));
//                    
//                    Boolean find = false;
//                    int i = 0;
//                    int index = 0;
//                    for (ProduitR pr : produitListVent) {
//                        if (libProd.getValue().equals(pr.getLibProd().getValue())) {
//                            find = true;
//                            index = i;
//                        }
//                        i++;
//                    }
//                    if (find == true) {
//                        System.out.println(produitListVent.get(index));
//                        ProduitR prr = new ProduitR(produit);
//                        JFXTextField x = new JFXTextField(qteCom.getText());
//                        prr.setQteCom(x);
//                        JFXCheckBox c = new JFXCheckBox();
//                        prr.setSuppression(c);
//                        prr.setTotal(total);
//                        System.out.println(prr.getTotal());
//                        produitListVent.remove(index);
//                        
//                        qteCom.setFocusTraversable(true);
//                        qteCom.requestFocus();
//                    }
//                }
//            }
//
//        });
        //qteComm.setText(String.valueOf(com));
        this.qteCom.setText(String.valueOf(com));
        
        //qteCom.setFocusTraversable(true);
        //qteCom.requestFocus();
        //this.qteCom = qteComm;
       // this.qteComm.setEditable(false);
        //this.qteCom.setText(CaissePaneController.txtQteProdComm.getText());
    }

    public Integer getTo() {
        return to;
    }
    
    public SimpleStringProperty getVte() {
        return  new SimpleStringProperty(this.Vte.getIdVen()+"")   ;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public SimpleStringProperty getCltAch() {
        return cltAch;
    }

    public void setCltAch(SimpleStringProperty cltAch) {
        this.cltAch = cltAch;
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
