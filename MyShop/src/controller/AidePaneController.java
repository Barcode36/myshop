/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class AidePaneController implements Initializable {

    @FXML
    private AnchorPane stage;
    @FXML
    private Label lab;
    
    boolean ok = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MainViewController.temporaryPaneTot.widthProperty().addListener((obs, oldVal, newVal)->{
            if( (Double) newVal <= (Double) oldVal){
                lab.setTranslateX(lab.getTranslateX()-2);
                    
            }
            
        });
    }    
    
}
