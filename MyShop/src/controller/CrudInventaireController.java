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
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.ProduitR;
import service.IProduitService;
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
    @FXML
    private JFXTextField txtQteAug;

    IProduitService produitService = MainViewController.produitService;

    ObservableList<ProduitR> produitList = FXCollections.observableArrayList();

    Produit produitModif = new Produit();
    @FXML
    private JFXButton saveUp = new JFXButton();
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private AnchorPane stage;
    @FXML
    private AnchorPane cont;
    @FXML
    private Label ent;
    @FXML
    private Group gp;
    private Label label;
    @FXML
    private JFXTextField txtRec;
    @FXML
    private ColumnConstraints consCol1;
    @FXML
    private ColumnConstraints consCol2;

    public List<Produit> listProduit() {
        return produitService.produitList();
    }

    private AnchorPane stagePane;
     
    /**
     * Initializes the controller class.
     */
    boolean ok = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        stagePane = stage;
        //txtCode.setFocusTraversable(true);
        //txtCode.requestFocus();
        
                
        loadInventairegrid();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s = (Stage) stage.getScene().getWindow();
                if (s.isMaximized()) {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());

                }
            }
        });

     
       
        
        txtQteAug.setText("0");
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
        txtQteProd.setEditable(false);
        txtQteAug.setDisable(false);
        txtQteAug.setText("0");
        saveUp.setText("Modifier");
    }

    @FXML
    private void saveProd(ActionEvent event) {
        save();
    }

    private void save() {
        if(txtCode.getText().isEmpty() || txtLibProd.getText().isEmpty() || txtPrixProd.getText().isEmpty()
                || txtQteProd.getText().isEmpty()){
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Veuillez remplir tous les champs ", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(1));
        } else 
            if (saveUp.getText().equals("Ajouter")) {
            //clearProduitText();
            Produit produit = new Produit();
            produit.setCodeProd(txtCode.getText());
            produit.setLibProd(txtLibProd.getText());
            produit.setPrixUniProd(txtPrixProd.getText());
            txtQteProd.setFocusTraversable(true); 
            txtQteProd.setDisable(false);
            txtQteProd.setEditable(true);
            //System.out.println("qte prod: "+txtQteProd.getText());
            produit.setQteIniProd(Integer.parseInt(txtQteProd.getText()));
            produit.setEtatProd("actif");
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
                notification.setTray("MyShop", "Ajout effectué", NotificationType.SUCCESS);
                notification.showAndDismiss(Duration.seconds(1));

            }

        } else {
                
                produitModif.setCodeProd(txtCode.getText());
                produitModif.setLibProd(txtLibProd.getText());
                produitModif.setPrixUniProd(txtPrixProd.getText());
                produitModif.setQteIniProd(Integer.parseInt(txtQteProd.getText()));
                produitService.modifier(produitModif);
                txtQteAug.setText("0");
                txtQteAug.setDisable(true);

                saveUp.setText("Modifier");
                TrayNotification notification = new TrayNotification();
                notification.setAnimationType(AnimationType.POPUP);
                notification.setTray("MyShop", "Modiffication effectuée", NotificationType.SUCCESS);
                notification.showAndDismiss(Duration.seconds(1));

                    txtQteProd.requestFocus();
                    txtQteProd.setFocusTraversable(true); 
                txtCode.setFocusTraversable(true);
                txtCode.requestFocus();
                txtQteProd.setDisable(false);
                 txtQteProd.setFocusTraversable(true);
                clearProduitText();
           
            
            
        }

        loadInventairegrid();
        txtCode.setFocusTraversable(true);
        txtQteProd.setDisable(false); 
        txtQteAug.setFocusTraversable(true);
        txtCode.requestFocus();
    }

    private void clearProduitText() {
        txtCode.clear();
        txtLibProd.clear();
        txtPrixProd.clear();
        txtQteProd.clear();
        txtQteProd.setDisable(false);
        txtQteAug.setText("0");
        txtQteAug.setDisable(true);
        saveUp.setText("Ajouter");
        txtCode.setFocusTraversable(true);
        txtCode.requestFocus();
    }

    @FXML
    private void suppProduit(ActionEvent event) {
        if(txtCode.getText().isEmpty() || txtLibProd.getText().isEmpty() || txtPrixProd.getText().isEmpty()
                || txtQteProd.getText().isEmpty()){
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Veuillez selectionner un produit à supprimer ", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(1));
        } else {
            produitService.supprimer(produitModif);
            loadInventairegrid();
            clearProduitText();
            saveUp.setText("Ajouter");
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Suppression effectué", NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.seconds(1));
            clearProduitText();
            txtQteProd.setDisable(false);
            txtQteProd.setEditable(true);
            txtCode.setFocusTraversable(true);
            txtCode.requestFocus();
        }
    }

    @FXML
    private void vider(ActionEvent event) {
        clearProduitText();
       
        txtQteProd.setDisable(false);
        txtQteProd.setEditable(true);
        saveUp.setText("Ajouter");
        txtCode.setFocusTraversable(true);
        txtCode.requestFocus();
    }

    @FXML
    private void recherche(KeyEvent event) {
        if (txtRec.getText().isEmpty()) {
            loadInventairegrid();
        } else {
            if (event.getCode() == KeyCode.ENTER) {
                produitList.clear();
                List<Produit> list = produitService.recherche2(txtRec.getText());
                for (Produit produit : list) {
                    produitList.add(new ProduitR(produit));
                }
                libProdCol.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
                codeProdcol.setCellValueFactory(cellData -> cellData.getValue().getCodeProd());
                prixProdCol.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
                qteProdCol.setCellValueFactory(cellData -> cellData.getValue().getQteIniProd().asObject());
                produitTable.setItems(produitList);
            } else {
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

    }

    @FXML
    private void viderRec(ActionEvent event) {
        loadInventairegrid();
        txtRec.clear();
        txtRec.setFocusTraversable(true);
        txtRec.requestFocus();
    }
    
    @FXML
    private void calculQte(){
       //txtQteProd.setDisable(true);
       txtQteProd.setText( String.valueOf(Integer.parseInt(txtQteProd.getText())  + Integer.parseInt(txtQteAug.getText())));
       txtQteProd.setFocusTraversable(true);
       txtQteProd.requestFocus();
       txtQteAug.setText("0");
       txtQteAug.setDisable(true);
       txtQteProd.setDisable(false);
       txtQteProd.setEditable(true);
      
    }

}
