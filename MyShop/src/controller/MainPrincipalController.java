/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import entites.Compte;
import entites.ContenirVente;
import entites.Produit;
import entites.TypeCompte;
import entites.Vente;
import entites.ContenirVentePK;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FillTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modele.ClientR;
import modele.CompteR;
import modele.ProduitR;
import modele.TypeCompteR;
import modele.VenteR;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.ICompteService;
import service.IContenirVente;
import service.IProduitService;
import service.ITypeService;
import service.IVenteService;
import service.imp.ContenirVenteService;
import service.imp.CompteService;
import service.imp.ProduitService;
import service.imp.TypeService;
import service.imp.VenteService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class MainPrincipalController implements Initializable {

    private Stage stage;
    private FileChooser fileChooser;
    private File file;

//declaration des composants de la partie inventaire
    @FXML
    private GridPane grid;
    @FXML
    private JFXButton btnInventaire;
    @FXML
    private GridPane gridInventaire;
//Declaration des composants lies à Inventaire
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtLibProd;
    @FXML
    private TextField txtPrixProd;
    @FXML
    private TextField txtQteProd;
    @FXML
    private JFXButton tbnSaveProd;
    @FXML
    private TableView<ProduitR> produitTable;
    @FXML
    private TableColumn<ProduitR, String> libProdCol;
    @FXML
    private TableColumn<ProduitR, String> codeProdcol;
    @FXML
    private TableColumn<ProduitR, Integer> qteProdCol;
    @FXML
    private TableColumn<ProduitR, String> prixProdCol;
// fin declaration

//declaration des composants de la fenetre de connexion a la caisse
    private ComboBox<CompteR> combpPseudoCennect;
    @FXML
    private PasswordField txtPassConnect;
//fin declaration

//Declaration des composants lies à Compte
    @FXML
    private JFXButton tbnSaveComp;
    @FXML
    private PasswordField txtPassword;
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
    private JFXButton btnComp;
    @FXML
    private JFXButton btnCaisse;
    @FXML
    private GridPane gridCompte;
    @FXML
    private GridPane gridConnect;
    @FXML
    private TextField txtNomComp;
    @FXML
    private TextField txtPseudoComp;
    @FXML
    private TextField txtPrenomComp;
    @FXML
    private ComboBox<TypeCompteR> typeCompteCombo;
    //fin declaration

    //declaration des bilans
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
    private GridPane gridCaisse;
    @FXML
    private TextField txtCodeProdCaisse;
    @FXML
    private TextField txtNomProdCaisse;
    @FXML
    private TextField txtQteProdCaisse;
    @FXML
    private TextField txtPrixUnitCaisse;
    private TextField txtQteComCaisse;

    //fin declaration
    //declaration des composants de la grid  caisses
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
    //fin declaration
//Declaration des observables listes
    ObservableList<ProduitR> produitList = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVent = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVentCaissier = FXCollections.observableArrayList();
    ObservableList<CompteR> compteList = FXCollections.observableArrayList();
    ObservableList<TypeCompteR> typeCompteList = FXCollections.observableArrayList();
    ObservableList<VenteR> venteList = FXCollections.observableArrayList();

//fin de declaration
//Declaration des services
    IProduitService produitService = new ProduitService();
    ICompteService compteService = new CompteService();
    ITypeService typeService = new TypeService();
    IVenteService venteService = new VenteService();
    IContenirVente contenirVenteService = new ContenirVenteService();
//fin de declaration
//Declaration des classes à modifier
    Produit produitModif = new Produit();
    Compte compteModif = new Compte();
//fin declaration

//Declaration de d'autre variable
    private Compte compteActif = new Compte();
    private Produit produitVente = new Produit();
//fin declaration
    //recuperation des listes de chaque composant

    @FXML
    private JFXDrawer drawer;
    @FXML
    private TextField txtPseudoCennect;
    @FXML
    private JFXButton btnBilan;
    @FXML
    private GridPane gridBilan;
    @FXML
    private ComboBox<String> moisCombo;
    @FXML
    private GridPane gridReglage;
    @FXML
    private JFXButton btnReglage;
    @FXML
    private Pane anchorPane;
    @FXML
    private ToggleGroup periode;
    @FXML
    private GridPane gridAPropos;
    @FXML
    private Pane anchorPane1;
    @FXML
    private JFXButton btnPropos;
    @FXML
    private Label lblCLose;
    @FXML
    private ImageView imgShop;
    @FXML
    private Label lblCLose1;
    @FXML
    private JFXButton btnCaisse1;
    ObservableList<String> listMois = FXCollections.observableArrayList();
    @FXML
    private JFXDatePicker datePiker1;
    @FXML
    private JFXDatePicker datePiker2;
    @FXML
    private RadioButton rbMoisCours;
    @FXML
    private RadioButton rbMois;
    @FXML
    private RadioButton rbDeuxDate;
    @FXML
    private Button btnMoisCours;
    @FXML
    private Button btnDeuxDateSearch;
    private Button btnCaisseVente;
    @FXML
    private MenuItem menuProduct;
    @FXML
    private Button btnInventaireClose;
    @FXML
    private Button btnCaisseClose;
    @FXML
    private Pane paneCaisseClose;
    @FXML
    private Pane paneBilanClose;
    @FXML
    private Button btnBilanClose;

    private void mois() {
        listMois.addAll("Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre",
                "Novembre", "Decembre");
        moisCombo.setItems(listMois);
    }

    public List<Produit> listProduit() {
        return produitService.produitList();
    }

    public List<Compte> listCompte() {
        return compteService.compteList();
    }

    public List<TypeCompte> listTypeCompte() {
        return typeService.typeCmopteList();
    }

    public List<Vente> listVente() {
        return venteService.ventes();
    }

//Chargement des tables de chaque grid
    public void loadInventairegrid() {
        produitList.clear();
        for (Produit produit : listProduit()) {
            produitList.add(new ProduitR(produit));
        }
        libProdCol.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        codeProdcol.setCellValueFactory(cellData -> cellData.getValue().getCodeProd());
        prixProdCol.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        qteProdCol.setCellValueFactory(cellData -> cellData.getValue().getQteIniProd().asObject());
        produitTable.setItems(produitList);
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
//            for (Vente vente : listVParCaissier) {
//                List<ContenirVente> listCon = contenirVenteService.listParVente(vente);
//                for (ContenirVente cv : listCon) {
////                    Produit p = new Produit(cv.getContenirVentePK().getIdProd());
//                   // Produit produit = produitService.findById(p);
//                    int totPro = Integer.parseInt(produit.getPrixUniProd()) * cv.getQteVen();
//                    totVent += totPro;
//                }
//            }
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

    public void loadVenteCaissierDetail(VenteR venteR) {
        produitListVentCaissier.clear();
        Compte c = new Compte(venteR.getidCompte().getValue());
        Compte compte = compteService.findById(c);
        List<Vente> list = venteService.ventesParCaissier(compte);
        for (Vente v : list) {
            List<ContenirVente> listCon = contenirVenteService.listParVente(v);
//            for (ContenirVente cv : listCon) {
//                Produit p = new Produit(cv.getContenirVentePK().getIdProd());
//                Produit produit = produitService.findById(p);
//                produitListVentCaissier.add(new ProduitR(produit, v, cv));
//            }
        }

//        
        totDetCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getTotal());
        PuCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        QteCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getQteProdCom().asObject());
        DateCaissier.setCellValueFactory(cellData -> cellData.getValue().getDateVen());
        ProduitCaissier.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        tableDetailCaissier.setItems(produitListVentCaissier);
    }

    public void loadTypeCompteCombo() {
        typeCompteList.clear();
        List<TypeCompte> list = typeService.typeCmopteList();
        for (TypeCompte typeCompte : list) {
            typeCompteList.add(new TypeCompteR(typeCompte));
        }
        typeCompteCombo.setItems(typeCompteList);
    }
//fin des methodes de chargement

    /**
     * Initializes the controller class.
     */
    private StringBuffer barcode = new StringBuffer();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (listTypeCompte().size() == 0) {
            TypeCompte tc = new TypeCompte();
            tc.setLibTyp("Administrateur");
            typeService.ajouter(tc);
            Compte c = new Compte();
            c.setEtatComp("actif");
            c.setIdTypComp(tc.getIdTyp());
            c.setMdpComp("1234");
            c.setNomComp("Roi");
            c.setPrenomComp("King");
            c.setPseudoComp("sohrel");
            compteService.ajouter(c);
            TypeCompte tc1 = new TypeCompte();
            tc1.setLibTyp("Caissier");
            typeService.ajouter(tc1);
        }

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
                        produitListVent.add(new ProduitR(produitVente, produitListVent, produitCaisseTable, 1));
                        barcode = new StringBuffer();
                    } else if (find == true) {
                        ProduitR pm = produitListVent.get(e);
                        // pm.setQteProdCom(new SimpleIntegerProperty(pm.getQteProdCom().getValue() + 1));
                        produitListVent.remove(e);
                        produitListVent.add(new ProduitR(produitVente, produitListVent, produitCaisseTable, Integer.parseInt(pm.getQteCom().getText()) + 1));
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
       
//        List<Vente> listvv = venteService.ventes();
//        loadVenteCaissier(listvv);
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx"));
        txtPseudoCennect.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            if (txtPseudoCennect.getText().equals(null)) {
                autoComplete();
            }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                 loadInventairegrid();
        loadCompteGrid();
        loadTypeCompteCombo();
        showClavier();
        mois();
                txtPseudoCennect.setFocusTraversable(true);
                txtPseudoCennect.requestFocus();
                Stage stage = (Stage) btnBilan.getScene().getWindow();
                stage.setMaximized(true);
                btnInventaireClose.setLayoutX(produitTable.getWidth() - 80);
                btnCaisseClose.setLayoutX(paneCaisseClose.getWidth() - 80);
                btnBilanClose.setLayoutX(paneBilanClose.getWidth() - 80);
                produitTable.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        btnInventaireClose.setLayoutX(newValue.doubleValue() - 80);
                    }

                });
                paneCaisseClose.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        btnCaisseClose.setLayoutX(newValue.doubleValue() - 80);
                    }

                });
                paneBilanClose.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        btnCaisseClose.setLayoutX(newValue.doubleValue() - 80);
                    }

                });

            }
        });

        gridCaisse.setOnKeyTyped(new EventHandler<KeyEvent>() {

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

    @FXML
    private void selectForm(ActionEvent event) {
        if (event.getSource() == btnInventaire || event.getSource() == menuProduct) {
            gridInventaire.toFront();
            gridInventaire.setVisible(true);
            grid.toBack();
            grid.setVisible(false);
            gridCompte.setVisible(false);
            gridAPropos.setVisible(false);
        } else if (event.getSource() == btnComp) {
            gridCompte.toFront();
            gridCompte.setVisible(true);
            grid.setVisible(false);
            gridInventaire.setVisible(false);
            gridAPropos.setVisible(false);
        } else if (event.getSource() == btnCaisse) {
            gridCaisse.toFront();
            gridCaisse.setVisible(true);
            gridCaisse.setFocusTraversable(true);
            gridCaisse.requestFocus();
            if (gridCaisse.isFocused()) {
                System.out.println("ok");
            }
            btnCaisseVente.setDefaultButton(true);
            grid.setVisible(false);
            gridInventaire.setVisible(false);
            gridCompte.setVisible(false);
            gridAPropos.setVisible(false);
        } else if (event.getSource() == btnBilan) {
            gridBilan.toFront();
            gridBilan.setVisible(true);
            grid.setVisible(false);
            gridInventaire.setVisible(false);
            gridCompte.setVisible(false);
            gridAPropos.setVisible(false);
        } else if (event.getSource() == btnReglage) {
            gridReglage.toFront();
            gridReglage.setVisible(true);
            grid.setVisible(false);
            gridInventaire.setVisible(false);
            gridCompte.setVisible(false);
            gridAPropos.setVisible(false);
        } else if (event.getSource() == btnPropos) {
            gridAPropos.toFront();
            gridAPropos.setVisible(true);
            gridReglage.setVisible(false);
            grid.setVisible(false);
            gridInventaire.setVisible(false);
            gridCompte.setVisible(false);
        }
    }

    private void saveUpProduit(String saveUp) {
        if (saveUp.equals("Ajouter")) {
            Produit produit = new Produit();
            produit.setCodeProd(txtCode.getText());
            produit.setLibProd(txtLibProd.getText());
            produit.setPrixUniProd(txtPrixProd.getText());
            produit.setQteIniProd(Integer.parseInt(txtQteProd.getText()));
            produitService.ajouter(produit);
        } else {
            produitModif.setCodeProd(txtCode.getText());
            produitModif.setLibProd(txtLibProd.getText());
            produitModif.setPrixUniProd(txtPrixProd.getText());
            produitModif.setQteIniProd(Integer.parseInt(txtQteProd.getText()));
            produitService.modifier(produitModif);
            tbnSaveProd.setText("Ajouter");
        }
    }

    private void saveUpCompte(String saveUp) {
        if (saveUp.equals("Ajouter")) {
            Compte compte = new Compte();
            compte.setMdpComp(txtPassword.getText());
            compte.setNomComp(txtNomComp.getText());
            compte.setPrenomComp(txtPrenomComp.getText());
            compte.setPseudoComp(txtPseudoComp.getText());
            compte.setEtatComp("actif");
            compte.setIdTypComp(typeCompteCombo.getSelectionModel().getSelectedItem().getIdTyp().getValue());
            compteService.ajouter(compte);
        } else {
            compteModif.setMdpComp(txtPassword.getText());
            compteModif.setNomComp(txtNomComp.getText());
            compteModif.setPrenomComp(txtPrenomComp.getText());
            compteModif.setPseudoComp(txtPseudoComp.getText());
            compteService.modifier(compteModif);
            tbnSaveComp.setText("Ajouter");
        }
    }

    private void getProduitInformationFromTable(ProduitR produitR) {
        Produit p = new Produit(produitR.getIdProd().getValue());
        produitModif = produitService.findById(p);
        txtCode.setText(produitModif.getCodeProd());
        txtLibProd.setText(produitModif.getLibProd());
        txtPrixProd.setText(produitModif.getPrixUniProd());
        txtQteProd.setText(String.valueOf(produitModif.getQteIniProd()));
    }

    private void getCompteInformationFromTable(CompteR compteR) {
        Compte c = new Compte(compteR.getIdComp().getValue());
        compteModif = compteService.findById(c);
        txtNomComp.setText(compteModif.getNomComp());
        txtPrenomComp.setText(compteModif.getPrenomComp());
        txtPseudoComp.setText(compteModif.getPseudoComp());
        txtPassword.setText(compteModif.getMdpComp());
    }

    @FXML
    private void closeInventaire(ActionEvent event) {
        grid.toFront();
        grid.setVisible(true);
        gridInventaire.toBack();
        gridInventaire.setVisible(false);
        venteList.clear();
        clearCompteTxt();
        clearProduitText();
        clearCaisseTxt();
        TypeCompte tc = new TypeCompte(compteActif.getIdTypComp());
        TypeCompte typeCompte = typeService.findById(tc);
        if (typeCompte.getLibTyp().equals("Caissier")) {
            System.exit(0);
        }
    }

    private void clearCaisseTxt() {
        txtCodeProdCaisse.clear();
        txtNomProdCaisse.clear();
        txtQteProdCaisse.clear();
        txtPrixUnitCaisse.clear();
        venteList.clear();
        produitListVent.clear();
    }

    @FXML
    private void connexionButton(ActionEvent event) {
        Compte c = new Compte();
        c.setPseudoComp(txtPseudoCennect.getText());
        c.setMdpComp(txtPassConnect.getText());
        try {
            compteActif = compteService.Connexion(c);
            TypeCompte compte = new TypeCompte(compteActif.getIdTypComp());
            TypeCompte typeCompte = typeService.findById(compte);
            if (typeCompte.getLibTyp().equals("Caissier")) {
                gridCaisse.toFront();
                gridCaisse.setVisible(true);
//                grid.setVisible(false);
                gridConnect.setVisible(false);
                gridConnect.toBack();
                gridCompte.setVisible(false);
                gridInventaire.setVisible(false);
            } else if (typeCompte.getLibTyp().equals("Administrateur")) {
                grid.toFront();
                grid.setVisible(true);
                gridConnect.setVisible(false);
                gridConnect.toBack();
                gridCompte.setVisible(false);
                gridInventaire.setVisible(false);
            }

        } catch (Exception e) {
            //e.printStackTrace();
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Pseudo ou Mot de passe incorrect", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(1.5));
        }
    }

    @FXML
    private void essai(KeyEvent event) {
//        System.out.println(combpPseudoCennect.getSelectionModel().getSelectedItem().getPseudoComp().getValue());
    }

    @FXML
    private void saveProduit(ActionEvent event) {
        saveUpProduit(tbnSaveProd.getText());
        loadInventairegrid();
        clearProduitText();
    }

    @FXML
    private void saveCompte(ActionEvent event) {
        saveUpCompte(tbnSaveComp.getText());
        loadCompteGrid();
        clearCompteTxt();
    }

    @FXML
    private void deleteProduit(ActionEvent event) {
        produitService.supprimer(produitModif);
        loadInventairegrid();
        clearProduitText();
    }

    @FXML
    private void deleteCompte(ActionEvent event) {
        compteService.supprimer(compteModif);
        loadCompteGrid();
        clearCompteTxt();
    }

    @FXML
    private void recuperationProduitInfo(KeyEvent event) {
        //caisseProduitInfo();
        //txtNomProdCaisse.setOnKeyTyped(new);

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
                produitListVent.add(new ProduitR(produitVente, produitListVent, produitCaisseTable, 1));
                barcode = new StringBuffer();
            } else if (find == true) {
                ProduitR pm = produitListVent.get(e);
                // pm.setQteProdCom(new SimpleIntegerProperty(pm.getQteProdCom().getValue() + 1));
                produitListVent.remove(e);
                produitListVent.add(new ProduitR(produitVente, produitListVent, produitCaisseTable, pm.getQteProdCom().getValue() + 1));
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

    private void valideProduit(ActionEvent event) {
        produitListVent.add(new ProduitR(produitVente, produitListVent, produitCaisseTable, Integer.parseInt(txtQteComCaisse.getText())));

        libProdColCaisse.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        prixColCaisse.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        qteColCaisse.setCellValueFactory(new PropertyValueFactory<ProduitR, JFXTextField>("qteCom"));
        actionColCaisse.setCellValueFactory(new PropertyValueFactory<ProduitR, JFXCheckBox>("suppression"));
        totalColCaisse.setCellValueFactory(cellData -> cellData.getValue().getTotal());
        produitCaisseTable.setItems(produitListVent);
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
    private void getProduitFromTable(MouseEvent event) {
        ProduitR pr = produitTable.getSelectionModel().getSelectedItem();
//        if (pr == null) {
//            TrayNotification notification = new TrayNotification();
//            notification.setAnimationType(AnimationType.POPUP);
//            notification.setTray("MyShop", "Selectionnez un produit à supprimer", NotificationType.ERROR);
//            notification.showAndDismiss(Duration.seconds(2));
//            return;
//        }
        Produit p = new Produit(pr.getIdProd().getValue());
        produitModif = produitService.findById(p);
        txtCode.setText(produitModif.getCodeProd());
        txtLibProd.setText(produitModif.getLibProd());
        txtPrixProd.setText(String.valueOf(produitModif.getPrixUniProd()));
        txtQteProd.setText(String.valueOf(produitModif.getQteIniProd()));
        tbnSaveProd.setText("Modifier");
    }

    @FXML
    private void getCompteFromTable(MouseEvent event) {
        CompteR cr = CompteTable.getSelectionModel().getSelectedItem();
        Compte c = new Compte(cr.getIdComp().getValue());
        compteModif = compteService.findById(c);
        TypeCompte tc = new TypeCompte(compteModif.getIdTypComp());
        TypeCompte typeCompte = typeService.findById(tc);
        typeCompteCombo.setValue(new TypeCompteR(typeCompte));
        txtNomComp.setText(compteModif.getNomComp());
        txtPrenomComp.setText(compteModif.getPrenomComp());
        txtPseudoComp.setText(compteModif.getPseudoComp());
        txtPassword.setText(compteModif.getMdpComp());
        tbnSaveComp.setText("Modifier");
    }

    private void clearProduitText() {
        txtCode.clear();
        txtLibProd.clear();
        txtPrixProd.clear();
        txtQteProd.clear();
    }

    private void showClavier() {
        try {
            Pane vBox = FXMLLoader.load(getClass().getResource("/views/clavier.fxml"));
            drawer.setSidePane(vBox);
            ;
            for (Node node : vBox.getChildren()) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getAccessibleText()) {
                            case "1":
                                txtPassConnect.setText(txtPassConnect.getText() + "1");
                                break;
                            case "2":
                                txtPassConnect.setText(txtPassConnect.getText() + "2");
                                break;
                            case "3":
                                txtPassConnect.setText(txtPassConnect.getText() + "3");
                                break;
                            case "4":
                                txtPassConnect.setText(txtPassConnect.getText() + "4");
                                break;
                            case "5":
                                txtPassConnect.setText(txtPassConnect.getText() + "5");
                                break;
                            case "6":
                                txtPassConnect.setText(txtPassConnect.getText() + "6");
                                break;
                            case "7":
                                txtPassConnect.setText(txtPassConnect.getText() + "7");
                                break;
                            case "8":
                                txtPassConnect.setText(txtPassConnect.getText() + "8");
                                break;
                            case "9":
                                txtPassConnect.setText(txtPassConnect.getText() + "9");
                                break;
                            case "0":
                                txtPassConnect.setText(txtPassConnect.getText() + "0");
                                break;
                        }

                    });
                }
            }
            txtPassConnect.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                drawer.toFront();
                if (drawer.isShown()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void autoComplete() {

    }

    @FXML
    private void exportInventaire(ActionEvent event) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Inventaire");
        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Code Produit");
        header.createCell(1).setCellValue("Produit");
        header.createCell(2).setCellValue("Prix Unitaire");
        header.createCell(3).setCellValue("Quantité");
        sheet.autoSizeColumn(0);
        sheet.setColumnWidth(0, 256 * 25);
        sheet.setColumnWidth(1, 256 * 25);
        sheet.setColumnWidth(2, 256 * 25);
        sheet.setColumnWidth(3, 256 * 25);

        int index = 1;
        for (Produit produit : listProduit()) {
            XSSFRow row = sheet.createRow(index);
            row.createCell(0).setCellValue(produit.getCodeProd());
            row.createCell(1).setCellValue(produit.getLibProd());
            row.createCell(2).setCellValue(produit.getPrixUniProd());
            row.createCell(3).setCellValue(produit.getQteIniProd());
            index++;
        }
        try {
            stage = (Stage) anchorPane.getScene().getWindow();
            file = fileChooser.showSaveDialog(stage);
            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath() + ".xlsx");
            wb.write(fos);
            fos.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        TrayNotification notification = new TrayNotification();
        notification.setAnimationType(AnimationType.POPUP);
        notification.setTray("MyShop", "Exportation terminée", NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.seconds(1.5));
    }

    @FXML
    private void getCaissier(MouseEvent event) {
        VenteR venteR = CaissierVenteTable.getSelectionModel().getSelectedItem();
        loadVenteCaissierDetail(venteR);
    }

    @FXML
    private void importInventaire(ActionEvent event) {
        stage = (Stage) anchorPane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        Produit produit = new Produit();
        if (file != null) {
            try {
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                XSSFSheet sheet = wb.getSheetAt(0);
                Row row;
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    Produit p = new Produit();
                    p.setCodeProd(row.getCell(0).getStringCellValue());
                    p.setLibProd(row.getCell(1).getStringCellValue());
                    p.setPrixUniProd(row.getCell(2).getStringCellValue());
                    p.setQteIniProd((int) row.getCell(3).getNumericCellValue());
                    try {
                        produit = produitService.findByCode(p);
                        produit.setQteIniProd(produit.getQteIniProd() + p.getQteIniProd());
                        produitService.modifier(produit);
                    } catch (Exception e) {
                        produitService.ajouter(p);
                    }
                }

                wb.close();
                fis.close();
                loadInventairegrid();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainPrincipalController.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(MainPrincipalController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Importation terminée", NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.seconds(1.5));
        }
    }

    private void clearCompteTxt() {
        txtNomComp.clear();
        txtPrenomComp.clear();
        txtPseudoComp.clear();
        txtPassword.clear();
        typeCompteCombo.setValue(null);
        typeCompteCombo.getEditor().clear();
    }

    @FXML
    private void saveVente(ActionEvent event) {
//      
        Stage stage = new Stage();
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        try {
            root = loader.load(getClass().getResource("/views/CaisseConfirmation.fxml").openStream());

            CaisseConfirmationController caisseConfirmationController = (CaisseConfirmationController) loader.getController();
            caisseConfirmationController.setListProd(produitListVent, compteActif, new ClientR());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/MainPrincipalCss.css").toExternalForm());
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            stage.setOnHidden(e -> {
                produitListVent.clear();
                txtCodeProdCaisse.clear();
                txtNomProdCaisse.clear();
                txtQteProdCaisse.clear();
                txtPrixUnitCaisse.clear();
            });

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
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
    private void selectScene(MouseEvent event) {
        gridCaisse.setFocusTraversable(true);
        gridCaisse.requestFocus();
    }

    @FXML
    private void fermerApp(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void closeInventaire(MouseEvent event) {
    }

}
