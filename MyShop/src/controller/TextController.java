/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entites.Client;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import modele.ClientR;
import service.IClientService;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class TextController implements Initializable {

    @FXML
    private ComboBox<ClientR> comb ;

    private IClientService clientService = MainViewController.clientService;
    private ObservableList<ClientR> listC = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    
    private List<Client> clientList() {
        return clientService.findAll();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         listC.clear();
        for (Client c : clientList()) {
            listC.add(new ClientR(c));
        }
        comb.setItems(listC);
        new AutoCompleteComboBoxListener<>(comb);
    }

}
