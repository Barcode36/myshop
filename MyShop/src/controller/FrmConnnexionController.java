/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import entites.Compte;
import entites.TypeCompte;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class FrmConnnexionController implements Initializable {

    @FXML
    private JFXComboBox<String> txtPseudoCennect;
    @FXML
    private JFXPasswordField txtPassConnect;

    ObservableList<String> compteList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane cont;
    
    public static String ky;

    /**
     * Initializes the controller class.
     */
    public List<Compte> listCompte() {
        return MainViewController.compteServiceD.compteList();
    }

    private void fillCompteCombo() {
        compteList.clear();
        for (Compte c : listCompte()) {
            compteList.add(c.getPseudoComp());
        }
        txtPseudoCennect.setItems(compteList);
    }
    
     boolean ok;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MainViewController.hamburgerTmp.setVisible(false);
        MainViewController.mainCss.setVisible(false);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
        fillCompteCombo();
        
       
    }

    @FXML
    private void connexionButton(ActionEvent event) {
        Compte c = new Compte();
       
       
        c.setPseudoComp(txtPseudoCennect.getJFXEditor().getText());
        System.out.println("getval: "+txtPseudoCennect.getJFXEditor().getText());
        c.setMdpComp(txtPassConnect.getText());
        
        try {
            
           /// MainViewController.compteServiceD.
           if(txtPseudoCennect.getJFXEditor().getText().equals("admin")){
                Compte cp = MainViewController.compteServiceD.findByPseudoComp(txtPseudoCennect.getJFXEditor().getText());
                 ky = cp.getNomComp().split(" ")[1];
                System.out.println("cp "+cp.getMdpComp()+" ky "+ky);

                //if(cp !=null){
                   // javax.swing.JOptionPane.showMessageDialog(null,"MyShop Info trouveéééé \n");

                    //System.out.println("pk"+ PasswordEncrypt.verifyUserPassword(txtPassConnect.getText(), cp.getMdpComp(), ky));   
                if(PasswordEncrypt.verifyUserPassword(txtPassConnect.getText(), cp.getMdpComp(), ky)){
                    c.setMdpComp(PasswordEncrypt.generateSecurePassword(txtPassConnect.getText(), ky)); 
                    System.out.println("cp222 "+c.getMdpComp()+" ky "+ky);
                    System.out.println("comp: "+cp.getMdpComp().equals(c.getMdpComp()));
                    
                    //ReglagePaneController.txtShopNum.setEditable(false);
                    //ReglagePaneController.txtShpName.setEditable(false);
                }
                
           }
            
                
            //}
            
            MainViewController.compteActif = MainViewController.compteServiceD.Connexion(c);
            /*javax.swing.JOptionPane.showMessageDialog(null,"MyShop Info \n"
                    + ""+MainViewController.compteActif.getMdpComp()
                    + "Contactez le +228 90628725 pour plus d'informations! Merci"); */
            TypeCompte compte = new TypeCompte(MainViewController.compteActif.getIdTypComp());
            MainViewController.typeCompteActif = MainViewController.typeServiceD.findById(compte);
            MainViewController.temporaryPane.getChildren().clear();
            
            FXMLLoader fxmlLoader  = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(Constants.DashBoard));
            StackPane dashBoard = fxmlLoader.load();
            
            MainViewController.temporaryPane.getChildren().setAll(dashBoard);
            
            MainViewController.hamburgerTmp.setVisible(false);
            MainViewController.mainCss.setVisible(false);
            MainViewController.dshPane.setVisible(true);
                       
            
            if (!MainViewController.typeCompteActif.getLibTyp().equals("Administrateur")) {


                DashBoardController.contHbox1.getChildren().removeAll(DashBoardController.btnComp,
                        DashBoardController.btnInvent,DashBoardController.btnBil,DashBoardController.btnReg);

                DashBoardController.contHbox1.getChildren().addAll(DashBoardController.btnAid,
                        DashBoardController.btnDec);

                DashBoardController.contHbox2.getChildren().clear();
                VBox menu = null;
                menu = FXMLLoader.load(getClass().getResource(Constants.MenuLateralC));
                MainViewController.drawerTmp.setSidePane(menu);
            }else{

            }
            MainViewController.dshPane.setVisible(true);
           
             
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(" "+e);
            e.printStackTrace();
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Pseudo ou Mot de passe incorrect", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(1.5));
        }
        //System.out.println("verif: "+txtPassConnect.getText());
//        System.out.println("PasswordEncr "+MainViewController.compteActif.getNomComp().split(" ")[1]);
       // System.out.println("MainViewControl "+MainViewController.compteActif.getMdpComp());
        
        /*if (MainViewController.typeCompteActif.getLibTyp().equals("Administrateur") && PasswordEncrypt.verifyUserPassword(txtPassConnect.getText(), MainViewController.compteActif.getMdpComp(), PasswordEncrypt.getSalt(30))  
                 ){
        //if (MainViewController.typeCompteActif.getLibTyp().equals("Administrateur") && MainViewController.compteActif.getNomComp().equals("root")
        //        ){    
        TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Bienvenu M. Ulrich ", NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.seconds(1.5));
        }else{
            System.out.println("");
                    System.out.println("PasswordEncrypt.getSalt(30) "+PasswordEncrypt.getSalt(30));

        }*/
    }

    @FXML
    private void cancel(ActionEvent event) {
        System.exit(0);
    }

}
