/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entites.Client;
import entites.Compte;
import entites.Produit;
import entites.TypeCompte;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modele.ClientR;
import modele.ProduitR;
import service.IClientService;
import service.IProduitService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class CaissePaneController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField txtCodeProdCaisse;
    @FXML
    private JFXTextField txtNomProdCaisse;
    @FXML
    private JFXTextField txtQteProdCaisse;
    @FXML
    private JFXTextField txtPrixUnitCaisse;
    @FXML
    private VBox pane2;
    @FXML
    private TableView<ProduitR> produitCaisseTable;
    @FXML
    private TableColumn<ProduitR, String> libProdColCaisse;
    @FXML
    private TableColumn<ProduitR, JFXTextField> qteColCaisse;
    @FXML
    private TableColumn<ProduitR, String> prixColCaisse;
    @FXML
    private TableColumn<ProduitR, JFXCheckBox> actionColCaisse;
    @FXML
    private TableColumn<ProduitR, String> totalColCaisse;
    @FXML
    private JFXButton saveUp;

    private Produit produitVente = new Produit();
    private StringBuffer barcode = new StringBuffer();
    public static boolean vente = false;

    public static ProduitR produitChoisi;
    public static ClientR clientNew;

    IProduitService produitService = MainViewController.produitService;
    private IClientService clientService = MainViewController.clientService;
    private ObservableList<ClientR> listC = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVent = FXCollections.observableArrayList();
    @FXML
    private GridPane cont;
    @FXML
    private JFXComboBox<ClientR> cltCombo;

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
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s = (Stage) pane.getScene().getWindow();
                if (s.isMaximized()) {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                } else {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                }
                pane.setPrefWidth(MainViewController.temporaryPaneTot.getPrefWidth());
                cont.setPrefWidth(MainViewController.temporaryPaneTot.getPrefWidth() - 87);
                System.out.println(s.isMaximized());
            }
        });
        System.out.println(MainViewController.temporaryPaneTot.getPrefWidth());
        MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                pane.setPrefWidth(newValue.doubleValue());
                cont.setPrefWidth(newValue.doubleValue() - 87);

            }

        });
        fillCombo();
        txtCodeProdCaisse.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                Produit p = new Produit();
                p.setCodeProd(txtCodeProdCaisse.getText());
                try {
                    produitVente = produitService.findByCode(p);
                    txtNomProdCaisse.setText(produitVente.getLibProd());
                    txtQteProdCaisse.setText(String.valueOf(produitVente.getQteIniProd()));
                    txtPrixUnitCaisse.setText(String.valueOf(produitVente.getPrixUniProd()));
                    Boolean find = false;
                    int i = 0;
                    int e = 0;
                    for (ProduitR pr : produitListVent) {

                        if (produitVente.getCodeProd().equals(pr.getCodeProd().getValue())) {
                            find = true;
                            e = i;
                        }
                        i++;
                    }
                    if (find == false) {
                        if (produitVente.getQteIniProd() <= 0) {
                            TrayNotification notification = new TrayNotification();
                            notification.setAnimationType(AnimationType.POPUP);
                            notification.setTray("MyShop", "Produit en rupture de stock", NotificationType.ERROR);
                            notification.showAndDismiss(Duration.seconds(1));
                        } else {
                            produitListVent.add(new ProduitR(produitVente, produitListVent, produitCaisseTable, 1));
                            barcode = new StringBuffer();
                        }

                    } else if (find == true) {
                        ProduitR pm = produitListVent.get(e);
                        if (produitVente.getQteIniProd() <= Integer.parseInt(pm.getQteCom().getText())) {
                            TrayNotification notification = new TrayNotification();
                            notification.setAnimationType(AnimationType.POPUP);
                            notification.setTray("MyShop", "Stock atteint", NotificationType.ERROR);
                            notification.showAndDismiss(Duration.seconds(1));
                        } else {
                            produitListVent.remove(e);
                            produitListVent.add(new ProduitR(produitVente, produitListVent, produitCaisseTable, Integer.parseInt(pm.getQteCom().getText()) + 1));
                        }
                        // pm.setQteProdCom(new SimpleIntegerProperty(pm.getQteProdCom().getValue() + 1));

                        // produitListVent.set(e, pm);
                    }

                    libProdColCaisse.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
                    prixColCaisse.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
                    qteColCaisse.setCellValueFactory(new PropertyValueFactory<ProduitR, JFXTextField>("qteCom"));
                    actionColCaisse.setCellValueFactory(new PropertyValueFactory<ProduitR, JFXCheckBox>("suppression"));
                    totalColCaisse.setCellValueFactory(cellData -> cellData.getValue().getTotal());
                    produitCaisseTable.setItems(produitListVent);
                    txtCodeProdCaisse.clear();

                    //System.out.println(produitListVent);
                } catch (Exception e) {
                }
            }
        });

        pane.setOnKeyTyped(new EventHandler<KeyEvent>() {

            //String b = new String();
            @Override
            public void handle(KeyEvent event) {
                if (event.getCharacter().charAt(0) == (char) 0x000d) {

                    barcode.delete(0, barcode.length());
                } else {
                    barcode.append(event.getCharacter());
                }
                //  barcode.append(event.getCharacter());
                //b = b + event.getCharacter();
                System.out.println(barcode);
                caisseProduitInfo(barcode);
                //event.consume();
            }

        });

    }

    private void caisseProduitInfo(StringBuffer b) {
        Produit p = new Produit();
        p.setCodeProd(b.toString());
        try {
            produitVente = produitService.findByCode(p);
            txtNomProdCaisse.setText(produitVente.getLibProd());
            txtQteProdCaisse.setText(String.valueOf(produitVente.getQteIniProd()));
            txtPrixUnitCaisse.setText(String.valueOf(produitVente.getPrixUniProd()));
            Boolean find = false;
            int i = 0;
            int e = 0;
            for (ProduitR pr : produitListVent) {

                if (produitVente.getCodeProd().equals(pr.getCodeProd().getValue())) {
                    find = true;
                    e = i;
                }
                i++;
            }
            if (find == false) {
                if (produitVente.getQteIniProd() <= 0) {
                    TrayNotification notification = new TrayNotification();
                    notification.setAnimationType(AnimationType.POPUP);
                    notification.setTray("MyShop", "Produit en rupture de stock", NotificationType.ERROR);
                    notification.showAndDismiss(Duration.seconds(1));
                } else {
                    produitListVent.add(new ProduitR(produitVente, produitListVent, produitCaisseTable, 1));
                    barcode = new StringBuffer();
                }
            } else if (find == true) {
                ProduitR pm = produitListVent.get(e);
                if (produitVente.getQteIniProd() <= Integer.parseInt(pm.getQteCom().getText())) {
                    TrayNotification notification = new TrayNotification();
                    notification.setAnimationType(AnimationType.POPUP);
                    notification.setTray("MyShop", "Stock atteint", NotificationType.ERROR);
                    notification.showAndDismiss(Duration.seconds(1));
                } else {
                    produitListVent.remove(e);
                    produitListVent.add(new ProduitR(produitVente, produitListVent, produitCaisseTable, Integer.parseInt(pm.getQteCom().getText()) + 1));
                }
// pm.setQteProdCom(new SimpleIntegerProperty(pm.getQteProdCom().getValue() + 1));
                // produitListVent.set(e, pm);
            }

            libProdColCaisse.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
            prixColCaisse.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
            qteColCaisse.setCellValueFactory(new PropertyValueFactory<ProduitR, JFXTextField>("qteCom"));
            actionColCaisse.setCellValueFactory(new PropertyValueFactory<ProduitR, JFXCheckBox>("suppression"));
            totalColCaisse.setCellValueFactory(cellData -> cellData.getValue().getTotal());
            produitCaisseTable.setItems(produitListVent);
            txtCodeProdCaisse.clear();

            //System.out.println(produitListVent);
        } catch (Exception e) {
        }
    }

    @FXML
    private void recuperationProduitInfo(KeyEvent event) {
    }

    @FXML
    private void SupprimerProdVent(ActionEvent event) {
        ProduitR pr = produitCaisseTable.getSelectionModel().getSelectedItem();
        if (pr == null) {
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Selectionnez un produit à supprimer", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(2));
            return;
        }
        produitListVent.remove(pr);
        produitCaisseTable.setItems(produitListVent);
    }

    @FXML
    private void saveVente(ActionEvent event) {
        for (ProduitR pr : produitListVent) {
            Produit p = new Produit(pr.getIdProd().getValue());
            Produit produit = produitService.findById(p);
            if (produit.getQteIniProd() < Integer.parseInt(pr.getQteCom().getText())) {
                TrayNotification notification = new TrayNotification();
                notification.setAnimationType(AnimationType.POPUP);
                notification.setTray("MyShop", "La quantite de " + produit.getLibProd() + " en stock est inferieur à \n celle commandée", NotificationType.ERROR);
                notification.showAndDismiss(Duration.seconds(1));
                produitListVent.remove(pr);
                produitListVent.add(new ProduitR(produit, produitListVent, produitCaisseTable, produit.getQteIniProd()));
                produitCaisseTable.setItems(produitListVent);
                return;
            }
        }
        Stage stage = new Stage();
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        TypeCompte typeCompte = new TypeCompte(MainViewController.typeCompteActif.getIdTyp());
        TypeCompte tC = MainViewController.typeServiceD.findById(typeCompte);

        if (tC.getLibTyp().equals("Administrateur")) {
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Le type de votre compte ne vous permez pas de vendre", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(1));
        } else {
            boolean selCombo = Traitement.comboEmty(cltCombo);
            if (selCombo) {
                try {
                    root = loader.load(getClass().getResource("/views/CaisseConfirmation.fxml").openStream());

                    CaisseConfirmationController caisseConfirmationController = (CaisseConfirmationController) loader.getController();
                    caisseConfirmationController.setListProd(produitListVent, MainViewController.compteActif,cltCombo.getValue());
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/essai.css").toExternalForm());
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.show();

                    stage.setOnHidden(e -> {
                        if (this.vente == true) {
                            produitListVent.clear();
                            txtCodeProdCaisse.clear();
                            txtNomProdCaisse.clear();
                            txtQteProdCaisse.clear();
                            txtPrixUnitCaisse.clear();
                            fillCombo();
                            this.vente = false;
                        }

                    });

                } catch (IOException ex) {
                    Logger.getLogger(MainPrincipalController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                TrayNotification notification = new TrayNotification();
                notification.setAnimationType(AnimationType.POPUP);
                notification.setTray("MyShop", "Choississez un client", NotificationType.ERROR);
                notification.showAndDismiss(Duration.seconds(1));
            }

        }

    }

    @FXML
    private void supprimerProduitCaisse(ActionEvent event) {
        ObservableList<ProduitR> listEff = FXCollections.observableArrayList();
        for (ProduitR produitR : produitListVent) {
            if (produitR.getSuppression().isSelected()) {
                listEff.add(produitR);
            }
        }
        produitListVent.removeAll(listEff);
        produitCaisseTable.setItems(produitListVent);
    }

    @FXML
    private void focus(MouseEvent event) {
        pane.setFocusTraversable(true);
        pane.requestFocus();
    }

    @FXML
    private void choixProd(ActionEvent event) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/views/CaisseChoixProd.fxml").openStream());
            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/essai.css").toExternalForm());
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.show();
            

            stage.setOnHidden(ex -> {
                txtCodeProdCaisse.setFocusTraversable(true);
                txtCodeProdCaisse.requestFocus();
                Produit p = new Produit(produitChoisi.getIdProd().getValue());
                Produit pC = produitService.findById(p);

                Boolean find = false;
                int i = 0;
                int e = 0;
                for (ProduitR pr : produitListVent) {

                    if (pC.getCodeProd().equals(pr.getCodeProd().getValue())) {
                        find = true;
                        e = i;
                    }
                    i++;
                }

                if (find == false) {
                    if (pC.getQteIniProd() <= 0) {
                        TrayNotification notification = new TrayNotification();
                        notification.setAnimationType(AnimationType.POPUP);
                        notification.setTray("MyShop", "Produit en rupture de stock", NotificationType.ERROR);
                        notification.showAndDismiss(Duration.seconds(1));
                    } else {
                        produitListVent.add(new ProduitR(pC, produitListVent, produitCaisseTable, 1));
                    }
                } else if (find == true) {
                    ProduitR pm = produitListVent.get(e);
                    if (pC.getQteIniProd() <= Integer.parseInt(pm.getQteCom().getText())) {
                        TrayNotification notification = new TrayNotification();
                        notification.setAnimationType(AnimationType.POPUP);
                        notification.setTray("MyShop", "Stock atteint", NotificationType.ERROR);
                        notification.showAndDismiss(Duration.seconds(1));
                    } else {
                        produitListVent.remove(e);
                        produitListVent.add(new ProduitR(pC, produitListVent, produitCaisseTable, Integer.parseInt(pm.getQteCom().getText()) + 1));
                    }

                }

                libProdColCaisse.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
                prixColCaisse.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
                qteColCaisse.setCellValueFactory(new PropertyValueFactory<ProduitR, JFXTextField>("qteCom"));
                actionColCaisse.setCellValueFactory(new PropertyValueFactory<ProduitR, JFXCheckBox>("suppression"));
                totalColCaisse.setCellValueFactory(cellData -> cellData.getValue().getTotal());
                produitCaisseTable.setItems(produitListVent);
                if (this.vente == true) {

                }

            });

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void nouveauClt(ActionEvent event) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/views/CaisseChoixProd.fxml").openStream());
            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/essai.css").toExternalForm());
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.show();
            

            stage.setOnHidden(ex -> {
                cltCombo.setValue(clientNew);
                if (this.vente == true) {

                }

            });

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
