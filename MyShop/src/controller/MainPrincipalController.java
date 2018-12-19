/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import entites.Compte;
import entites.Produit;
import entites.TypeCompte;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import modele.CompteR;
import modele.ProduitR;
import modele.TypeCompteR;
import service.ICompteService;
import service.IProduitService;
import service.ITypeService;
import service.imp.CompteService;
import service.imp.ProduitService;
import service.imp.TypeService;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class MainPrincipalController implements Initializable {

//declaration des composants de la partie inventaire
    @FXML
    private GridPane grid;
    @FXML
    private JFXButton btnInventaire;
    @FXML
    private GridPane gridInventaire;
//Declaration des composants lies à Inventaire
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtLibProd;
    @FXML
    private TextField txtPrixProd;
    @FXML
    private TextField txtQteProd;
    @FXML
    private JFXButton tbnSaveProd;
    @FXML
    private TableView<ProduitR> produitTable;
    @FXML
    private TableColumn<ProduitR, String> libProdCol;
    @FXML
    private TableColumn<ProduitR, String> codeProdcol;
    @FXML
    private TableColumn<ProduitR, Integer> qteProdCol;
    @FXML
    private TableColumn<ProduitR, String> prixProdCol;
// fin declaration

    //Declaration des composants lies à Compte
    @FXML
    private JFXButton tbnSaveComp;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TableView<CompteR> CompteTable;
    @FXML
    private TableColumn<CompteR, String> nomCompCol;
    @FXML
    private TableColumn<CompteR, String> prenomCompcol;
    @FXML
    private TableColumn<CompteR, String> pseudoCompCol;
    @FXML
    private TableColumn<CompteR, String> mdpCompCol;
    @FXML
    private TableColumn<CompteR, String> rolCompCol;
    @FXML
    private TableColumn<CompteR, String> etatCompCol;
    @FXML
    private JFXButton btnComp;
    @FXML
    private GridPane gridCompte;
    @FXML
    private TextField txtNomComp;
    @FXML
    private TextField txtPseudoComp;
    @FXML
    private TextField txtPrenomComp;
    @FXML
    private ComboBox<TypeCompteR> typeCompteCombo;
    //fin declaration

//Declaration des observables listes
    ObservableList<ProduitR> produitList = FXCollections.observableArrayList();
    ObservableList<CompteR> compteList = FXCollections.observableArrayList();
    ObservableList<TypeCompteR> typeCompteList = FXCollections.observableArrayList();

//fin de declaration
//Declaration des services
    IProduitService produitService = new ProduitService();
    ICompteService compteService = new CompteService();
    ITypeService typeService = new TypeService();

//fin de declaration
//Declaration des classes à modifier
    Produit produitModif = new Produit();
    Compte compteModif = new Compte();
//fin declaration
    //recuperation des listes de chaque composant

    public List<Produit> listProduit() {
        return produitService.produitList();
    }

    public List<Compte> listCompte() {
        return compteService.compteList();
    }

    public List<TypeCompte> listTypeCompte() {
        return typeService.typeCmopteList();
    }

//Chargement des tables de chaque grid
    public void loadInventairegrid() {
        produitList.clear();
        for (Produit produit : listProduit()) {
            produitList.add(new ProduitR(produit));
        }
        libProdCol.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        codeProdcol.setCellValueFactory(cellData -> cellData.getValue().getCodeProd());
        prixProdCol.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        qteProdCol.setCellValueFactory(cellData -> cellData.getValue().getQteIniProd().asObject());
        produitTable.setItems(produitList);
    }

    public void loadCompteGrid() {
        compteList.clear();
        for (Compte compte : listCompte()) {
            TypeCompte t = new TypeCompte(compte.getIdTypComp());
            TypeCompte typeCompte = typeService.findById(t);
            compteList.add(new CompteR(compte, typeCompte));
        }
        nomCompCol.setCellValueFactory(cellData -> cellData.getValue().getNomComp());
        prenomCompcol.setCellValueFactory(cellData -> cellData.getValue().getPrenomComp());
        pseudoCompCol.setCellValueFactory(cellData -> cellData.getValue().getPseudoComp());
        mdpCompCol.setCellValueFactory(cellData -> cellData.getValue().getMdpComp());
        rolCompCol.setCellValueFactory(cellData -> cellData.getValue().getLibTypComp());
        etatCompCol.setCellValueFactory(cellData -> cellData.getValue().getEtatComp());
        CompteTable.setItems(compteList);
    }

    public void loadTypeCompteCombo() {
        typeCompteList.clear();
        for (TypeCompte typeCompte : listTypeCompte()) {
            typeCompteList.add(new TypeCompteR(typeCompte));
        }
        typeCompteCombo.setItems(typeCompteList);
    }
//fin des methodes de chargement

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadInventairegrid();
        loadCompteGrid();
        loadTypeCompteCombo();
    }

    @FXML
    private void selectForm(ActionEvent event) {
        if (event.getSource() == btnInventaire) {
            gridInventaire.toFront();
            gridInventaire.setVisible(true);
            grid.toBack();
            grid.setVisible(false);
            gridCompte.setVisible(false);
        } else if (event.getSource() == btnComp) {
            gridCompte.toFront();
            gridCompte.setVisible(true);
            grid.setVisible(false);
            gridInventaire.setVisible(false);
        }
    }

    private void saveUpProduit(String saveUp) {
        if (saveUp.equals("Ajouter")) {
            Produit produit = new Produit();
            produit.setCodeProd(txtCode.getText());
            produit.setLibProd(txtLibProd.getText());
            produit.setPrixUniProd(txtPrixProd.getText());
            produit.setQteIniProd(Integer.parseInt(txtQteProd.getText()));
            produitService.ajouter(produit);
        } else {
            produitModif.setCodeProd(txtCode.getText());
            produitModif.setLibProd(txtLibProd.getText());
            produitModif.setPrixUniProd(txtPrixProd.getText());
            produitModif.setQteIniProd(Integer.parseInt(txtQteProd.getText()));
            produitService.modifier(produitModif);
        }
    }

    private void saveUpCompte(String saveUp) {
        if (saveUp.equals("Ajouter")) {
            Compte compte = new Compte();
            compte.setMdpComp(txtPassword.getText());
            compte.setNomComp(txtNomComp.getText());
            compte.setPrenomComp(txtPrenomComp.getText());
            compte.setPseudoComp(txtPseudoComp.getText());
            compteService.ajouter(compte);
        } else {
            compteModif.setMdpComp(txtPassword.getText());
            compteModif.setNomComp(txtNomComp.getText());
            compteModif.setPrenomComp(txtPrenomComp.getText());
            compteModif.setPseudoComp(txtPseudoComp.getText());
            compteService.modifier(compteModif);
        }
    }

    private void getProduitInformationFromTable(ProduitR produitR) {
        Produit p = new Produit(produitR.getIdProd().getValue());
        produitModif = produitService.findById(p);
        txtCode.setText(produitModif.getCodeProd());
        txtLibProd.setText(produitModif.getLibProd());
        txtPrixProd.setText(produitModif.getPrixUniProd());
        txtQteProd.setText(String.valueOf(produitModif.getQteIniProd()));
    }

    private void getCompteInformationFromTable(CompteR compteR) {
        Compte c = new Compte(compteR.getIdComp().getValue());
        compteModif = compteService.findById(c);
        txtNomComp.setText(compteModif.getNomComp());
        txtPrenomComp.setText(compteModif.getPrenomComp());
        txtPseudoComp.setText(compteModif.getPseudoComp());
        txtPassword.setText(compteModif.getMdpComp());
    }

    @FXML
    private void closeInventaire(MouseEvent event) {
        grid.toFront();
        grid.setVisible(true);
        gridInventaire.toBack();
        gridInventaire.setVisible(false);
    }

}
