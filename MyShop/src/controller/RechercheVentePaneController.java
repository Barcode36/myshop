/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import static controller.CaissePaneController.produitChoisi;
import entites.Produit;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modele.ProduitR;
import modele.ResultatsReq;
import service.IContenirVente;
import service.IHistoriqueVente;
import service.IProduitService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class RechercheVentePaneController implements Initializable {

    @FXML
    private TableView<ResultatsReq> venteTable;
    @FXML
    private TableColumn<ResultatsReq, String> venteNumCol;
    @FXML
    private TableColumn<ResultatsReq, String> montantVenteCol;
    @FXML
    private TableColumn<ResultatsReq, String> ClientCol;
    @FXML
    private TableColumn<ResultatsReq, String> CaissierCol;
    @FXML
    private TableColumn<ResultatsReq, String> dateVenteCol;
    

    IContenirVente contenirVenteService = MainViewController.contenirVenteService;
   

    ObservableList<ResultatsReq> venteList = FXCollections.observableArrayList();
    
    
    @FXML
    private JFXTextField txtRec;
    @FXML
    private AnchorPane stage;
    @FXML
    private AnchorPane cont;
    
    public static TableView<ResultatsReq> vtTableRecVte;

    public List<Object[]> listVente() { //listvente ici le fre par requete
        return contenirVenteService.findAllVteDesc();
    }
   

    public void loadInventairegrid() {
        venteList.clear();
        List<Object[]> lv = listVente();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
         
        if(lv!=null){
            for (Object[] o : lv) {
                
                Date dt = (Date) o[5];
                if(dt != null){
                    //System.out.println("dt "+dt+" df "+df.format( new Date(dt.getTime())));
                    venteList.add(new ResultatsReq(
                        new SimpleStringProperty(o[0]+""),
                        new SimpleStringProperty(o[1]+""),
                        new SimpleStringProperty(o[2]+""),
                        new SimpleStringProperty(o[3]+""), 
                        new SimpleStringProperty(o[4]+""), 
                        new SimpleStringProperty(df.format( new Date(dt.getTime()))) 
                    ));
                } else {
                    venteList.add(new ResultatsReq(
                        new SimpleStringProperty(o[0]+""),
                        new SimpleStringProperty(o[1]+""),
                        new SimpleStringProperty(o[2]+""),
                        new SimpleStringProperty(o[3]+""), 
                        new SimpleStringProperty(o[4]+""), 
                        new SimpleStringProperty("Non Définie") 
                    ));
                }
                //df.format( new Date(dt.getTime()))+"";
                
                
            }
        } else{
            venteList.add(new ResultatsReq( new SimpleStringProperty(""),new SimpleStringProperty( "")));
        }
        
        //System.out.println("taille "+venteList.size());
        venteNumCol.setCellValueFactory(cellData -> cellData.getValue().getResultString());
        montantVenteCol.setCellValueFactory(cellData -> cellData.getValue().getResultInt());
        ClientCol.setCellValueFactory(cellData -> cellData.getValue().getPrixProd());
        CaissierCol.setCellValueFactory(cellData -> cellData.getValue().getDateVen());
        dateVenteCol.setCellValueFactory(cellData -> cellData.getValue().getNomClt());
      
        venteTable.setItems(venteList);
    }

    /**
     * Initializes the controller class.
     */
    boolean ok = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         MainViewController.temporaryPaneTot.widthProperty().addListener((obs, oldVal, newVal)->{
            if( (Double) newVal <= (Double) oldVal){
                
                    if(!ok){
                       venteTable.setMaxWidth(730);
                       ok=true;
                    }else{
                         venteTable.setMaxWidth(venteTable.getWidth()-5);
                    }  
            }
            
        });
        
        
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
        loadInventairegrid();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s =(Stage) stage.getScene().getWindow();
                if(s.isMaximized()){
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                    System.out.println(s.getWidth());
                }
                //stage.setPrefWidth(MainViewController.temporaryPaneTot.getWidth());
                //cont.setPrefWidth(MainViewController.temporaryPaneTot.getWidth() - 135);
            }
        });
        
        /*MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stage.setPrefWidth(newValue.doubleValue());
                cont.setPrefWidth(newValue.doubleValue() - 135);
            }

        });*/
        
        vtTableRecVte = venteTable;
    }

    @FXML
    private void recherche(KeyEvent event) {
        venteList.clear();
        
        if(!txtRec.getText().equals("")){
            List<Object []> list = contenirVenteService.findVenteByLike( Integer.parseInt(txtRec.getText()) );
            //System.out.println(list.size());
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            for (Object[] o : list) {
                Date dt = (Date) o[5];
                if(dt != null){
                    //System.out.println("dt "+dt+" df "+df.format( new Date(dt.getTime())));
                    venteList.add(new ResultatsReq(
                        new SimpleStringProperty(o[0]+""),
                        new SimpleStringProperty(o[1]+""),
                        new SimpleStringProperty(o[2]+""),
                        new SimpleStringProperty(o[3]+""), 
                        new SimpleStringProperty(o[4]+""), 
                        new SimpleStringProperty(df.format( new Date(dt.getTime()))) 
                    ));
                } else {
                    venteList.add(new ResultatsReq(
                        new SimpleStringProperty(o[0]+""),
                        new SimpleStringProperty(o[1]+""),
                        new SimpleStringProperty(o[2]+""),
                        new SimpleStringProperty(o[3]+""), 
                        new SimpleStringProperty(o[4]+""), 
                        new SimpleStringProperty("Non Définie") 
                    ));
                }
            }
            
            venteNumCol.setCellValueFactory(cellData -> cellData.getValue().getResultString());
            montantVenteCol.setCellValueFactory(cellData -> cellData.getValue().getResultInt());
            ClientCol.setCellValueFactory(cellData -> cellData.getValue().getPrixProd().concat("("+cellData.getValue().getMtVte().get()+")"));
            CaissierCol.setCellValueFactory(cellData -> cellData.getValue().getDateVen());
            dateVenteCol.setCellValueFactory(cellData -> cellData.getValue().getNomClt());

            venteTable.setItems(venteList);
            //System.out.println(list);
        }else {
            loadInventairegrid();
        }
        
        //System.out.println(list);
        
    }
    
    @FXML
    private void detailsVente(MouseEvent event) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/views/DetailsVente.fxml").openStream());
            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/essai.css").toExternalForm());
            stage.getIcons().add(new Image(CaissePaneController.class.getResourceAsStream("/img/afnacos.ico")));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
