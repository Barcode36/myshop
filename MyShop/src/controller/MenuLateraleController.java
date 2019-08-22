/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class MenuLateraleController implements Initializable {

    @FXML
    private JFXButton btnAccueil;
    @FXML
    private JFXButton btnInventaire;
    @FXML
    private JFXButton btnCOmpte;
    @FXML
    private JFXButton btnCaisse;
    @FXML
    private JFXButton btnBilan;
    @FXML
    private JFXButton btnReglage;
    @FXML
    private JFXButton btnAide;
    @FXML
    private JFXButton btnQuitter;
    @FXML
    private JFXButton btnRec;
    @FXML
    private VBox stage;
    @FXML
    private Pane pane;
    @FXML
    private ImageView img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            StackPane dashBoard = FXMLLoader.load(getClass().getResource(Constants.Connect));
            MainViewController.temporaryPane.getChildren().setAll(dashBoard);
           // MainViewController.transition.stop();
        } catch (IOException ex) {
            System.out.println(" "+ex);
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
       
        
        /*MainViewController.temporaryPaneTot.widthProperty().addListener((obs,oldVal,newVal)->{
            if( (Double)newVal<(Double)oldVal ){
                stage.setTranslateX(0);
                if(stage.getWidth() >=150){
                    stage.setPrefWidth(stage.getWidth()-2);
                    pane.setPrefWidth(stage.getWidth());
                   // img.setFitWidth(0);
                }
            }
        });*/
       /* MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stage.setPrefWidth(newValue.doubleValue());
                pane.setPrefWidth(newValue.doubleValue() - 2);
            }
        });*/
        
        
    }

    @FXML
    private void openAccueil(ActionEvent event) {
        switchPane(Constants.DashBoard);
    }

    @FXML
    private void openInventaire(ActionEvent event) {
        switchPane(Constants.CrudInventaire);
    }
    
    @FXML
    private void openHistorique(ActionEvent event) {
        switchPane(Constants.Historique);
    }

    @FXML
    private void openComptes(ActionEvent event) {
        switchPane(Constants.CrudCompte);
    }

    @FXML
    private void openCaisse(ActionEvent event) {
        switchPane(Constants.Caisse);
    }

    @FXML
    private void openBilan(ActionEvent event) {
        switchPane(Constants.CrudBilan);
    }

    @FXML
    private void openReglage(ActionEvent event) {
        switchPane(Constants.Reglage);
    }

    @FXML
    private void openRecherche(ActionEvent event) {
        switchPane(Constants.RechercheProd);
    }

    @FXML
    private void openAide(ActionEvent event) {
        switchPane(Constants.Aide);
    }

    @FXML
    private void openClose(ActionEvent event) {
        try {
            switchPane(Constants.Connect);
            VBox menu = null;
            menu = FXMLLoader.load(getClass().getResource(Constants.MenuLateral));
            MainViewController.drawerTmp.setSidePane(menu);
            
            MainViewController.dshPane.setVisible(false);
            MainViewController.drawerTmp.setVisible(false);
            /*MainViewController.img.setTranslateX(0);
            if(MainViewController.temporaryPaneTot.getWidth()>1200){
                MainViewController.img.setFitWidth(1366);
                MainViewController.img.setFitHeight(745);
            }
            else {
                MainViewController.img.setFitWidth(1233);
                MainViewController.img.setFitHeight(717);
                MainViewController.img.setTranslateX(0);
            }*/
            
        } catch (IOException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void switchPane(String pane) {
        try {
            
            MainViewController.temporaryPane.getChildren().clear();
            StackPane stackPane = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> elements = stackPane.getChildren();
            MainViewController.temporaryPane.getChildren().setAll(elements);
            MainViewController.drawerTmp.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
