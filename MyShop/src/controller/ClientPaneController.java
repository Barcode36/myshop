/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entites.Client;
import entites.Produit;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
public class ClientPaneController implements Initializable {
    
    @FXML
    private AnchorPane stage;
    @FXML
    private BorderPane gp;
    @FXML
    private AnchorPane cont;
    @FXML
    private AnchorPane pane1;
    @FXML
    private JFXTextField txtNomClt;
    @FXML
    private JFXTextField txtAdrClt;
    @FXML
    private JFXButton saveUp;
    @FXML
    private AnchorPane pane2;
    @FXML
    private TableView<ClientR> ClientTable;
    @FXML
    private TableColumn<ClientR, String> nomCltCol;
    @FXML
    private TableColumn<ClientR, String> adrCltCol;
    @FXML
    private TableColumn<ClientR, String> adrNumCol;
    @FXML
    private TableColumn<ClientR, Double> nbPointsCol;
    @FXML
    private Label ent;
    
    private IClientService clientService = MainViewController.clientService;
    private ObservableList<ClientR> listC = FXCollections.observableArrayList();
    
    private Client cltModif = new Client();
    @FXML
    private JFXTextField txtNumClt;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadClient();
    }
    
    private List<Client> clientList() {
        return clientService.findAll();
    }
    
    private void loadClient() {
        listC.clear();
        for (Client c : clientList()) {
            listC.add(new ClientR(c));
        }
        nomCltCol.setCellValueFactory(celldate -> celldate.getValue().getNomClt());
        adrNumCol.setCellValueFactory(celldate -> celldate.getValue().getNumClt());
        adrCltCol.setCellValueFactory(celldate -> celldate.getValue().getAdrClt());
        nbPointsCol.setCellValueFactory(celldate -> celldate.getValue().getNbPoints().asObject() );
        ClientTable.setItems(listC);
    }
    
    @FXML
    private void saveProd(ActionEvent event) {
        if(txtNomClt.getText().isEmpty() || txtAdrClt.getText().isEmpty() || txtNumClt.getText().isEmpty()){
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Veuillez remplir tous les champs ", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(1));
        } else 
            if (saveUp.getText().equals("Ajouter")) {

                Client c = new Client(
                    txtNomClt.getText(),
                    txtAdrClt.getText(),
                    txtNumClt.getText(),
                    "actif",
                    0.0
                );
                try {
                    Client clt = clientService.findByNom(c);
                    TrayNotification notification = new TrayNotification();
                    notification.setAnimationType(AnimationType.POPUP);
                    notification.setTray("MyShop", "Ce Client existe déjà", NotificationType.WARNING);
                    notification.showAndDismiss(Duration.seconds(1));
                } catch (Exception e) {
                    //c.setNbPoints(0.0);
                    //c.setEtatClt();
                    clientService.ajouter(c);
                    clearTxt();
                    txtNomClt.setFocusTraversable(true);
                    txtNomClt.requestFocus();
                    TrayNotification notification = new TrayNotification();
                    notification.setAnimationType(AnimationType.POPUP);
                    notification.setTray("MyShop", "Enregistrement effectué", NotificationType.SUCCESS);
                    notification.showAndDismiss(Duration.seconds(1));

                }

            } else {
                cltModif.setAdrClt(txtAdrClt.getText());
                cltModif.setNomClt(txtNomClt.getText());
                cltModif.setNumClt(txtNumClt.getText());
                clientService.modifier(cltModif);
                saveUp.setText("Modifier");
                TrayNotification notification = new TrayNotification();
                notification.setAnimationType(AnimationType.POPUP);
                notification.setTray("MyShop", "Modiffication effectuée", NotificationType.SUCCESS);
                notification.showAndDismiss(Duration.seconds(1));
                txtNomClt.setFocusTraversable(true);
                txtNomClt.requestFocus();
                clearTxt();
            }
        loadClient();
        
    }
    
    private void clearTxt() {
        txtAdrClt.clear();
        txtNomClt.clear();
        txtNumClt.clear();
        saveUp.setText("Ajouter");
    }
    
    @FXML
    private void suppProduit(ActionEvent event) {
        cltModif.setEtatClt("inactif");
        clientService.modifier(cltModif);
        saveUp.setText("Ajouter");
        loadClient();
        clearTxt();
    }
    
    @FXML
    private void vider(ActionEvent event) {
        clearTxt();
    }
    
    @FXML
    private void getProduitFromTable(MouseEvent event) {
        Client c = new Client(ClientTable.getSelectionModel().getSelectedItem().getIdClt().getValue());
        cltModif = clientService.findById(c);
        txtAdrClt.setText(cltModif.getAdrClt());
        txtNomClt.setText(cltModif.getNomClt());
        txtNumClt.setText(cltModif.getNumClt());
        
        saveUp.setText("Modifier");
    }
    
}
