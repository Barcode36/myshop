/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entites.Produit;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.swing.SwingUtilities;
import modele.ProduitR;
import service.IProduitService;
import service.imp.ProduitService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class CrudInventaireController implements Initializable {

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
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXTextField txtLibProd;
    @FXML
    private JFXTextField txtPrixProd;
    @FXML
    private JFXTextField txtQteProd;

    IProduitService produitService = MainViewController.produitService;

    ObservableList<ProduitR> produitList = FXCollections.observableArrayList();

    Produit produitModif = new Produit();
    @FXML
    private JFXButton saveUp = new JFXButton();
    @FXML
    private GridPane pane1;
    @FXML
    private VBox pane2;
    @FXML
    private AnchorPane stage;
    @FXML
    private GridPane cont;
    @FXML
    private Label ent;
    @FXML
    private Group gp;
    private Label label;

    public List<Produit> listProduit() {
        return produitService.produitList();
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
                Stage s = (Stage) stage.getScene().getWindow();
                if (s.isMaximized()) {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());

                }
                cont.setPrefWidth(MainViewController.temporaryPaneTot.getWidth() - 170);
            }
        });

        MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double val = newValue.doubleValue() / 4;
                ent.setLayoutX(val);
                cont.setPrefWidth(newValue.doubleValue() - 170);
                pane1.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        ent.setPrefWidth(newValue.doubleValue() - 15);
                    }
                });

            }

        });

//        
        txtQteProd.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                save();
            }
        });
        txtCode.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                save();
            }
        });
        txtPrixProd.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                save();
            }
        });
        txtLibProd.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                save();
            }
        });
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);

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

    @FXML
    private void getProduitFromTable(MouseEvent event) {
        ProduitR pr = produitTable.getSelectionModel().getSelectedItem();
//      
        Produit p = new Produit(pr.getIdProd().getValue());
        produitModif = produitService.findById(p);
        txtCode.setText(produitModif.getCodeProd());
        txtLibProd.setText(produitModif.getLibProd());
        txtPrixProd.setText(String.valueOf(produitModif.getPrixUniProd()));
        txtQteProd.setText(String.valueOf(produitModif.getQteIniProd()));
        saveUp.setText("Modifier");
    }

    @FXML
    private void saveProd(ActionEvent event) {
        save();
    }

    private void save() {
        if (saveUp.getText().equals("ENREGISTRER")) {

            Produit produit = new Produit();
            produit.setCodeProd(txtCode.getText());
            produit.setLibProd(txtLibProd.getText());
            produit.setPrixUniProd(txtPrixProd.getText());
            produit.setQteIniProd(Integer.parseInt(txtQteProd.getText()));
            try {
                Produit p = produitService.findByCode(produit);
                TrayNotification notification = new TrayNotification();
                notification.setAnimationType(AnimationType.POPUP);
                notification.setTray("MyShop", "Ce produit existe déjà", NotificationType.WARNING);
                notification.showAndDismiss(Duration.seconds(1));
            } catch (Exception e) {
                produitService.ajouter(produit);
                clearProduitText();
                txtCode.setFocusTraversable(true);
                txtCode.requestFocus();
                TrayNotification notification = new TrayNotification();
                notification.setAnimationType(AnimationType.POPUP);
                notification.setTray("MyShop", "Enregistrement effectué", NotificationType.SUCCESS);
                notification.showAndDismiss(Duration.seconds(1));

            }

        } else {
            produitModif.setCodeProd(txtCode.getText());
            produitModif.setLibProd(txtLibProd.getText());
            produitModif.setPrixUniProd(txtPrixProd.getText());
            produitModif.setQteIniProd(Integer.parseInt(txtQteProd.getText()));
            produitService.modifier(produitModif);
            saveUp.setText("ENREGISTRER");
            txtCode.setFocusTraversable(true);
            txtCode.requestFocus();
            clearProduitText();
        }

        loadInventairegrid();

    }

    private void clearProduitText() {
        txtCode.clear();
        txtLibProd.clear();
        txtPrixProd.clear();
        txtQteProd.clear();
    }

    @FXML
    private void suppProduit(ActionEvent event) {
        produitService.supprimer(produitModif);
        loadInventairegrid();
        clearProduitText();
        saveUp.setText("Enregistrer");
        TrayNotification notification = new TrayNotification();
        notification.setAnimationType(AnimationType.POPUP);
        notification.setTray("MyShop", "Suppression effectué", NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.seconds(1));
        clearProduitText();
    }

    @FXML
    private void vider(ActionEvent event) {
        clearProduitText();
    }

}
