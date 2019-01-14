/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import entites.Compte;
import entites.ContenirVente;
import entites.Vente;
import entites.ContenirVentePK;
import entites.Produit;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.ProduitR;
import service.ICompteService;
import service.IContenirVente;
import service.IProduitService;
import service.IVenteService;
import service.imp.CompteService;
import service.imp.ContenirVenteService;
import service.imp.ProduitService;
import service.imp.VenteService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class CaisseConfirmationController implements Initializable {
    
    @FXML
    private TableView<ProduitR> produitCaisseTable;
    @FXML
    private TableColumn<ProduitR, String> libProdColCaisse;
    @FXML
    private TableColumn<ProduitR, Integer> qteColCaisse;
    @FXML
    private TableColumn<ProduitR, String> prixColCaisse;
    @FXML
    private TableColumn<ProduitR, String> totalColCaisse;
    @FXML
    private TextField txtMontCllt;
    @FXML
    private Label lblTot;
    @FXML
    private TextField txtRemise;
    @FXML
    private Button btnClose;
    
    ObservableList<ProduitR> produitListVent = FXCollections.observableArrayList();
    
    IVenteService venteService = new VenteService();
    ICompteService compteService = new CompteService();
    IContenirVente contenirVenteService = new ContenirVenteService();
    IProduitService produitService = new ProduitService();
    
    Compte compteActif = new Compte();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void SupprimerProdVent(ActionEvent event) {
    }
    
    public void setListProd(ObservableList<ProduitR> list, Compte c) {
        int tot = 0;
        for (ProduitR pr : list) {
            pr.setQteProdCom(new SimpleIntegerProperty(Integer.parseInt(pr.getQteCom().getText())));
            int t = Integer.parseInt(pr.getQteCom().getText()) * Integer.parseInt(pr.getPrixUniProd().getValue());
            pr.setTotal(new SimpleStringProperty(String.valueOf(t)));
            produitListVent.add(pr);
            int totprod = Integer.parseInt(pr.getPrixUniProd().getValue()) * Integer.parseInt(pr.getQteCom().getText());
            tot += totprod;
        }
        lblTot.setText(String.valueOf(tot));
        libProdColCaisse.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        prixColCaisse.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        qteColCaisse.setCellValueFactory(cellData -> cellData.getValue().getQteProdCom().asObject());
        totalColCaisse.setCellValueFactory(cellData -> cellData.getValue().getTotal());
        produitCaisseTable.setItems(produitListVent);
        compteActif = c;
        //   cellData -> cellData.getValue().getQteProdCom().asObject()
//     new PropertyValueFactory<ProduitR, JFXTextField>("qteCom")
    }
    
    @FXML
    private void Valider(ActionEvent event) {
        Vente vente = new Vente();
        vente.setDateVen(new Date());
        vente.setIdClt(0);
        vente.setIdComp(compteActif.getIdComp());
        venteService.ajouter(vente);
        for (ProduitR pr : produitListVent) {
            ContenirVentePK cvpk = new ContenirVentePK(vente.getIdVen(), pr.getIdProd().getValue());
            ContenirVente contenirVente = new ContenirVente(cvpk);
            contenirVente.setQteVen(pr.getQteProdCom().getValue());
            contenirVenteService.ajouterContenirVente(contenirVente);
            Produit p = new Produit(pr.getIdProd().getValue());
            Produit produit = produitService.findById(p);
            produit.setQteIniProd(produit.getQteIniProd() - pr.getQteProdCom().getValue());
            
            produitService.modifier(produit);
        }
        TrayNotification notification = new TrayNotification();
        notification.setAnimationType(AnimationType.POPUP);
        notification.setTray("MyShop", "Vente Effectuée", NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.seconds(1.5));
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void calculRemise(KeyEvent event) {
        if (!txtMontCllt.getText().equals("")) {
            int rem = Integer.parseInt(txtMontCllt.getText()) - Integer.parseInt(lblTot.getText());
            txtRemise.setText(String.valueOf(rem));
        } else {
            txtRemise.clear();
        }
    }
    
}
