/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import entites.Client;
import entites.Compte;
import entites.TypeCompte;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import service.IClientService;
import service.ICompteService;
import service.IContenirVente;
import service.IProduitService;
import service.ITypeService;
import service.IVenteService;
import service.imp.ClientService;
import service.imp.CompteService;
import service.imp.ContenirVenteService;
import service.imp.ProduitService;
import service.imp.TypeService;
import service.imp.VenteService;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class MainViewController implements Initializable {
    
    @FXML
    private JFXToolbar toolbar;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private JFXDrawer drawer;
    public static JFXDrawer drawerTmp;
    public static AnchorPane temporaryPane;
    public static AnchorPane temporaryPaneTot;
    public static JFXHamburger hamburgerTmp;
    public static IProduitService produitService = new ProduitService();
    public static IContenirVente contenirVenteService = new ContenirVenteService();
    public static ICompteService compteServiceD = new CompteService();
    public static ITypeService typeServiceD = new TypeService();
    public static IVenteService venteService = new VenteService();
    public static IClientService clientService = new ClientService();
    public static Compte compteActif = new Compte();
    public static TypeCompte typeCompteActif = new TypeCompte();
    public static Boolean initialise = false;
    public static VBox menuL = null;
    public static Label mainCss;
    
    ITypeService typeService = new TypeService();
    ICompteService compteService = compteServiceD;
    @FXML
    private AnchorPane stageTot;
    @FXML
    private Label maiCss;
    
    public List<TypeCompte> listTypeCompte() {
        return typeService.typeCmopteList();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (listTypeCompte().size() == 0) {
            TypeCompte tc = new TypeCompte();
            tc.setLibTyp("Administrateur");
            typeService.ajouter(tc);
            Compte c = new Compte();
            c.setEtatComp("actif");
            c.setIdTypComp(tc.getIdTyp());
            c.setMdpComp("90628725");
            c.setNomComp("root");
            c.setPrenomComp("root");
            c.setPseudoComp("admin");
            compteService.ajouter(c);
            TypeCompte tc1 = new TypeCompte();
            tc1.setLibTyp("Caissier");
            typeService.ajouter(tc1);
            Client clt = new Client();
            clt.setNomClt("Inconnu");
            clt.setAdrClt("Inconnu");
            clt.setNumClt("Inconnu");
            clientService.ajouter(clt);
        }
        temporaryPane = contentPane;
        temporaryPaneTot = stageTot;
        drawerTmp = drawer;
        hamburgerTmp = hamburger;
        mainCss = maiCss;
        
        
        initDrawer();
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
        
    }
    
    private void initDrawer() {
        try {
            VBox menu = null;
            menu = FXMLLoader.load(getClass().getResource(Constants.MenuLateral));
            
            drawer.setSidePane(menu);
            HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
            transition.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
                //transition.setRate(transition.getRate() * -1);
                //transition.play();
                if (drawer.isShown()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    @FXML
    private void Acceuil(MouseEvent event) {
        switchPane(Constants.DashBoard);
    }
    
}
