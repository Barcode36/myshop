/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;


import com.jfoenix.controls.JFXButton;
import entites.ContenirVente;
import entites.Produit;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.BorderPane;
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
    @FXML
    private HBox hbox1;
    @FXML
    private HBox hbox2;
    
    public static HBox contHbox1;
    public static HBox contHbox2;
    public static JFXButton btnRe;
    public static JFXButton btnComp;
    public static JFXButton btnInvent;
    public static JFXButton btnBil;
     public static JFXButton btnReg;
    public static JFXButton btnAid;
    public static JFXButton btnDec;
    @FXML
    private StackPane ec;
    @FXML
    private AnchorPane cont;
    @FXML
    private JFXButton btnDeconnexion;
    @FXML
    private JFXButton btnClient;
  
    ObservableList<PieChart.Data> datas = FXCollections.observableArrayList();
    ObservableList<ProduitR> obVent = FXCollections.observableArrayList();
    ObservableList<String> obVentNom = FXCollections.observableArrayList();
    ObservableList<Integer> obVentMont = FXCollections.observableArrayList();
    ObservableList<ProduitR> obPro = FXCollections.observableArrayList();
    ObservableList<String> obProFiniNom = FXCollections.observableArrayList();
    ObservableList<Integer> obProFini = FXCollections.observableArrayList();
    ObservableList<String> databar = FXCollections.observableArrayList();
    public static ObservableList<XYChart.Data> listProdEnFin = null ;
    public static ObservableList<PieChart.Data> listMieuxVen = null;
    XYChart.Series series = new XYChart.Series<>();
    Random random = new Random();

    public List<Produit> listProduit() {
        return produitService.produitList();
    }

    /**
     * Initializes the controller class.
     */
    ArrayList<Double> tabDim = new ArrayList<>() ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        btnRe = btnRec;
        btnComp = btnCOmpte;
        btnInvent = btnInventaire;
        btnBil = btnBilan;
        btnReg = btnReglage;
        btnAid=btnAide;
        btnDec=btnDeconnexion;
        
        contHbox1 = hbox1;
        contHbox2 = hbox2;
        
        MainViewController.drawerTmp.setVisible(true);
        MainViewController.drawerTmp.open();
        

        if (MainViewController.initialise == true) {
            if (!MainViewController.typeCompteActif.getLibTyp().equals("Administrateur")) {
                contHbox1.getChildren().removeAll(btnComp,
                            btnInvent,btnBil,btnReg);
                    

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
            }
        });
        
        List <Object []> listMieuxVen = contenirVenteService.listMieuxVen();
        
        if(listMieuxVen!=null && listMieuxVen.size()>0){
        for(Object[] o : listMieuxVen){
                obVentNom.add(  o[0].toString());
                obVentMont.add( Integer.parseInt(o[3]+""));
            }
        }
        //contenirVenteService.
        
        List <Object []> listEnFini = contenirVenteService.listEnFinition();
        for(Object[] o :listEnFini){
            if(o[0]!=null){
                obProFiniNom.add(o[0].toString());
                obProFini.add(Integer.parseInt(o[1]+""));
            }
            
        }
       
        if(obVentNom!=null && obVentNom.size()>=10 && obProFini!=null && obProFini.size()>=10 ){
            for (int i = 0; i < 10; i++) {
                datas.add(new PieChart.Data(obVentNom.get(i), Double.parseDouble(obVentMont.get(i)+"")));
                series.getData().add(new XYChart.Data<>(obProFiniNom.get(i), obProFini.get(i)));

            }
            pieChart.setData(datas);
            barCode.getData().addAll(series);
            listProdEnFin = series.getData() ;
        }
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

                            //System.out.println("seriiies: "+series.getData().toString());
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
            MainViewController.drawerTmp.setVisible(false);
            MainViewController.dshPane.setVisible(false);
            
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
            MainViewController.drawerTmp.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
