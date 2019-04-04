/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entites.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.ClientR;
import service.IClientService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class ClientDemiFormController implements Initializable {

    @FXML
    private GridPane pane1;
    @FXML
    private JFXTextField txtNomClt;
    @FXML
    private JFXTextField txtAdrClt;
    @FXML
    private JFXButton saveUp;

    private IClientService clientService = MainViewController.clientService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void clearTxt() {
        txtAdrClt.clear();
        txtNomClt.clear();
    }

    @FXML
    private void saveProd(ActionEvent event) {
        Client c = new Client();
        c.setAdrClt(txtAdrClt.getText());
        c.setNomClt(txtNomClt.getText());
        try {
            clientService.findByNom(c);
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Ce Client existe déjà", NotificationType.WARNING);
            notification.showAndDismiss(Duration.seconds(1));
        } catch (Exception e) {
            clientService.ajouter(c);
            ClientR cr = new ClientR(c);
            CaissePaneController.clientNew = cr;
        }
        Stage s = (Stage) saveUp.getScene().getWindow();
        s.close();
    }

    @FXML
    private void vider(ActionEvent event) {
        clearTxt();
    }

}
