/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import static controller.CaissePaneController.clientNew;
import entites.Client;
import entites.Compte;
import entites.ContenirVente;
import entites.Produit;
import entites.Vente;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
public class HistoriqueController implements Initializable {

    
     @FXML
    private TableView<ProduitR> tableDetailCaissier;
     public static TableView<ProduitR> tableDetailCaissierSt;
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
    private TableView<ProduitR> tableDetailClient;
     
    @FXML
    private TableColumn<ProduitR, String> DateCaissierCl;
    @FXML
    private TableColumn<ProduitR, String> ProduitCaissierCl;
    @FXML
    private TableColumn<ProduitR, Integer> QteCaissierColCl;
    @FXML
    private TableColumn<ProduitR, String> PuCaissierColCl;
    @FXML
    private TableColumn<ProduitR, String> totDetCaissierColCl;
    @FXML
    private JFXComboBox<ClientR> cltCombo;
    
    
    @FXML
    private TableView<VenteR> tableDetailVenteNew;
    @FXML
    private TableColumn<VenteR, Integer> NumVente;
    @FXML
    private TableColumn<VenteR, String> dateVente;
    @FXML
    private TableColumn<VenteR, Double> mtVente;
    @FXML
    private TableColumn<VenteR, String> cltVente;
    
    IClientService clientService = MainViewController.clientService;

    ObservableList<ClientR> obClienR = FXCollections.observableArrayList();

    IVenteService venteService = MainViewController.venteService;
    IContenirVente contenirVenteService = MainViewController.contenirVenteService;
    
    IProduitService produitService = MainViewController.produitService;
    IProduitService produitServiceCai = MainViewController.produitService;
    ICompteService compteService = MainViewController.compteServiceD;

    ObservableList<VenteR> venteList = FXCollections.observableArrayList();
    ObservableList<VenteR> vtList = FXCollections.observableArrayList();
    ObservableList<VenteR> vtListTable = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVentCaissier = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVentCaissierCai = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVentCaissierVte = FXCollections.observableArrayList();
    ObservableList<String> listMois = FXCollections.observableArrayList();
     ObservableList<String> listCais = FXCollections.observableArrayList();
     ObservableList<String> listVteNew = FXCollections.observableArrayList();
    // IContenirVente contenirVenteServiceN = MainViewController.contenirVenteService;
    @FXML
    private GridPane gridBilan;
    @FXML
    private TableColumn<VenteR, String> caissierColVente;
    @FXML
    private TableColumn<VenteR, Integer> totCaissierColVente;
    @FXML
    private TableView<VenteR> CaissierVenteTable;
    
    @FXML
    private TableColumn<ProduitR, String> cltCol;
    
    
    private ObservableList<ClientR> listC = FXCollections.observableArrayList();
    //ObservableList<VenteR> venteList = FXCollections.observableArrayList();
    //private ObservableList<ClientR> listC = FXCollections.observableArrayList();
    
    
    
    @FXML
    private JFXComboBox caisCombo;
    
     public static JFXComboBox caisComboSt;
    
    
   

     ObservableList<String> compteList = FXCollections.observableArrayList();
     ObservableList<Compte> compteListe = FXCollections.observableArrayList();
     
     private List<Client> clientList() {
        return clientService.findAll();
    }
     
      public List<Compte> listCompte() {
        return MainViewController.compteServiceD.compteList();
    }
   
    private void fillComboClient() {
        listC.clear();
        for (Client c : clientList()) {
            listC.add(new ClientR(c));
        }
        cltCombo.setItems(listC);
    }
     
    private void fillComboCaissier() {
        compteList.clear();
        
        for (Compte c : listCompte()) {
            if(c.getIdTypComp()==3){
                compteList.add(  c.getNomComp()+" "+c.getPrenomComp());
                compteListe.add(c);
            }
        }
        
        caisCombo.setItems(compteList);
        caisComboSt = caisCombo;
        caisComboSt.setItems(caisCombo.getItems());
        caisComboSt.setSelectionModel(caisCombo.getSelectionModel());
        System.out.println("sel: "+caisComboSt.getSelectionModel().getSelectedItem());
    }
    
    
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillComboClient();
        fillComboCaissier();
        loadVenteCaissier();

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
        
        caisCombo.setOnKeyReleased((event) -> {
            compteListe.clear();
            Compte c = new Compte();
            c.setPseudoComp(cltCombo.getJFXEditor().getText());
            //c.setNumClt(cltCombo.getJFXEditor().getText());
            List<Compte> lc = (List<Compte>) compteService.findById(c);
            for (Compte cpt : lc) {
                Integer id  = cpt.getIdComp();
                compteListe.add(new Compte(id));
            }
            caisCombo.setItems(compteListe);
        });

    }
    
    public void loadVenteCaissierClient(ClientR cr) {
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
        totDetCaissierColCl.setCellValueFactory(cellData -> cellData.getValue().getTotal());
        PuCaissierColCl.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        QteCaissierColCl.setCellValueFactory(cellData -> cellData.getValue().getQteProdCom().asObject());
        DateCaissierCl.setCellValueFactory(cellData -> cellData.getValue().getDateVen());
        ProduitCaissierCl.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        tableDetailClient.setItems(produitListVentCaissier);
        
    }

    @FXML
    private void loadTableClient(ActionEvent event) {
        ClientR cr = cltCombo.getValue();
        loadVenteCaissierClient(cr);
    }

    // **************************************
    //  table caissier
    //***************************************
    
    
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
        caissierColVente.setCellValueFactory(cellData -> cellData.getValue().getCaissier());
        totCaissierColVente.setCellValueFactory(cellData -> cellData.getValue().getTotalCaissier().asObject());
        CaissierVenteTable.setItems(venteList);
//      
        
    }
    
    
    public void loadVenteCaissierDetail(VenteR venteR) {
        produitListVentCaissierCai.clear();
        produitListVentCaissierVte.clear();
        Compte c = new Compte(venteR.getidCompte().getValue());
        Compte compte = compteService.findById(c);
        List<Vente> list = venteService.ventesParCaissier(compte);
        vtListTable.clear();
        
         int i = 0;
        for (Vente v : list) {
            Client cl = new Client(v.getIdClt());
            Client clt = MainViewController.clientService.findById(cl);
            List<ContenirVente> listCon = contenirVenteService.listParVente(v);
           
            for (ContenirVente cv : listCon) {
                
                
                try {
                    vtListTable.add(new VenteR(compte, v, cv.getMontVente(), clt, cv.getDtVente()));
                    produitListVentCaissierVte.add(new ProduitR(v, cv, clt));
                    
                    
                    Produit p = new Produit(cv.getIdProd());
                    Produit produit = produitServiceCai.findById(p);
                    produitListVentCaissierCai.add(new ProduitR(produit, v, cv, clt));
                    
                } catch (Exception e) {
                }
                 
            }
            
        }
        
       
         
        NumVente.setCellValueFactory(cellData -> cellData. getValue().getIdVen().asObject());
        dateVente.setCellValueFactory(cellData -> cellData. getValue().getDtVte());
        mtVente.setCellValueFactory(cellData -> cellData. getValue(). getMontVente().asObject());
        cltVente.setCellValueFactory(cellData -> cellData. getValue().getClient());
        tableDetailVenteNew.setItems(vtListTable);
        
         
        
        totDetCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getTotal());
        PuCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        QteCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getQteProdCom().asObject());
        DateCaissier.setCellValueFactory(cellData -> cellData.getValue().getDateVen());
        ProduitCaissier.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        
        cltCol.setCellValueFactory(cellData -> cellData.getValue().getCltAch());
        
        tableDetailCaissier.setItems(produitListVentCaissierCai);
        tableDetailCaissierSt = tableDetailCaissier;
        tableDetailCaissierSt.setItems(tableDetailCaissier.getItems());
        
    }

    @FXML
    private void loadTableCaissier(ActionEvent event) {
        loadVenteCaissier(); 
         
        vtList = CaissierVenteTable.getItems();
        for(VenteR vt : vtList){
            if(vt.getCaissier().getValue().equalsIgnoreCase(caisCombo.getSelectionModel().getSelectedItem()+"")){
                loadVenteCaissierDetail( vt );
            }
        }
        
    }
    
    @FXML
    private void getDetailVente( MouseEvent me ){
        try {
            StackPane root;
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/views/DetailVentePane.fxml").openStream());
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/essai.css").toExternalForm());
            stage.getIcons().add(new Image(CaissePaneController.class.getResourceAsStream("/img/afnacos.ico")));
            
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNIFIED);
            stage.setResizable(false);
            stage.show();
            
            

        } catch (IOException ex) {
            System.out.println(""+ex);
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
