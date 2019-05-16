/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import static controller.CaissePaneController.clientNew;
import entites.Client;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modele.ClientR;
import service.IClientService;
import service.imp.ClientService;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class FrmChoixClientController implements Initializable {

    @FXML
    private JFXTextField txtRec;
    @FXML
    private TableView<ClientR> clientTable;
    @FXML
    private TableColumn<ClientR, String> colNom;
    @FXML
    private TableColumn<ClientR, String> colAdr;
    @FXML
    private TableColumn<ClientR, String> colNum;

    IClientService clientService = MainViewController.clientService;

    ObservableList<ClientR> obClienR = FXCollections.observableArrayList();

    public List<Client> lClient() {
        return clientService.findAll();
    }

    private void loadTable() {
        obClienR.clear();
        for (Client c : lClient()) {
            obClienR.add(new ClientR(c));
        }
        clientTable.setItems(obClienR);
        colNom.setCellValueFactory(celldata -> celldata.getValue().getNomClt());
        colAdr.setCellValueFactory(celldata -> celldata.getValue().getAdrClt());
        colNum.setCellValueFactory(celldata -> celldata.getValue().getNumClt());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTable();
    }

    @FXML
    private void recherche(KeyEvent event) {
        obClienR.clear();
        Client c = new Client();
        c.setNomClt(txtRec.getText());
        c.setNumClt(txtRec.getText());
        List<Client> l = clientService.recLikeNomOrNum(c);
        for (Client clt : l) {
            obClienR.add(new ClientR(clt));
        }
        clientTable.setItems(obClienR);
        colNom.setCellValueFactory(celldata -> celldata.getValue().getNomClt());
        colAdr.setCellValueFactory(celldata -> celldata.getValue().getAdrClt());
        colNum.setCellValueFactory(celldata -> celldata.getValue().getNumClt());

    }

    @FXML
    private void ChoixProd(MouseEvent event) {
        if (clientTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        CaissePaneController.clientNew = clientTable.getSelectionModel().getSelectedItem();
        Stage s = (Stage) clientTable.getScene().getWindow();
        s.close();
    }

    @FXML
    private void nouveauClt(ActionEvent event) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/views/ClientDemiForm.fxml").openStream());

            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/essai.css").toExternalForm());
            stage.getIcons().add(new Image(CaissePaneController.class.getResourceAsStream("/img/afnacos.ico")));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNIFIED);
            stage.setResizable(false);
            stage.show();
            Stage s = (Stage) clientTable.getScene().getWindow();
            s.close();
            stage.setOnHidden(ex -> {
//                cltCombo.setValue(clientNew);
//                if (this.newClt == true) {
//
//                }

            });

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
