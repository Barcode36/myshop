/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entites.Client;
import entites.Stock;
import java.util.Date;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Christ
 */
public class StockR {

    private SimpleIntegerProperty idStock;
    private SimpleIntegerProperty idProd;
    private SimpleStringProperty codeStock;
    private SimpleIntegerProperty coutAchatStock;
    private SimpleIntegerProperty qteIniSTock;
    private SimpleIntegerProperty qteActuStock;
    private SimpleStringProperty dateRavi;
    private SimpleStringProperty dateExpi;
    private SimpleStringProperty Fournisseur;
    
    public StockR() {
    }

    public StockR( SimpleIntegerProperty idProd, SimpleStringProperty codeStock, SimpleIntegerProperty coutAchatStock, SimpleIntegerProperty qteIniSTock, SimpleIntegerProperty qteActuStock, SimpleStringProperty dateRavi, SimpleStringProperty dateExpi, SimpleStringProperty Fournisseur) {
        
        this.idProd = idProd;
        this.codeStock = codeStock;
        this.coutAchatStock = coutAchatStock;
        this.qteIniSTock = qteIniSTock;
        this.qteActuStock = qteActuStock;
        this.dateRavi = dateRavi;
        this.dateExpi = dateExpi;
        this.Fournisseur = Fournisseur;
    }

    public StockR(SimpleStringProperty codeStock, SimpleIntegerProperty coutAchatStock, SimpleIntegerProperty qteIniSTock, SimpleIntegerProperty qteActuStock, SimpleStringProperty Fournisseur) {
        this.codeStock = codeStock;
        this.coutAchatStock = coutAchatStock;
        this.qteIniSTock = qteIniSTock;
        this.qteActuStock = qteActuStock;
        this.Fournisseur = Fournisseur;
    }
    
    

    public StockR(Stock stk) {
        this.idProd = new SimpleIntegerProperty(stk.getIdProd());
        this.codeStock = new SimpleStringProperty(stk.getCodeStock());
        this.coutAchatStock = new SimpleIntegerProperty (stk.getCoutAchatUni());
        this.qteIniSTock = new SimpleIntegerProperty(stk.getQteIniSTock());
        this.qteActuStock = new SimpleIntegerProperty(stk.getQteActuStock());
        this.dateRavi = new SimpleStringProperty(new Date(stk.getDateRavi().getTime())+"");
        this.dateExpi = new SimpleStringProperty(new Date(stk.getDateExpi().getTime())+"");
        this.Fournisseur = new SimpleStringProperty(stk.getFournisseur()); 
    }

    public SimpleIntegerProperty getIdStock() {
        return idStock;
    }

    public void setIdStock(SimpleIntegerProperty idStock) {
        this.idStock = idStock;
    }

    public SimpleIntegerProperty getIdProd() {
        return idProd;
    }

    public void setIdProd(SimpleIntegerProperty idProd) {
        this.idProd = idProd;
    }

    public SimpleStringProperty getCodeStock() {
        return codeStock;
    }

    public void setCodeStock(SimpleStringProperty codeStock) {
        this.codeStock = codeStock;
    }

    public SimpleIntegerProperty getCoutAchatStock() {
        return coutAchatStock;
    }

    public void setCoutAchatStock(SimpleIntegerProperty coutAchatStock) {
        this.coutAchatStock = coutAchatStock;
    }

    public SimpleIntegerProperty getQteIniSTock() {
        return qteIniSTock;
    }

    public void setQteIniSTock(SimpleIntegerProperty qteIniSTock) {
        this.qteIniSTock = qteIniSTock;
    }

    public SimpleIntegerProperty getQteActuStock() {
        return qteActuStock;
    }

    public void setQteActuStock(SimpleIntegerProperty qteActuStock) {
        this.qteActuStock = qteActuStock;
    }

    public SimpleStringProperty getDateRavi() {
        return dateRavi;
    }

    public void setDateRavi(SimpleStringProperty dateRavi) {
        this.dateRavi = dateRavi;
    }

    public SimpleStringProperty getDateExpi() {
        return dateExpi;
    }

    public void setDateExpi(SimpleStringProperty dateExpi) {
        this.dateExpi = dateExpi;
    }

    public SimpleStringProperty getFournisseur() {
        return Fournisseur;
    }

    public void setFournisseur(SimpleStringProperty Fournisseur) {
        this.Fournisseur = Fournisseur;
    }

   

}
