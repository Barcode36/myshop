/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import entites.Client;
import entites.Compte;
import entites.ContenirVente;
import entites.Produit;
import entites.Vente;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modele.ClientR;
import modele.ProduitR;
import modele.VenteR;
import service.IClientService;
import service.IContenirVente;
import service.IProduitService;
import service.IVenteService;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class BilanClientController implements Initializable {

    @FXML
    private TableView<ProduitR> tableDetailCaissier;
    @FXML
    private TableColumn<ProduitR, String> DateCaissier;
    @FXML
    private TableColumn<ProduitR, String> ProduitCaissier;
    @FXML
    private TableColumn<ProduitR, Integer> QteCaissierCol;
    @FXML
    private TableColumn<ProduitR, String> PuCaissierCol;
    @FXML
    private TableColumn<ProduitR, String> totDetCaissierCol;
    @FXML
    private TableColumn<ClientR, Double> totPointsCol;
    @FXML
    private JFXComboBox<ClientR> cltCombo;

    ObservableList<VenteR> venteList = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVentCaissier = FXCollections.observableArrayList();
    private ObservableList<ClientR> listC = FXCollections.observableArrayList();

    IVenteService venteService = MainViewController.venteService;
    IContenirVente contenirVenteService = MainViewController.contenirVenteService;
    IProduitService produitService = MainViewController.produitService;
    private IClientService clientService = MainViewController.clientService;

    private List<Client> clientList() {
        return clientService.findAll();
    }

    private void fillCombo() {
        listC.clear();
        for (Client c : clientList()) {
            listC.add(new ClientR(c));
        }
        cltCombo.setItems(listC);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillCombo();

        cltCombo.setOnKeyReleased((event) -> {
            listC.clear();
            Client c = new Client();
            c.setNomClt(cltCombo.getJFXEditor().getText());
            c.setNumClt(cltCombo.getJFXEditor().getText());
            List<Client> l = clientService.recLikeNomOrNum(c);
            for (Client clt : l) {
                listC.add(new ClientR(clt));
            }
            cltCombo.setItems(listC);
        });

    }

    public void loadVenteCaissierDetail(ClientR cr) {
        produitListVentCaissier.clear();
        Client c = new Client(cr.getIdClt().getValue());
        Client clt = clientService.findById(c);
        List<Vente> list = venteService.ventesParClient(clt);
        for (Vente v : list) {
            List<ContenirVente> listCon = contenirVenteService.listParVente(v);
            for (ContenirVente cv : listCon) {
                try {
                    Produit p = new Produit(cv.getIdProd());
                    Produit produit = produitService.findById(p);
                    produitListVentCaissier.add(new ProduitR(produit, v, cv, clt));
                } catch (Exception e) {
                }
            }
        }

//       
        totPointsCol.setCellValueFactory(cellData -> cellData.getValue().getNbPoints().asObject());
        totDetCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getTotal());
        PuCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        QteCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getQteProdCom().asObject());
        DateCaissier.setCellValueFactory(cellData -> cellData.getValue().getDateVen());
        ProduitCaissier.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        tableDetailCaissier.setItems(produitListVentCaissier);
    }

    @FXML
    private void loadTable(ActionEvent event) {
        ClientR cr = cltCombo.getValue();
        loadVenteCaissierDetail(cr);
    }

}
