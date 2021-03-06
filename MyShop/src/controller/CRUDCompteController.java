/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entites.Compte;
import entites.TypeCompte;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.SwingUtilities;
import modele.CompteR;
import modele.TypeCompteR;
import service.ICompteService;
import service.ITypeService;
import service.imp.CompteService;
import service.imp.ProduitService;
import service.imp.TypeService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class CRUDCompteController implements Initializable {

    @FXML
    private JFXTextField txtPassword;
    @FXML
    private TableView<CompteR> CompteTable;
    @FXML
    private TableColumn<CompteR, String> nomCompCol;
    @FXML
    private TableColumn<CompteR, String> prenomCompcol;
    @FXML
    private TableColumn<CompteR, String> pseudoCompCol;
    @FXML
    private TableColumn<CompteR, String> mdpCompCol;
    @FXML
    private TableColumn<CompteR, String> rolCompCol;
    @FXML
    private TableColumn<CompteR, String> etatCompCol;
    @FXML
    private JFXButton saveUp = new JFXButton();
    @FXML
    private TextField txtNomComp;
    @FXML
    private TextField txtPseudoComp;
    @FXML
    private TextField txtPrenomComp;
    @FXML
    private ComboBox<TypeCompteR> typeCompteCombo;

    Compte compteModif = new Compte();

    ObservableList<CompteR> compteList = FXCollections.observableArrayList();
    ObservableList<TypeCompteR> typeCompteList = FXCollections.observableArrayList();

    ICompteService compteService = MainViewController.compteServiceD;
    ITypeService typeService = MainViewController.typeServiceD;
    @FXML
    private AnchorPane stage;
    @FXML
    private GridPane cont;
    @FXML
    private ColumnConstraints consCol1;
    @FXML
    private ColumnConstraints consCol2;
    
    /**
     * Initializes the controller class.
     */
    private boolean ok = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
     
        
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
        loadCompteGrid();
        loadTypeCompteCombo();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s = (Stage) stage.getScene().getWindow();
                if (s.isMaximized()) {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                }
                
            }
        });
        
    }

    @FXML
    private void saveProd(ActionEvent event) {
//       
        if(txtNomComp.getText().isEmpty() || txtPrenomComp.getText().isEmpty() || txtPseudoComp.getText().isEmpty() || 
                txtPassword.getText().isEmpty()||typeCompteCombo.getSelectionModel().isEmpty()){
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Veuillez remplir tous les champs ", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(1));
        } else
        if (saveUp.getText().equals("Ajouter")) {
            Compte compte = new Compte();
            compte.setMdpComp(txtPassword.getText());
            compte.setNomComp(txtNomComp.getText());
            compte.setPrenomComp(txtPrenomComp.getText());
            compte.setPseudoComp(txtPseudoComp.getText());
            compte.setEtatComp("actif");
            compte.setIdTypComp(typeCompteCombo.getSelectionModel().getSelectedItem().getIdTyp().getValue());
            compteService.ajouter(compte);
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Ajout Effectué avec succès ", NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.seconds(1));
        } else {
            saveUp.setText("Modifier");
            compteModif.setMdpComp(txtPassword.getText());
            compteModif.setNomComp(txtNomComp.getText());
            compteModif.setPrenomComp(txtPrenomComp.getText());
            compteModif.setPseudoComp(txtPseudoComp.getText());
            compteModif.setIdTypComp(typeCompteCombo.getSelectionModel().getSelectedItem().getIdTyp().getValue());
            compteService.modifier(compteModif);
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Modiffication effectuée", NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.seconds(1));
            
            
        }
        loadCompteGrid();
        clearCompteTxt();
    }

    public void loadCompteGrid() {
        compteList.clear();
        for (Compte compte : listCompte()) {
            TypeCompte t = new TypeCompte(compte.getIdTypComp());
            TypeCompte typeCompte = typeService.findById(t);
            compteList.add(new CompteR(compte, typeCompte));
//            combpPseudoCennect.setItems(compteList);
        }
        nomCompCol.setCellValueFactory(cellData -> cellData.getValue().getNomComp());
        prenomCompcol.setCellValueFactory(cellData -> cellData.getValue().getPrenomComp());
        pseudoCompCol.setCellValueFactory(cellData -> cellData.getValue().getPseudoComp());
        mdpCompCol.setCellValueFactory(cellData -> cellData.getValue().getMdpComp());
        rolCompCol.setCellValueFactory(cellData -> cellData.getValue().getLibTypComp());
        etatCompCol.setCellValueFactory(cellData -> cellData.getValue().getEtatComp());
        CompteTable.setItems(compteList);
    }

    private void clearCompteTxt() {
        txtNomComp.clear();
        txtPrenomComp.clear();
        txtPseudoComp.clear();
        txtPassword.clear();
        typeCompteCombo.setValue(null);
        typeCompteCombo.getEditor().clear();
        saveUp.setText("Ajouter");
    }

    public void loadTypeCompteCombo() {
        typeCompteList.clear();
        List<TypeCompte> list = typeService.typeCmopteList();
        for (TypeCompte typeCompte : list) {
            typeCompteList.add(new TypeCompteR(typeCompte));
        }
        typeCompteCombo.setItems(typeCompteList);
    }

    public List<Compte> listCompte() {
        return compteService.compteList();
    }

    public List<TypeCompte> listTypeCompte() {
        return typeService.typeCmopteList();
    }

    @FXML
    private void suppProduit(ActionEvent event) {
        compteService.supprimer(compteModif);
        
        loadCompteGrid();
        clearCompteTxt();
    }

    @FXML
    private void getCompteFromTable(MouseEvent event) {
        try {
            CompteR cr = CompteTable.getSelectionModel().getSelectedItem();
            Compte c = new Compte(cr.getIdComp().getValue());
            compteModif = compteService.findById(c);
            TypeCompte tc = new TypeCompte(compteModif.getIdTypComp());
            TypeCompte typeCompte = typeService.findById(tc);
            //System.out.println("idCpte: "+tc);
            typeCompteCombo.setValue(new TypeCompteR(typeCompte));
            txtNomComp.setText(compteModif.getNomComp());
            txtPrenomComp.setText(compteModif.getPrenomComp());
            txtPseudoComp.setText(compteModif.getPseudoComp());
            txtPassword.setText(compteModif.getMdpComp());
            saveUp.setText("Modifier");
             //clearCompteTxt();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       
    }

    @FXML
    private void vider(ActionEvent event) {
        clearCompteTxt();
    }

}
