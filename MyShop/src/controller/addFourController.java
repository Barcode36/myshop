/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import static controller.CaissePaneController.clientNew;
import entites.Stock;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
public class addFourController implements Initializable {

   
    @FXML
    private TextField fourNameField;
    @FXML
    private TextField fourCtcField;
    
    @FXML
    private Button saveBtn;
    
    @FXML
    private Button closeBtn;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }

   
    @FXML
    private void saveFour(ActionEvent event) {
        
        newStockController.lFour.add(fourNameField.getText()+ "("+fourCtcField.getText()+")");
        
        Stage st = (Stage) saveBtn.getScene().getWindow();
        st.close();
    }

    @FXML
    private void annuler(ActionEvent event) {
        Stage s = (Stage) closeBtn.getScene().getWindow();
        s.close();
    }
    
   

}
