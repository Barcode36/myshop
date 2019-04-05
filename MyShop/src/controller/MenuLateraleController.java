/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
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
import javafx.scene.layout.StackPane;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            StackPane dashBoard = FXMLLoader.load(getClass().getResource(Constants.Connect));
            MainViewController.temporaryPane.getChildren().setAll(dashBoard);
        } catch (IOException ex) {
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        System.exit(0);
    }

    private void switchPane(String pane) {
        try {
            MainViewController.temporaryPane.getChildren().clear();
            StackPane stackPane = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> elements = stackPane.getChildren();
            MainViewController.temporaryPane.getChildren().setAll(elements);
            MainViewController.drawerTmp.close();
          //  MainViewController.hamburgerTmp = new JFXHamburger();
        } catch (IOException ex) {
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
