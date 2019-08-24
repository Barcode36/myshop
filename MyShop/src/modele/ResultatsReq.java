/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author elsha
 */
public class ResultatsReq {
    private SimpleStringProperty resultString;
    private SimpleStringProperty resultInt;
    private SimpleStringProperty prixProd;
    private SimpleStringProperty mtVte;
    private SimpleStringProperty dateVen;
    private SimpleStringProperty nomClt;
    
    public ResultatsReq(SimpleStringProperty ob,SimpleStringProperty resultInt){
        this.resultString = ob;
        this.resultInt = resultInt;
    }

    public ResultatsReq(SimpleStringProperty resultString, SimpleStringProperty resultInt, SimpleStringProperty prixProd) {
        this.resultString = resultString;
        this.resultInt = resultInt;
        this.prixProd = prixProd;
    }

    public ResultatsReq(SimpleStringProperty resultString, SimpleStringProperty resultInt, SimpleStringProperty prixProd, SimpleStringProperty mtVte) {
        this.resultString = resultString;
        this.resultInt = resultInt;
        this.prixProd = prixProd;
        this.mtVte = mtVte;
    }
    
    
    

    public ResultatsReq(SimpleStringProperty resultString, SimpleStringProperty resultInt, SimpleStringProperty prixProd, SimpleStringProperty mtVte, SimpleStringProperty dateVen, SimpleStringProperty nomClt) {
        this.resultString = resultString;
        this.resultInt = resultInt;
        this.prixProd = prixProd;
        this.mtVte = mtVte;
        this.dateVen = dateVen;
        this.nomClt = nomClt;
    }

    public SimpleStringProperty getResultString() {
        return resultString;
    }

    public void setResultString(SimpleStringProperty resultString) {
        this.resultString = resultString;
    }

    public SimpleStringProperty getResultInt() {
        return resultInt;
    }

    public void setResultInt(SimpleStringProperty resultInt) {
        this.resultInt = resultInt;
    }

    public SimpleStringProperty getPrixProd() {
        return prixProd;
    }

    public void setPrixProd(SimpleStringProperty prixProd) {
        this.prixProd = prixProd;
    }

    public SimpleStringProperty getMtVte() {
        return mtVte;
    }

    public void setMtVte(SimpleStringProperty mtVte) {
        this.mtVte = mtVte;
    }

    public SimpleStringProperty getDateVen() {
        return dateVen;
    }

    public void setDateVen(SimpleStringProperty dateVen) {
        this.dateVen = dateVen;
    }


    public SimpleStringProperty getNomClt() {
        return nomClt;
    }

    public void setNomClt(SimpleStringProperty nomClt) {
        this.nomClt = nomClt;
    }
    
    

    
}
