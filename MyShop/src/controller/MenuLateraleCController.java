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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class MenuLateraleCController implements Initializable {

    @FXML
    private JFXButton btnAccueil;
    @FXML
    private JFXButton btnRec;
    @FXML
    private JFXButton btnCaisse;
    @FXML
    private JFXButton btnAide;
    @FXML
    private JFXButton btnQuitter;
    @FXML
    private VBox dash;
    
    public static VBox dashB ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        MainViewController.dshPane.setVisible(true);
        
    }

    public  double  getDashWidth() {
        dashB = dash;
        return dashB.getWidth();
    }
    
    

    @FXML
    private void openAccueil(ActionEvent event) {
        
        switchPane(Constants.DashBoard);
        if (!MainViewController.typeCompteActif.getLibTyp().equals("Administrateur")) {
            DashBoardController.contHbox1.getChildren().removeAll(DashBoardController.btnComp,
                            DashBoardController.btnInvent,DashBoardController.btnBil,DashBoardController.btnReg);
                    
                    DashBoardController.contHbox1.getChildren().addAll(DashBoardController.btnAid,
                            DashBoardController.btnDec);
                    
                    DashBoardController.contHbox2.getChildren().clear();
        } else {

        }
    }

    @FXML
    private void openInventaire(ActionEvent event) {
        switchPane(Constants.CrudInventaire);
        

    }

    @FXML
    private void openComptes(MouseEvent event) {
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
            //MainViewController.img.setTranslateX(0);
            /*if(MainViewController.temporaryPaneTot.getWidth()>1200){
                MainViewController.img.setFitWidth(1366);
                MainViewController.img.setFitHeight(745);
            }else {
                MainViewController.img.setFitWidth(1233);
                MainViewController.img.setFitHeight(717);
                MainViewController.img.setTranslateX(0);
            }*/
            
        } catch (IOException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println(" "+ex);
        }
    }

    private void switchPane(String pane) {
        try {
            MainViewController.temporaryPane.getChildren().clear();
            StackPane stackPane = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> elements = stackPane.getChildren();
            MainViewController.temporaryPane.getChildren().setAll(elements);
            MainViewController.drawerTmp.setVisible(true);
            
            if (!MainViewController.typeCompteActif.getLibTyp().equals("Administrateur")) {
                DashBoardController.btnComp.setVisible(false);
                DashBoardController.btnInvent.setVisible(false);
                DashBoardController.btnBil.setVisible(false);
                DashBoardController.btnReg.setVisible(false);
            } else {

            }
        } catch (IOException ex) {
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
