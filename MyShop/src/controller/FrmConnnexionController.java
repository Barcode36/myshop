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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.CompteR;
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
    private GridPane cont;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCompteCombo();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s = (Stage) cont.getScene().getWindow();
                if (s.isMaximized()) {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                }

                cont.setPrefWidth(MainViewController.temporaryPaneTot.getPrefWidth() - 922);
            }
        });
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
            StackPane dashBoard = FXMLLoader.load(getClass().getResource(Constants.DashBoard));
            MainViewController.temporaryPane.getChildren().setAll(dashBoard);
            MainViewController.hamburgerTmp.setVisible(true);
            MainViewController.initialise = true;
            if (MainViewController.initialise == true) {
                if (!MainViewController.typeCompteActif.getLibTyp().equals("Administrateur")) {
                    MenuLateraleController.btnComp.setDisable(true);
                    DashBoardController.btnComp.setDisable(true);
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
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
