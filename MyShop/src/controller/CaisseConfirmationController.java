/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;
import entites.Client;
import entites.Compte;
import entites.ContenirVente;
import entites.Vente;
import entites.Produit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.ClientR;
import modele.ProduitR;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.IClientService;
import service.ICompteService;
import service.IContenirVente;
import service.IProduitService;
import service.IVenteService;
import service.imp.CompteService;
import service.imp.ContenirVenteService;
import service.imp.ProduitService;
import service.imp.VenteService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class CaisseConfirmationController extends Traitement implements Initializable {

    @FXML
    private TableView<ProduitR> produitCaisseTable;
    @FXML
    private TableColumn<ProduitR, String> libProdColCaisse;
    @FXML
    private TableColumn<ProduitR, Integer> qteColCaisse;
    @FXML
    private TableColumn<ProduitR, String> prixColCaisse;
    @FXML
    private TableColumn<ProduitR, String> totalColCaisse;
    @FXML
    private TextField txtMontCllt;
    @FXML
    private Label lblTot;
    @FXML
    private TextField txtRemise;
    @FXML
    private TextField txtPtActu;
    @FXML
    private TextField txtCoeff;
    @FXML
    private Button btnClose;

    ObservableList<ProduitR> produitListVent = FXCollections.observableArrayList();

    IVenteService venteService = new VenteService();
    ICompteService compteService = new CompteService();
    IContenirVente contenirVenteService = new ContenirVenteService();
    IProduitService produitService = new ProduitService();

    Compte compteActif = new Compte();
    ClientR clientR = new ClientR();
    private boolean corr = true;
    @FXML
    private Label Client;

     private File file;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Client.setText("Vente au client: " + clientR.getNomClt().getValue().toUpperCase()
                        + " (" + clientR.getNumClt().getValue() + ")");
                //txtPtActu.setText(clientR.getNbPoints().getValue()+" Points");
                
                txtMontCllt.setFocusTraversable(true);
                txtMontCllt.requestFocus();
                txtCoeff.setVisible(false);
                
                txtPtActu.setVisible(false);
            }
        });
        
        try{
               
               
                 file = new File("Ressources/coeff.xls");
                 
                if (file != null) {
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        XSSFWorkbook wb = new XSSFWorkbook(fis);
                        XSSFSheet sheet = wb.getSheetAt(0);
                        Row row;
                        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                            row = sheet.getRow(i);
                            System.out.println(""+row.getCell(0).getStringCellValue());
                           // txtCoeff.setText(row.getCell(0).getStringCellValue());
                        }
                        wb.close();
                        fis.close();
                    } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainPrincipalController.class
                            .getName()).log(Level.SEVERE, null, ex);

                    } 
                }
                
            } catch (IOException e) { 
                System.out.println(""+e);
               // e.printStackTrace(); 
            } 
        
      
    }

    @FXML
    private void SupprimerProdVent(ActionEvent event) {
    }

    public void setListProd(ObservableList<ProduitR> list, Compte c, ClientR cr) {
        int tot = 0;
        for (ProduitR pr : list) {
            pr.setQteProdCom(new SimpleIntegerProperty(Integer.parseInt(pr.getQteCom().getText())));
            int t = Integer.parseInt(pr.getQteCom().getText()) * Integer.parseInt(pr.getPrixUniProd().getValue());
            pr.setTotal(new SimpleStringProperty(String.valueOf(t)));
            produitListVent.add(pr);
            int totprod = Integer.parseInt(pr.getPrixUniProd().getValue()) * Integer.parseInt(pr.getQteCom().getText());
            tot += totprod;
        }
        lblTot.setText(String.valueOf(tot));
        libProdColCaisse.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        prixColCaisse.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        qteColCaisse.setCellValueFactory(cellData -> cellData.getValue().getQteProdCom().asObject());
        totalColCaisse.setCellValueFactory(cellData -> cellData.getValue().getTotal());
        produitCaisseTable.setItems(produitListVent);
        compteActif = c;
        clientR = cr;

    }

    private IClientService clientService = MainViewController.clientService;
    
    @FXML
    private void Valider(ActionEvent event) {
        Boolean txtNumber = textFieldTypeNumber(txtMontCllt);
        if (txtNumber) {
            if (corr == true) {
                Vente vente = new Vente();
                vente.setDateVen(new Date());
                vente.setIdClt(clientR.getIdClt().getValue());
               
                Client c = new Client(clientR.getIdClt().getValue());
                Client clt = clientService.findById(c);
                clt.setNbPoints(clt.getNbPoints()+ Double.parseDouble(lblTot.getText()) *  10/100 );
                clientService.modifier(clt);
                vente.setIdComp(compteActif.getIdComp());
                venteService.ajouter(vente);
                for (ProduitR pr : produitListVent) {
                    ContenirVente contenirVente = new ContenirVente();
                    contenirVente.setQteVen(pr.getQteProdCom().getValue());
                    contenirVente.setIdVen(vente.getIdVen());
                    contenirVente.setIdProd(pr.getIdProd().getValue());
                    contenirVente.setPrixProd(Integer.parseInt(pr.getPrixUniProd().getValue()));
                    contenirVente.setMontVente(Double.parseDouble(lblTot.getText()));
                    contenirVente.setDtVente(new Date());
                    
                    contenirVenteService.ajouterContenirVente(contenirVente);
                    
                    Produit p = new Produit(pr.getIdProd().getValue());
                    Produit produit = produitService.findById(p);
                    produit.setQteIniProd(produit.getQteIniProd() - pr.getQteProdCom().getValue());

                    produitService.modifier(produit);
                }
                CaissePaneController.vente = true;
                TrayNotification notification = new TrayNotification();
                notification.setAnimationType(AnimationType.POPUP);
                notification.setTray("MyShop", "Vente Effectuée", NotificationType.SUCCESS);
                notification.showAndDismiss(Duration.seconds(1.5));
                Stage stage = (Stage) btnClose.getScene().getWindow();
                stage.close();
                switchPane(Constants.Caisse);
            } else {
                TrayNotification notification = new TrayNotification();
                notification.setAnimationType(AnimationType.POPUP);
                notification.setTray("MyShop", "Montant incorrect", NotificationType.ERROR);
                notification.showAndDismiss(Duration.seconds(1.5));
            }
        } else {
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Montant doit etre numerique", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(1.5));
        }

    }

    private void switchPane(String pane) {
        try {
            MainViewController.temporaryPane.getChildren().clear();
            StackPane stackPane = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> elements = stackPane.getChildren();

            MainViewController.temporaryPane.getChildren().setAll(elements);

            MainViewController.drawerTmp.setVisible(true);
            // MainViewController.hamburgerTmp = new JFXHamburger();
        } catch (IOException ex) {
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void calculRemise(KeyEvent event) {
        int rem = 0;
        if (!txtMontCllt.getText().equals("")) {
            try {
                rem = Integer.parseInt(txtMontCllt.getText()) - Integer.parseInt(lblTot.getText());
                txtRemise.setText(String.valueOf(rem));
                
                
                if( Double.parseDouble( txtMontCllt.getText()) > Double.parseDouble(lblTot.getText()) ){
                     txtMontCllt.setStyle("Background-color: lightgreen;");
                }
                
            } catch (Exception e) {
            }

        } else {
            txtRemise.clear();
        }
        if (rem < 0 || txtRemise.getText().equals("") ) {
            corr = false;
            txtMontCllt.setStyle(" -fx-background-color: red; "); ;
        } else {
            corr = true;
            txtMontCllt.setStyle("-fx-background-color: lightgreen;");
        }
        
    }

}
