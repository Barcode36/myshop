/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import entites.ContenirVente;
import entites.Produit;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
    private BarChart<?, ?> barCode;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private JFXButton btnCaisse;
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

    public List<Produit> listProduit() {
        return produitService.produitList();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<PieChart.Data> datas = FXCollections.observableArrayList();
        //ObservableList<BarChart<String,Number>> databar = FXCollections.observableArrayList();
        XYChart.Series series = new XYChart.Series<>();
        Random random = new Random();
        for (Produit p : listProduit()) {

            List<ContenirVente> list = contenirVenteService.listParVente(p);
            int tot = 0;
            for (ContenirVente cv : list) {
                tot += cv.getQteVen();
            }
            datas.add(new PieChart.Data(p.getLibProd(), tot));
            series.getData().add(new XYChart.Data<>(p.getLibProd(), p.getQteIniProd()));
        }
        pieChart.setData(datas);
        barCode.getData().addAll(series);
        btnComp = btnCOmpte;
        btnInvent = btnInventaire;
        btnBil = btnBilan;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s = (Stage) stage.getScene().getWindow();
                if (s.isMaximized()) {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                }
                stage.setPrefWidth(MainViewController.temporaryPaneTot.getWidth());
                cont.setPrefWidth(MainViewController.temporaryPaneTot.getPrefWidth() - 45);
            }
        });
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
        switchPane(Constants.Connect);
    }

    private void switchPane(String pane) {
        try {
            MainViewController.temporaryPane.getChildren().clear();
            StackPane stackPane = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> elements = stackPane.getChildren();
            MainViewController.temporaryPane.getChildren().setAll(elements);
            MainViewController.drawerTmp.close();
            MainViewController.hamburgerTmp = new JFXHamburger();
        } catch (IOException ex) {
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
