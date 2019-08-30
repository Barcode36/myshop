/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import entites.Produit;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modele.ProduitR;
import service.IProduitService;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class CaisseChoixProdController implements Initializable {

    @FXML
    private JFXTextField txtRec;

    @FXML
    private TableView<ProduitR> produitCaisseTable;
    @FXML
    private TableColumn<ProduitR, String> libProdColCaisse;
    @FXML
    private TableColumn<ProduitR, Integer> qteColCaisse;
    @FXML
    private TableColumn<ProduitR, String> prixColCaisse;

    IProduitService produitService = MainViewController.produitService;

    ObservableList<ProduitR> produitList = FXCollections.observableArrayList();

    public List<Produit> listProduit() {
        return produitService.produitList();
    }

    public void loadInventairegrid() {
        produitList.clear();
        for (Produit produit : listProduit()) {
            produitList.add(new ProduitR(produit));
        }
        libProdColCaisse.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        prixColCaisse.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        qteColCaisse.setCellValueFactory(cellData -> cellData.getValue().getQteIniProd().asObject());
        produitCaisseTable.setItems(produitList);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
        loadInventairegrid();
        produitCaisseTable.setOnMouseClicked(value -> {
            try {
                ProduitR prChoi = produitCaisseTable.getSelectionModel().getSelectedItem();
                if (prChoi == null) {
                    return;
                }
                CaissePaneController.produitChoisi = prChoi;
                //CaissePaneController.
                Stage s = (Stage) txtRec.getScene().getWindow();
                s.close();
            } catch (Exception e) {
            }

        });
    }

    @FXML
    private void ChoixProd(MouseEvent event) {

    }

    @FXML
    private void recherche(KeyEvent event) {
        produitList.clear();
        List<Produit> list = produitService.recherche(txtRec.getText());
        System.out.println(list);
        for (Produit produit : list) {
            produitList.add(new ProduitR(produit));
        }
        libProdColCaisse.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        prixColCaisse.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        qteColCaisse.setCellValueFactory(cellData -> cellData.getValue().getQteIniProd().asObject());
        produitCaisseTable.setItems(produitList);
    }

}
