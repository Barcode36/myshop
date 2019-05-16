/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.JFXFillTransition;
import static controller.MainViewController.hamburgerTmp;
import static controller.MainViewController.mainCss;
import entites.ContenirVente;
import entites.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modele.ProduitR;
import service.IContenirVente;
import service.IProduitService;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class DashBoardController implements Initializable {

    @FXML
    private PieChart pieChart;

    IProduitService produitService = MainViewController.produitService;
    IContenirVente contenirVenteService = MainViewController.contenirVenteService;
    @FXML
    private BarChart<String, Integer> barCode;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private JFXButton btnCaisse = new JFXButton();
    @FXML
    private JFXButton btnBilan;
    @FXML
    private JFXButton btnReglage;
    @FXML
    private JFXButton btnAide;
    @FXML
    private JFXButton btnInventaire;
    @FXML
    private JFXButton btnRec;
    @FXML
    private JFXButton btnCOmpte;

    public static JFXButton btnComp;
    public static JFXButton btnInvent;
    public static JFXButton btnBil;
    @FXML
    private AnchorPane stage;
    @FXML
    private GridPane cont;
    @FXML
    private JFXButton btnDeconnexion;
    @FXML
    private JFXButton btnClient;

    ObservableList<PieChart.Data> datas = FXCollections.observableArrayList();
    ObservableList<ProduitR> obVent = FXCollections.observableArrayList();
    ObservableList<ProduitR> obPro = FXCollections.observableArrayList();
    ObservableList<String> databar = FXCollections.observableArrayList();
    XYChart.Series series = new XYChart.Series<>();
    Random random = new Random();

    public List<Produit> listProduit() {
        return produitService.produitList();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        btnComp = btnCOmpte;
        btnInvent = btnInventaire;
        btnBil = btnBilan;

        MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stage.setPrefWidth(newValue.doubleValue());
                cont.setPrefWidth(newValue.doubleValue() - 45);
            }

        });

        if (MainViewController.initialise == true) {
            if (!MainViewController.typeCompteActif.getLibTyp().equals("Administrateur")) {
                this.btnComp.setVisible(false);
                this.btnInvent.setVisible(false);
                this.btnBil.setVisible(false);

            }
        }
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s = (Stage) btnCaisse.getScene().getWindow();
                if (s.isMaximized()) {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                }
                stage.setPrefWidth(MainViewController.temporaryPaneTot.getWidth());
                cont.setPrefWidth(MainViewController.temporaryPaneTot.getPrefWidth() - 45);
            }
        });
        // loadTable();
        for (Produit p : listProduit()) {
            List<ContenirVente> list = contenirVenteService.listParVente(p);
            int tot = 0;
            for (ContenirVente cv : list) {
                tot += cv.getQteVen();
            }
            obVent.add(new ProduitR(p, tot));
            obPro.add(new ProduitR(p, p.getQteIniProd()));
            databar.add(p.getLibProd());
        }

        Comparator<ProduitR> c = Comparator.comparingInt(ProduitR::getTo);

        FXCollections.sort(obVent, c.reversed());
        FXCollections.sort(obPro, c);
        for (int i = 0; i < 10; i++) {
            datas.add(new PieChart.Data(obVent.get(i).getLibProd().getValue(), obVent.get(i).getTo()));
            series.getData().add(new XYChart.Data<>(obPro.get(i).getLibProd().getValue(), obPro.get(i).getTo()));

            System.out.println(series);
        }
        pieChart.setData(datas);
        barCode.getData().addAll(series);
    }

    private void loadTable() {
        Service<Void> loadTableS = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                // Instancier et retourner une Task<Image> ici.
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        for (Produit p : listProduit()) {
                            List<ContenirVente> list = contenirVenteService.listParVente(p);
                            int tot = 0;
                            for (ContenirVente cv : list) {
                                tot += cv.getQteVen();
                            }
                            obVent.add(new ProduitR(p, tot));
                            obPro.add(new ProduitR(p, p.getQteIniProd()));
                            databar.add(p.getLibProd());
                        }

                        Comparator<ProduitR> c = Comparator.comparingInt(ProduitR::getTo);

                        FXCollections.sort(obVent, c.reversed());
                        FXCollections.sort(obPro, c);
                        for (int i = 0; i < 2; i++) {
                            datas.add(new PieChart.Data(obVent.get(i).getLibProd().getValue(), obVent.get(i).getTo()));
                            series.getData().add(new XYChart.Data<>(obPro.get(i).getLibProd().getValue(), obPro.get(i).getTo()));

                            System.out.println(series);
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                pieChart.setData(datas);
                                barCode.getData().addAll(series);
                            }
                        });
                        return null;
                    }
                };
            }
        };
        loadTableS.start();
        loadTableS.setOnSucceeded((event) -> {
            System.out.println("ok");

        });
        loadTableS.setOnFailed((event) -> {
            System.out.println("nonok");
        });
    }

    private void openAccueil(ActionEvent event) {
        switchPane(Constants.DashBoard);
    }

    @FXML
    private void openInventaire(ActionEvent event) {
        switchPane(Constants.CrudInventaire);

    }

    @FXML
    private void openComptes(ActionEvent event) {
        switchPane(Constants.CrudCompte);
    }

    @FXML
    private void openCaisse(ActionEvent event) {
        switchPane(Constants.Caisse);
    }

    @FXML
    private void openBilan(ActionEvent event) {
        switchPane(Constants.CrudBilan);
    }

    @FXML
    private void openReglage(ActionEvent event) {
        switchPane(Constants.Reglage);
    }

    @FXML
    private void openRecherche(ActionEvent event) {
        switchPane(Constants.RechercheProd);
    }

    @FXML
    private void openAide(ActionEvent event) {
        switchPane(Constants.Aide);
    }

    @FXML
    private void openClient(ActionEvent event) {
        switchPane(Constants.Client);
    }

    @FXML
    private void deconnexion(ActionEvent event) {

        try {
            switchPane(Constants.Connect);
            VBox menu = null;
            menu = FXMLLoader.load(getClass().getResource(Constants.MenuLateral));
            MainViewController.drawerTmp.setSidePane(menu);
        } catch (IOException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void switchPane(String pane) {
        try {
            MainViewController.temporaryPane.getChildren().clear();
            StackPane stackPane = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> elements = stackPane.getChildren();

            MainViewController.temporaryPane.getChildren().setAll(elements);

            MainViewController.drawerTmp.close();
            // MainViewController.hamburgerTmp = new JFXHamburger();
        } catch (IOException ex) {
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
