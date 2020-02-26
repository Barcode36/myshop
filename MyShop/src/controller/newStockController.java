/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import static controller.CaissePaneController.clientNew;
import entites.Produit;
import entites.Stock;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modele.ClientR;
import service.IClientService;
import service.IStockService;
import service.imp.StockService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class newStockController implements Initializable {

   
    @FXML
    private TextField qteStockProd;
    @FXML
    private TextField libProdField;
    @FXML
    private TextField prixAchatField;
    @FXML
    private TextField codeStockField;
    @FXML
    private ComboBox fourCombo;
    @FXML
    private Button saveBtn;
    @FXML
    private Button printCmdBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private Button addFourBtn;
    @FXML
    private DatePicker dtPickerExpiry;
      
    public static  ObservableList<String> lFour = FXCollections.observableArrayList();
    IStockService stockService = new StockService() ;
    
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lFour.clear();
        libProdField.setText(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getLibProd().get());
        List <Object[]> l = null;
        l = stockService.findAll(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getIdProd().get()) ;
        
        if(l!=null && l.size()>0){
            for(Object[] s:l){
                lFour.add(s[4]+"" );
           }
        }
        
       
        SimpleStringProperty dtS = new SimpleStringProperty(""+new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        codeStockField.setText("stock "+dtS.getValue());
        fourCombo.setItems(lFour);
        
    }

   
    @FXML
    private void saveStock(ActionEvent event) {
        Stock s = new Stock();
       
        s.setCodeStock(codeStockField.getText());
        s.setFournisseur(fourCombo.getEditor().getText());
        s.setQteIniSTock(Integer.parseInt(qteStockProd.getText())); //qteStockProd
        s.setCoutAchatUni(Double.parseDouble(prixAchatField.getText()));
        s.setIdProd(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getIdProd().get());
        s.setQteActuStock(Integer.parseInt(qteStockProd.getText()));
        s.setDateRavi(new Date());
        s.setDateExpi(new Date(dtPickerExpiry.getValue().atStartOfDay(ZoneId.systemDefault()).toEpochSecond()));
        Produit p = new Produit();
        p.setIdProd(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getIdProd().get());
       
      
        try {
            stockService.findByNom(s);
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Ce Stock existe déjà", NotificationType.WARNING);
            notification.showAndDismiss(Duration.seconds(1.5));
        } catch (Exception e) {
            stockService.ajouter(s);
             Produit produitModif = MainViewController.produitService.findById(p);
             produitModif.setQteIniProd(produitModif.getQteIniProd()+Integer.parseInt(qteStockProd.getText()));
             MainViewController.produitService.modifier(produitModif);
             
             TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Stock enregistré", NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.seconds(1.5));
            /*ClientR cr = new ClientR(c);
            CaissePaneController.clientNew = cr;
            CaissePaneController.txtCltV.setText(clientNew.getNomClt().getValue() + " (" + clientNew.getNumClt().getValue() + ")");*/
        }
        //CaissePaneController.newClt = true;
          //switchPane("/views/stockInfos.fxml");
          
        Stage st = (Stage) saveBtn.getScene().getWindow();
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

            } catch (IOException e) {
                Logger.getLogger(MainPrincipalController.class
                        .getName()).log(Level.SEVERE, null, e);
            }
        st.close();
        
       
        
        st.setOnCloseRequest(ex -> {
                //  cltCombo.setValue(clientNew);
            

            });
        
        //RechercheProduitPaneController.
    }

    @FXML
    private void annuler(ActionEvent event) {
       // switchPane("/views/stockInfos.fxml");
        Stage st = (Stage) closeBtn.getScene().getWindow();
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

            } catch (IOException e) {
                Logger.getLogger(MainPrincipalController.class
                        .getName()).log(Level.SEVERE, null, e);
            }
        st.close();
       
    }
    
    @FXML
    private void newFour(ActionEvent event) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/views/addFour.fxml").openStream());
            Stage stage = new Stage();

            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("/css/essai.css").toExternalForm());
            stage.getIcons().add(new Image(CaissePaneController.class.getResourceAsStream("/img/afnacos.ico")));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.show();

           /* stage.setOnHidden(ex -> {
                //  cltCombo.setValue(clientNew);
                try {
                    txtClt.setText(clientNew.getNomClt().getValue() + " (" + clientNew.getNumClt().getValue() + ")");
                } catch (Exception e) {
                }

            });*/

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

     /*private void switchPane(String pane) {
        try {
            MainViewController.temporaryPane.getChildren().clear();
            StackPane stackPane = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> elements = stackPane.getChildren();

            MainViewController.temporaryPane.getChildren().setAll(elements);

            MainViewController.drawerTmp.setVisible(true);
            // MainViewController.hamburgerTmp = new JFXHamburger();
        } catch (IOException ex) {
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
}
