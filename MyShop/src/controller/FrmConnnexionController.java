/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import entites.Compte;
import entites.TypeCompte;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class FrmConnnexionController implements Initializable {

    @FXML
    private JFXComboBox<String> txtPseudoCennect;
    @FXML
    private JFXPasswordField txtPassConnect;

    ObservableList<String> compteList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane cont;

    /**
     * Initializes the controller class.
     */
    public List<Compte> listCompte() {
        return MainViewController.compteServiceD.compteList();
    }

    private void fillCompteCombo() {
        compteList.clear();
        for (Compte c : listCompte()) {
            compteList.add(c.getPseudoComp());
        }
        txtPseudoCennect.setItems(compteList);
    }
    
     boolean ok;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MainViewController.hamburgerTmp.setVisible(false);
        MainViewController.mainCss.setVisible(false);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
        fillCompteCombo();
        
       // MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
         //   @Override
          //  public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               //MainViewController.img.setFitWidth(MainViewController.temporaryPaneTot.getWidth());
               // System.out.println("ov "+oldValue+" nv "+newValue);
//                MainViewController.dshPane.heightProperty().addListener(new ChangeListener<Number>() {
                    /*@Override
                    public void changed(ObservableValue<? extends Number> observable0, Number oldValue0, Number newValue0) {
                        //System.out.println("dsh height: "+MainViewController.dshPane.getHeight());
//                        MainViewController.img.setFitHeight(MainViewController.dshPane.getHeight());
                    }
                });*/
                
              //  if( (Double) oldValue < (Double) newValue){
                    
                    //MainViewController.img.setTranslateX(MainViewController.dshPane.getWidth());
                    //MainViewController.dshPane.setTranslateX(0);
                   // MainViewController.img.setFitWidth(MainViewController.temporaryPaneTot.getWidth());
                    //System.out.println("inhinFm");
           //     }
                
            //    if( (Double) oldValue > (Double) newValue){
                   // MainViewController.img.setTranslateX(0);
                   // MainViewController.dshPane.setTranslateX(0);
                    
                   // System.out.println("unhunFm");
             //   }
                
        //    }
      
     //   });        
       
    }

    @FXML
    private void connexionButton(ActionEvent event) {
        Compte c = new Compte();
        try {
            if (!txtPseudoCennect.getValue().isEmpty()) {
                c.setPseudoComp(txtPseudoCennect.getValue());
            }
        } catch (Exception e) {
            c.setPseudoComp(txtPseudoCennect.getJFXEditor().getText());
        }

        c.setMdpComp(txtPassConnect.getText());
        try {
            
            
            MainViewController.compteActif = MainViewController.compteServiceD.Connexion(c);
            TypeCompte compte = new TypeCompte(MainViewController.compteActif.getIdTypComp());
            MainViewController.typeCompteActif = MainViewController.typeServiceD.findById(compte);
            MainViewController.temporaryPane.getChildren().clear();
            
            FXMLLoader fxmlLoader  = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(Constants.DashBoard));
            StackPane dashBoard = fxmlLoader.load();
            
            MainViewController.temporaryPane.getChildren().setAll(dashBoard);
            
            MainViewController.hamburgerTmp.setVisible(false);
            MainViewController.mainCss.setVisible(false);
            MainViewController.dshPane.setVisible(true);
           
            
                if (!MainViewController.typeCompteActif.getLibTyp().equals("Administrateur")) {
                    /*
                    DashBoardController.btnComp.setVisible(false);
                    DashBoardController.btnInvent.setVisible(false);
                    DashBoardController.btnBil.setVisible(false);*/
                    
                    DashBoardController.contHbox1.getChildren().removeAll(DashBoardController.btnComp,
                            DashBoardController.btnInvent,DashBoardController.btnBil,DashBoardController.btnReg);
                    
                    DashBoardController.contHbox1.getChildren().addAll(DashBoardController.btnAid,
                            DashBoardController.btnDec);
                    
                    DashBoardController.contHbox2.getChildren().clear();
                    VBox menu = null;
                    menu = FXMLLoader.load(getClass().getResource(Constants.MenuLateralC));
                    MainViewController.drawerTmp.setSidePane(menu);
                }else{
                    
                }
             MainViewController.dshPane.setVisible(true);
             
           //  if(MainViewController.temporaryPaneTot.getWidth()<1300){
//                MainViewController.img.setFitWidth(1233);
            //    MainViewController.img.setTranslateX(0);
          //      MainViewController.img.setFitHeight(717);
                // System.out.println("recadrage connect ok");
        //    }
             
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(" "+e);
            e.printStackTrace();
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Pseudo ou Mot de passe incorrect", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(1.5));
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        System.exit(0);
    }

}
