/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import static controller.CaissePaneController.clientNew;
import static controller.HistoriqueController.tableDetailCaissierSt;
import entites.Client;
import entites.Compte;
import entites.ContenirVente;
import entites.Produit;
import entites.Vente;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.ClientR;
import modele.ProduitR;
import modele.VenteR;
import service.IClientService;
import service.ICompteService;
import service.IContenirVente;
import service.IProduitService;
import service.IVenteService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class DetailVenteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private AnchorPane stge;
    @FXML
    private JFXComboBox caisCombo;
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
    private TableColumn<ProduitR, String> cltCol;
    
    ObservableList<ClientR> obClienR = FXCollections.observableArrayList();

    IVenteService venteService = MainViewController.venteService;
    IContenirVente contenirVenteService = MainViewController.contenirVenteService;
    IProduitService produitService = MainViewController.produitService;
    IProduitService produitServiceCai = MainViewController.produitService;
    ICompteService compteService = MainViewController.compteServiceD;

    ObservableList<VenteR> venteList = FXCollections.observableArrayList();
    ObservableList<VenteR> vtList = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVentCaissier = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVentCaissierCai = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVentCaissierVte = FXCollections.observableArrayList();
    ObservableList<String> listMois = FXCollections.observableArrayList();
     ObservableList<String> listCais = FXCollections.observableArrayList();
     ObservableList<String> listVteNew = FXCollections.observableArrayList();
    
    public static JFXComboBox caisComboSt;
    public static TableView<ProduitR> tableDetailCaissierSt;
    public static AnchorPane stage;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        stage = stge;
        
        caisCombo.setItems(HistoriqueController.caisComboSt.getItems());
        
        
    }

   
    public void loadVenteCaissier() {
        venteList.clear();
        List<Compte> listc = compteService.compteList();
        for (Compte c : listc) {
            List<Vente> listVParCaissier =venteService.ventes();
            
            int totVent = 0;
            for (Vente vente : listVParCaissier) {

                List<ContenirVente> listCon = contenirVenteService.listParVente(vente);
                System.out.println(listCon);
                for (ContenirVente cv : listCon) {
//                    Produit p = new Produit(cv.getIdProd());
//                    Produit produit = produitService.findById(p);
                    int totPro = cv.getPrixProd() * cv.getQteVen();
                    totVent += totPro;
                }

            }
            if (!listVParCaissier.isEmpty()) {
                venteList.add(new VenteR(c, totVent));
            }
        }
        
        
    }
    
    
    public void loadVenteCaissierDetail(VenteR venteR) {
        produitListVentCaissierCai.clear();
        produitListVentCaissierVte.clear();
        Compte c = new Compte(venteR.getidCompte().getValue());
        Compte compte = compteService.findById(c);
        List<Vente> list = venteService.ventesParCaissier(compte);
         int i = 0;
        for (Vente v : list) {
            Client cl = new Client(v.getIdClt());
            Client clt = MainViewController.clientService.findById(cl);
            List<ContenirVente> listCon = contenirVenteService.listParVente(v);
           
            for (ContenirVente cv : listCon) {
                
                
                try {
                    produitListVentCaissierVte.add(new ProduitR(v, cv, clt));
                    
                    
                    Produit p = new Produit(cv.getIdProd());
                    Produit produit = produitServiceCai.findById(p);
                    produitListVentCaissierCai.add(new ProduitR(produit, v, cv, clt));
                    
                } catch (Exception e) {
                }
                 
            }
            
        }
        
        totDetCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getTotal());
        PuCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        QteCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getQteProdCom().asObject());
        DateCaissier.setCellValueFactory(cellData -> cellData.getValue().getDateVen());
        ProduitCaissier.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        
        cltCol.setCellValueFactory(cellData -> cellData.getValue().getCltAch());
        
        tableDetailCaissier.setItems(produitListVentCaissierCai);
        tableDetailCaissierSt = tableDetailCaissier;
        tableDetailCaissierSt.setItems(tableDetailCaissier.getItems());
        //System.out.println("pdtr: "+tableDetailCaissierSt.getSelectionModel());
        //for(ProduitR pdtr: tableDetailCaissierSt.getItems()){
        //    System.out.println("pdtr: "+pdtr.getLibProd());
        //}
    }

    @FXML
    private void loadTableCaissier(ActionEvent event) {
        loadVenteCaissier(); 
         
        //vtList = CaissierVenteTable.getItems();
        for(VenteR vt : venteList){
            if(vt.getCaissier().getValue().equalsIgnoreCase(caisCombo.getSelectionModel().getSelectedItem()+"")){
               
                loadVenteCaissierDetail( vt );
            }
        }
        
    }
    
    
}
