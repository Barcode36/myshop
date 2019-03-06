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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modele.ProduitR;
import service.IProduitService;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class RechercheProduitPaneController implements Initializable {

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

    IProduitService produitService = MainViewController.produitService;

    ObservableList<ProduitR> produitList = FXCollections.observableArrayList();
    @FXML
    private JFXTextField txtRec;
    @FXML
    private AnchorPane stage;
    @FXML
    private GridPane cont;

    public List<Produit> listProduit() {
        return produitService.produitList();
    }

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadInventairegrid();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s =(Stage) stage.getScene().getWindow();
                if(s.isMaximized()){
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                    System.out.println(s.getWidth());
                }
                stage.setPrefWidth(MainViewController.temporaryPaneTot.getWidth());
                cont.setPrefWidth(MainViewController.temporaryPaneTot.getWidth() - 135);
            }
        });
        MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stage.setPrefWidth(newValue.doubleValue());
                cont.setPrefWidth(newValue.doubleValue() - 135);
            }

        });
    }

    @FXML
    private void recherche(KeyEvent event) {
        produitList.clear();
        List<Produit> list = produitService.recherche(txtRec.getText());
        System.out.println(list);
        for (Produit produit : list) {
            produitList.add(new ProduitR(produit));
        }
        libProdCol.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        codeProdcol.setCellValueFactory(cellData -> cellData.getValue().getCodeProd());
        prixProdCol.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        qteProdCol.setCellValueFactory(cellData -> cellData.getValue().getQteIniProd().asObject());
        produitTable.setItems(produitList);
    }

}
