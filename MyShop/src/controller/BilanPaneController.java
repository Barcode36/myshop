/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXDatePicker;
import entites.Compte;
import entites.ContenirVente;
import entites.Produit;
import entites.Vente;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;
import modele.ProduitR;
import modele.VenteR;
import service.ICompteService;
import service.IContenirVente;
import service.IProduitService;
import service.IVenteService;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class BilanPaneController implements Initializable {

    @FXML
    private GridPane gridBilan;
    @FXML
    private TableColumn<VenteR, String> caissierColVente;
    @FXML
    private TableColumn<VenteR, Integer> totCaissierColVente;
    @FXML
    private TableView<VenteR> CaissierVenteTable;
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
    private TableView<ProduitR> tableDetailCaissier;
    @FXML
    private RadioButton rbMoisCours;
    @FXML
    private ToggleGroup periode;
    @FXML
    private Button btnMoisCours;
    @FXML
    private RadioButton rbMois;
    @FXML
    private ComboBox<String> moisCombo;
    @FXML
    private RadioButton rbDeuxDate;
    @FXML
    private JFXDatePicker datePiker1;
    @FXML
    private JFXDatePicker datePiker2;
    @FXML
    private Button btnDeuxDateSearch;

    IVenteService venteService = MainViewController.venteService;
    IContenirVente contenirVenteService = MainViewController.contenirVenteService;
    IProduitService produitService = MainViewController.produitService;
    ICompteService compteService = MainViewController.compteServiceD;

    ObservableList<VenteR> venteList = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVentCaissier = FXCollections.observableArrayList();
    ObservableList<String> listMois = FXCollections.observableArrayList();
    @FXML
    private AnchorPane stage;
    @FXML
    private AnchorPane cont;
    @FXML
    private Pane band;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mois();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s = (Stage) stage.getScene().getWindow();
                if (s.isMaximized()) {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                }
                stage.setPrefWidth(MainViewController.temporaryPaneTot.getPrefWidth());
                cont.setPrefWidth(MainViewController.temporaryPaneTot.getPrefWidth() - 71);
            }
        });

        MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stage.setPrefWidth(newValue.doubleValue());
                cont.setPrefWidth(newValue.doubleValue() - 71);
                band.setPrefWidth(cont.getPrefWidth() - 300);
            }

        });

    }

    public void loadVenteCaissierDetail(VenteR venteR) {
        produitListVentCaissier.clear();
        Compte c = new Compte(venteR.getidCompte().getValue());
        Compte compte = compteService.findById(c);
        List<Vente> list = venteService.ventesParCaissier(compte);
        for (Vente v : list) {
            List<ContenirVente> listCon = contenirVenteService.listParVente(v);
            for (ContenirVente cv : listCon) {
                Produit p = new Produit(cv.getContenirVentePK().getIdProd());
                Produit produit = produitService.findById(p);
                produitListVentCaissier.add(new ProduitR(produit, v, cv));
            }
        }

//        
        totDetCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getTotal());
        PuCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        QteCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getQteProdCom().asObject());
        DateCaissier.setCellValueFactory(cellData -> cellData.getValue().getDateVen());
        ProduitCaissier.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        tableDetailCaissier.setItems(produitListVentCaissier);
    }

    @FXML
    private void getCaissier(MouseEvent event) {
        VenteR venteR = CaissierVenteTable.getSelectionModel().getSelectedItem();
        loadVenteCaissierDetail(venteR);
    }

    @FXML
    private void choixBilan(ActionEvent event) {
        venteList.clear();
        produitListVentCaissier.clear();
        if (rbMoisCours.isSelected()) {
            btnMoisCours.setDisable(false);
            moisCombo.setDisable(true);
            datePiker1.setDisable(true);
            datePiker2.setDisable(true);
            btnDeuxDateSearch.setDisable(true);

        } else if (rbMois.isSelected()) {
            moisCombo.setDisable(false);
            btnMoisCours.setDisable(true);
            datePiker1.setDisable(true);
            datePiker2.setDisable(true);
            btnDeuxDateSearch.setDisable(true);
        } else if (rbDeuxDate.isSelected()) {
            datePiker1.setDisable(false);
            datePiker2.setDisable(false);
            btnDeuxDateSearch.setDisable(false);
            btnMoisCours.setDisable(true);
            moisCombo.setDisable(true);

        }
    }

    @FXML
    private void bilanParMois(ActionEvent event) {
        Date date = new Date();
        LocalDate dateh = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String mois = "";
        switch (dateh.getMonthValue()) {
            case 1:
                mois = "01";
                break;
            case 2:
                mois = "02";
                break;
            case 3:
                mois = "03";
                break;
            case 4:
                mois = "04";
                break;
            case 5:
                mois = "05";
                break;
            case 6:
                mois = "06";
                break;
            case 7:
                mois = "07";
                break;
            case 8:
                mois = "08";
                break;
            case 9:
                mois = "09";
                break;
            default:
                mois = String.valueOf(dateh.getMonthValue());
        }
        String d = dateh.getYear() + "-" + mois;
        //List<Vente> list = venteService.ventesParMois(String.valueOf(dateh.getYear() + "-" + mois));
        loadVenteCaissier("mois", d, "", "");
    }

    private void mois() {
        listMois.addAll("Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre",
                "Novembre", "Decembre");
        moisCombo.setItems(listMois);
    }

    @FXML
    private void CaisseParMois(ActionEvent event) {
        String idMois = "";
        String mois = moisCombo.getValue();
        switch (mois) {
            case "Janvier":
                idMois = "01";
                break;
            case "Fevrier":
                idMois = "02";
                break;
            case "Mars":
                idMois = "03";
                break;
            case "Avril":
                idMois = "04";
                break;
            case "Mai":
                idMois = "05";
                break;
            case "Juin":
                idMois = "06";
                break;
            case "Juillet":
                idMois = "07";
                break;
            case "Aout":
                idMois = "08";
                break;
            case "Septembre":
                idMois = "09";
                break;
            case "Octobre":
                idMois = "10";
                break;
            case "Novembre":
                idMois = "11";
                break;
            case "Decembre":
                idMois = "12";
                break;

            default:
                break;
        }
        Date date = new Date();
        LocalDate dateh = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        List<Vente> list = venteService.ventesParMois(String.valueOf(dateh.getYear() + "-" + idMois));
        String d = dateh.getYear() + "-" + idMois;
        loadVenteCaissier("mois", d, "", "");
    }

    @FXML
    private void caisseEntreDate(ActionEvent event) {
        LocalDate d1 = datePiker1.getValue();
        String idMoisDp1 = "";
        String idMoisDp2 = "";
        String jr1 = "";
        String jr2 = "";
        switch (datePiker1.getValue().getMonthValue()) {
            case 10:
                idMoisDp1 = String.valueOf(datePiker1.getValue().getMonthValue());
                break;
            case 11:
                break;
            case 12:
                break;
            default:
                idMoisDp1 = "0" + datePiker1.getValue().getMonthValue();
        }
        switch (datePiker2.getValue().getMonthValue()) {
            case 10:
                idMoisDp2 = String.valueOf(datePiker2.getValue().getMonthValue());
                break;
            case 11:
                idMoisDp2 = String.valueOf(datePiker2.getValue().getMonthValue());
                break;
            case 12:
                idMoisDp2 = String.valueOf(datePiker2.getValue().getMonthValue());
                break;
            default:
                idMoisDp2 = "0" + datePiker2.getValue().getMonthValue();
        }
        switch (datePiker1.getValue().getDayOfMonth()) {
            case 1:
                jr1 = "01";
                break;
            case 2:
                jr1 = "02";
                break;
            case 3:
                jr1 = "03";
                break;
            case 4:
                jr1 = "04";
                break;
            case 5:
                jr1 = "05";
                break;
            case 6:
                jr1 = "06";
                break;
            case 7:
                jr1 = "07";
                break;
            case 8:
                jr1 = "08";
                break;
            case 9:
                jr1 = "09";
                break;
            default:
                jr1 = String.valueOf(datePiker1.getValue().getDayOfMonth());

        }
        switch (datePiker2.getValue().getDayOfMonth()) {
            case 1:
                jr2 = "01";
                break;
            case 2:
                jr2 = "02";
                break;
            case 3:
                jr2 = "03";
                break;
            case 4:
                jr2 = "04";
                break;
            case 5:
                jr2 = "05";
                break;
            case 6:
                jr2 = "06";
                break;
            case 7:
                jr2 = "07";
                break;
            case 8:
                jr2 = "08";
                break;
            case 9:
                jr2 = "09";
                break;
            default:
                jr2 = String.valueOf(datePiker2.getValue().getDayOfMonth());

        }
        String d = d1.getYear() + "-" + idMoisDp1 + "-" + jr1;
        LocalDate d2 = datePiker2.getValue();
        String dl = d2.getYear() + "-" + idMoisDp2 + "-" + jr2;
        System.out.println(d);
        System.out.println(dl);
//        List<Vente> list = venteService.ventesEntreDeuxDate(d, dl);
        loadVenteCaissier("date", "", d, dl);
    }

    public void loadVenteCaissier(String typeDem, String mois, String d, String d2) {
        venteList.clear();
        List<Compte> listc = compteService.compteList();
        for (Compte c : listc) {
            List<Vente> listVParCaissier = null;
            if (typeDem.equals("mois")) {
                listVParCaissier = venteService.ventesParCaissierMois(c, mois);
            } else if (typeDem.equals("date")) {
                listVParCaissier = venteService.ventesEntreDeuxDate(d, d2, c);
            }
            int totVent = 0;
            for (Vente vente : listVParCaissier) {

                List<ContenirVente> listCon = contenirVenteService.listParVente(vente);
                System.out.println(listCon);
                for (ContenirVente cv : listCon) {
                    Produit p = new Produit(cv.getContenirVentePK().getIdProd());
                    Produit produit = produitService.findById(p);
                    int totPro = Integer.parseInt(produit.getPrixUniProd()) * cv.getQteVen();
                    totVent += totPro;
                }

            }
            if (!listVParCaissier.isEmpty()) {
                venteList.add(new VenteR(c, totVent));
            } else {
                venteList.clear();
            }
        }
        caissierColVente.setCellValueFactory(cellData -> cellData.getValue().getCaissier());
        totCaissierColVente.setCellValueFactory(cellData -> cellData.getValue().getTotalCaissier().asObject());
        CaissierVenteTable.setItems(venteList);
//        for (Vente vente : list) {
//            Compte c = new Compte(vente.getIdComp());
//            Compte compte = compteService.findById(c);
//            List<ContenirVente> listCon = contenirVenteService.listParVente(vente);
//            int totVent = 0;
//            for (ContenirVente cv : listCon) {
//                Produit p = new Produit(cv.getContenirVentePK().getIdProd());
//                Produit produit = produitService.findById(p);
//                int totPro = Integer.parseInt(produit.getPrixUniProd()) * cv.getQteVen();
//                totVent += totPro;
//            }
//            venteList.add(new VenteR(compte, vente, totVent));
//
//        }

    }

}
