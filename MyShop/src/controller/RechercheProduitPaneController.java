/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import entites.Produit;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modele.ProduitR;
import modele.ResultatsReq;
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
    @FXML
    private TableColumn<ProduitR, String> expiryProdCol;

    IProduitService produitService = MainViewController.produitService;

    ObservableList<ProduitR> produitList = FXCollections.observableArrayList();
    @FXML
    private JFXTextField txtRec;
    @FXML
    private AnchorPane stage;
    @FXML
    private AnchorPane cont;
    
    public static TableView<ProduitR> recTableProd;

    public List<Produit> listProduit() {
        return produitService.produitList();
    }
    
    

    public  void loadInventairegrid() {
        produitList.clear();
        for (Produit produit : listProduit()) {
           //System.out.println("AJOUT ");
            produitList.add(new ProduitR(produit));
            
//System.out.println("dt: "+produitList.size());
            if(produitList.size()>0){
                if(produitList.get(produitList.size()-1).getExpiryDate() != null){
                   // System.out.println("dEDANS: ");
                   /* if(produit.getExpiryDate() != null){
                      //produitList.get(produitList.size()-1).setExpiryDate(new SimpleStringProperty(""+new SimpleDateFormat("dd-MM-yyyy").format(new Date(produit.getExpiryDate().getTime()))) );
                        produitList.get(produitList.size()-1).setExpiryDate(new SimpleStringProperty("Imminent") );
                    }else{
                        produitList.get(produitList.size()-1).setExpiryDate( new SimpleStringProperty("Date non-enregistrée")) ;
                    }*/
//
                }else{
                    produitList.get(produitList.size()-1).setExpiryDate( new SimpleStringProperty("Date non-enregistrée")) ;
                }
            }
            /**/
            
           
        }
        
       
        // System.out.println(""+new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        
        libProdCol.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        codeProdcol.setCellValueFactory(cellData -> cellData.getValue().getCodeProd());
        prixProdCol.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        qteProdCol.setCellValueFactory(cellData -> cellData.getValue().getQteIniProd().asObject());
       
        expiryProdCol.setCellValueFactory(cellData -> cellData.getValue().getExpiryDate());
        produitTable.setItems(produitList);
    }

    /**
     * Initializes the controller class.
     */
    boolean ok = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         MainViewController.temporaryPaneTot.widthProperty().addListener((obs, oldVal, newVal)->{
            if( (Double) newVal <= (Double) oldVal){
                
                    if(!ok){
                       produitTable.setMaxWidth(730);
                       ok=true;
                    }else{
                         produitTable.setMaxWidth(produitTable.getWidth()-5);
                    }  
            }
            
        });
        
        
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
       
        loadInventairegrid();
       

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s =(Stage) stage.getScene().getWindow();
                if(s.isMaximized()){
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                    System.out.println(s.getWidth());
                }
                //stage.setPrefWidth(MainViewController.temporaryPaneTot.getWidth());
                //cont.setPrefWidth(MainViewController.temporaryPaneTot.getWidth() - 135);
            }
        });
        
        /*MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stage.setPrefWidth(newValue.doubleValue());
                cont.setPrefWidth(newValue.doubleValue() - 135);
            }

        });*/
        recTableProd = produitTable;
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
        expiryProdCol.setCellValueFactory(cellData -> cellData.getValue().getExpiryDate() );
        
        produitTable.setItems(produitList);
    }
    
    
    @FXML
    private void openStockInfos(MouseEvent event) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/views/stockInfos.fxml").openStream());
            Stage stage = new Stage();

            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("/css/essai.css").toExternalForm());
            stage.getIcons().add(new Image(CaissePaneController.class.getResourceAsStream("/img/afnacos.ico")));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
