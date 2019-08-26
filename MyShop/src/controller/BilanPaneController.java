/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXDatePicker;
import static controller.DashBoardController.listProdEnFin;
import entites.Client;
import entites.Compte;
import entites.ContenirVente;
import entites.Produit;
import entites.Vente;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
//import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.converter.LocalDateTimeStringConverter;
import modele.ProduitR;
import modele.ResultatsReq;
import modele.VenteR;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import service.ICompteService;
import service.IContenirVente;
import service.IProduitService;
import service.IVenteService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class BilanPaneController implements Initializable {

    @FXML
    private GridPane gridBilan;
    @FXML
    TableColumn<ResultatsReq, String> caissierColVente;
    @FXML
    private TableColumn<ResultatsReq, String> totCaissierColVente;
    @FXML
    private TableView<ResultatsReq> CaissierVenteTable;
    @FXML
    private TableColumn<ResultatsReq, String> DateCaissier;
    @FXML
    private TableColumn<ResultatsReq, String> ProduitCaissier;
    @FXML
    private TableColumn<ResultatsReq, String> QteCaissierCol;
    @FXML
    private TableColumn<ResultatsReq, String> PuCaissierCol;
    @FXML
    private TableColumn<ResultatsReq, String> totDetCaissierCol;
    @FXML
    private TableColumn<ResultatsReq, String> cltCol;
    @FXML
    private TableView<ResultatsReq> tableDetailCaissier;
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
     @FXML
    private Button btnExport;

    IVenteService venteService = MainViewController.venteService;
    IContenirVente contenirVenteService = MainViewController.contenirVenteService;
    IProduitService produitService = MainViewController.produitService;
    ICompteService compteService = MainViewController.compteServiceD;

    ObservableList<VenteR> venteList = FXCollections.observableArrayList();
    ObservableList<ProduitR> produitListVentCaissier = FXCollections.observableArrayList();
     ObservableList<ResultatsReq> produitListVentCaissierFiltre = FXCollections.observableArrayList();
     ObservableList<ProduitR> lTmp = FXCollections.observableArrayList();
     ObservableList<Integer> lCompte = FXCollections.observableArrayList();
     ObservableList<String> caissierListNom = FXCollections.observableArrayList();
     ObservableList<String> caissierListPrenom = FXCollections.observableArrayList();
    ObservableList<Double> caissierListMontant = FXCollections.observableArrayList();
     ObservableList<ResultatsReq> listVParCaissier = FXCollections.observableArrayList();
     
    ObservableList<String> listMois = FXCollections.observableArrayList();
    @FXML
    private AnchorPane stage;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane cont;
    private Pane band;
    @FXML
    private Button btnCLient;
    @FXML
    private ColumnConstraints consCol1;
    @FXML
    private ColumnConstraints consCol2;
    @FXML
    private Label lblMontTotBil;
    @FXML
    private ImageView
            scriptIndicator;


    /**
     * Initializes the controller class.
     */
    Integer total;
    boolean ok = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Bearskin DEMO.otf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-ExtraBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Heebo-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(MainViewController.class.getResource("/css/Jurassic Park.ttf").toExternalForm(), 10);
        mois();
        
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx"));
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s = (Stage) stage.getScene().getWindow();
                if (s.isMaximized()) {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                } else {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                }
                //System.out.println(s.isMaximized());
                //stage.setPrefWidth(MainViewController.temporaryPaneTot.getPrefWidth());
                //cont.setPrefWidth(MainViewController.temporaryPaneTot.getPrefWidth() - 71);
            }
        });
       /* System.out.println(MainViewController.temporaryPaneTot.getPrefWidth());
        MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stage.setPrefWidth(newValue.doubleValue());
                cont.setPrefWidth(newValue.doubleValue() - 71);
                band.setPrefWidth(cont.getPrefWidth() - 300);
            }

        });*/
       scriptIndicator.setVisible(false);
        
    }

    public void loadVenteCaissierDetail(int idCompte)  {
        
         produitListVentCaissierFiltre.clear();
         List<Object []> lVteDet = null;
          if (rbMoisCours.isSelected()) {
            
             LocalDate today = LocalDateTime.now().toLocalDate();
             LocalDate firstDay = LocalDateTime.of(today.getYear(), today.getMonthValue(), 1, 0, 0).toLocalDate();
             
             long todayLong = today.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             long firstDayLong = firstDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             
           java.sql.Date dt1 =  new java.sql.Date(new Timestamp( firstDayLong *1000  ).getTime());
           java.sql.Date dt2 =  new java.sql.Date(new Timestamp(todayLong*1000).getTime());
            
            lVteDet = contenirVenteService.historiqueVente(dt1,dt2,idCompte);
            
            
        } else if(rbMois.isSelected()){
            
             LocalDate today = LocalDateTime.now().toLocalDate();
             LocalDate firstDay = LocalDateTime.of(today.getYear(), moisCombo.getSelectionModel().getSelectedIndex()+1, 1, 0, 0).toLocalDate();
             LocalDate secondDay = LocalDateTime.of(today.getYear(), moisCombo.getSelectionModel().getSelectedIndex()+1, firstDay.lengthOfMonth() , 0, 0).toLocalDate();
             System.out.println("firstDay.lengthOfMonth() "+firstDay.lengthOfMonth());
             long todayLong = firstDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             long firstDayLong = secondDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             
            java.sql.Date dt1 =  new java.sql.Date(new Timestamp( todayLong *1000  ).getTime());
            java.sql.Date dt2 =  new java.sql.Date(new Timestamp(firstDayLong*1000).getTime());
            System.out.println("");
            lVteDet = contenirVenteService.historiqueVente(dt1,dt2,idCompte);
          } else if (rbDeuxDate.isSelected()) {
             
            LocalDate d3 = datePiker1.getValue();
            LocalDate firstDay = LocalDateTime.of(d3.getYear(),d3.getMonthValue(),d3.getDayOfMonth(),0,0).toLocalDate();
            
             d3 = datePiker2.getValue();
            LocalDate secondDay = LocalDateTime.of(d3.getYear(),d3.getMonthValue(),d3.getDayOfMonth(),0,0).toLocalDate();
            long todayLong = firstDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
            long firstDayLong = secondDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
            java.sql.Date dt1 = new java.sql.Date(new Timestamp( todayLong *1000   ).getTime());
            java.sql.Date dt2 = new java.sql.Date(new Timestamp(  firstDayLong*1000  ).getTime());
            
            lVteDet = contenirVenteService.historiqueVente(dt1 ,  dt2,idCompte);
        }
           SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
         
        if(lVteDet!=null){
            for(Object[] o : lVteDet){
               Date dt = (Date) o[4]; 
                 //System.out.println("o[4] "+ dt.getTime() );
                // System.out.println(""+df.format( new Date(dt.getTime())));
                if(o[2]!=null){
                    produitListVentCaissierFiltre.add(new ResultatsReq(
                        new SimpleStringProperty(o[0]+""),
                        new SimpleStringProperty( o[1]+""),
                        new SimpleStringProperty( o[2]+""),
                        new SimpleStringProperty( o[3]+""),
                        new SimpleStringProperty( df.format( new Date(dt.getTime()))+""),
                        new SimpleStringProperty( o[5]+"")
                    ));
                }
                
                
            }
        }else{
            produitListVentCaissierFiltre.add(new ResultatsReq( new SimpleStringProperty(""),new SimpleStringProperty( "")));
        }
        
        totDetCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getMtVte());
        //if(produitListVentCaissierFiltre.)
        PuCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getPrixProd());
        QteCaissierCol.setCellValueFactory(cellData -> cellData.getValue().getResultInt());
        DateCaissier.setCellValueFactory(cellData -> cellData.getValue().getDateVen());
        ProduitCaissier.setCellValueFactory(cellData -> cellData.getValue().getResultString());
        cltCol.setCellValueFactory(cellData -> cellData.getValue().getNomClt());
        
        tableDetailCaissier.setItems(produitListVentCaissierFiltre);
    }

    @FXML
    private void getCaissier(MouseEvent event) {
       int idCompte  ;
        idCompte = Integer.parseInt(CaissierVenteTable.getSelectionModel().getSelectedItem().getPrixProd().getValue() ) ;
        
        //int id = listVParCaissier.
       loadVenteCaissierDetail(idCompte);
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
            tableDetailCaissier.getItems().clear();
            listVParCaissier.clear();
            List<Object[]> lvte = null;
        if (rbMoisCours.isSelected()) {
            
             LocalDate today = LocalDateTime.now().toLocalDate();
             LocalDate firstDay = LocalDateTime.of(today.getYear(), today.getMonthValue(), 1, 0, 0).toLocalDate();
             
             long todayLong = today.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             long firstDayLong = firstDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             
           java.sql.Date dt1 =  new java.sql.Date(new Timestamp( firstDayLong *1000  ).getTime());
           java.sql.Date dt2 =  new java.sql.Date(new Timestamp(todayLong*1000).getTime());
            
            lvte = contenirVenteService.findTotVteEffectueByTwoPeriode (dt1,dt2);
            
            
        } else if(rbMois.isSelected()){
            
             LocalDate today = LocalDateTime.now().toLocalDate();
             LocalDate firstDay = LocalDateTime.of(today.getYear(), moisCombo.getSelectionModel().getSelectedIndex()+1, 1, 0, 0).toLocalDate();
             LocalDate secondDay = LocalDateTime.of(today.getYear(), moisCombo.getSelectionModel().getSelectedIndex()+1, firstDay.lengthOfMonth() , 0, 0).toLocalDate();
             long todayLong = firstDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             long firstDayLong = secondDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             
            java.sql.Date dt1 =  new java.sql.Date(new Timestamp( todayLong *1000  ).getTime());
            java.sql.Date dt2 =  new java.sql.Date(new Timestamp(firstDayLong*1000).getTime());
            System.out.println("");
            lvte = contenirVenteService.findTotVteEffectueByTwoPeriode (dt1,dt2);
          } else if (rbDeuxDate.isSelected()) {
             
            LocalDate d3 = datePiker1.getValue();
            LocalDate firstDay = LocalDateTime.of(d3.getYear(),d3.getMonthValue(),d3.getDayOfMonth(),0,0).toLocalDate();
            
             d3 = datePiker2.getValue();
            LocalDate secondDay = LocalDateTime.of(d3.getYear(),d3.getMonthValue(),d3.getDayOfMonth(),0,0).toLocalDate();
            long todayLong = firstDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
            long firstDayLong = secondDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
            java.sql.Date dt1 = new java.sql.Date(new Timestamp( todayLong *1000   ).getTime());
            java.sql.Date dt2 = new java.sql.Date(new Timestamp(  firstDayLong*1000  ).getTime());
            
            lvte = contenirVenteService.findTotVteEffectueByTwoPeriode( dt1 ,  dt2);
        }
        System.out.println("listVParCaissier "+lvte);
        ObservableList<ResultatsReq > data = FXCollections.observableArrayList();
        //ObservableList<Object> nameRow =  FXCollections.observableArrayList();
        ObservableList<Object> montRow = FXCollections.observableArrayList();
        
        if(lvte!=null){
            for(Object[] o : lvte){
                listVParCaissier.add(new ResultatsReq( new SimpleStringProperty(o[1]+""),
                        new SimpleStringProperty(o[3]+""),
                        new SimpleStringProperty( o[0]+""),
                        new SimpleStringProperty( o[4]+"")
                ));
                
                montRow.add(o[3]);
            }
        }else{
            
            listVParCaissier.add(new ResultatsReq( new SimpleStringProperty(""),new SimpleStringProperty( "")));
         
        }
        
        caissierColVente.setCellValueFactory(cdata -> cdata.getValue().getResultString() );
        totCaissierColVente.setCellValueFactory(cdata -> cdata.getValue().getResultInt());
        CaissierVenteTable.setItems(listVParCaissier);
         
         total = 0;
        int tle = montRow.size();
        if(tle > 0){
             for(int i =0; i< tle;i++){
                total = Integer.parseInt(montRow.get(i)+"") + total;
                lblMontTotBil.setText( "TOTAL: "+ total);
                lblMontTotBil.setStyle("-fx-background-color: lightgreen;"
                        + "-fx-font-size: 30px;");
            }
        } else {
           
                lblMontTotBil.setText( "TOTAL: "+ total);
                lblMontTotBil.setStyle("-fx-background-color: lightgreen;"
                        + "-fx-font-size: 30px;");
        }
       
    }

    @FXML
    private void openClientBil(ActionEvent event) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/views/BilanClient.fxml").openStream());
            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/essai.css").toExternalForm());
            stage.getIcons().add(new Image(BilanPaneController.class.getResourceAsStream("/img/afnacos.ico")));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNIFIED);
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*public void loadVenteCaissierDetailList(VenteR venteR)  {
        produitListVentCaissier.clear();
        
        Compte c = new Compte(venteR.getidCompte().getValue());
        Compte compte = compteService.findById(c);
        List<Vente> list = venteService.ventesParCaissier(compte);
        /*if(rbMois.isSelected()){
             list = venteService.ventesParCaissierMois(compte,moisCombo.getValue());
        } else if(rbDeuxDate.isSelected()){
             list = venteService.ventesEntreDeuxDate(datePiker1.getPromptText(),datePiker2.getPromptText(),compte);
        }
        //List<Vente> list = venteService.ventesParCaissier(compte);
        for (Vente v : list) {
            Client cl = new Client(v.getIdClt());
            Client clt = MainViewController.clientService.findById(cl);
            List<ContenirVente> listCon = contenirVenteService.listParVente(v);
            for (ContenirVente cv : listCon) {
                try {
                    Produit p = new Produit(cv.getIdProd());
                    Produit produit = produitService.findById(p);
                    produitListVentCaissier.add(new ProduitR(produit, v, cv, clt));
                } catch (Exception e) {
                }
            }
        }
         
        for(ProduitR prod:produitListVentCaissier){
            if(rbMoisCours.isSelected()){
               
                 LocalDate dateDuJour = LocalDate.now();
                 
                 String dateAsString[] = prod.getDateVen().getValue().split("-");
                 String mois = dateAsString[1];
                 //System.out.println("ms"+mois);
                 
                 if(Integer.parseInt(mois)==(dateDuJour.getMonthValue())){
                     try {
                         
                        produitListVentCaissierFiltre.add(prod);
                    } catch (Exception e) {
                         System.out.println(""+e);
                    }
                     
                 }
                
             } else if(rbMois.isSelected()){
                 System.out.println("ms: "+moisCombo.getSelectionModel().getSelectedIndex());
                 String dateAsString[] = prod.getDateVen().getValue().split("-");
                 String mois = dateAsString[1];
                 if(moisCombo.getSelectionModel().getSelectedIndex()+1 == Integer.parseInt(mois) ){
                     try {
                         
                        produitListVentCaissierFiltre.add(prod);
                    } catch (Exception e) {
                         System.out.println(""+e);
                    }
                     
                 }
             } else if (rbDeuxDate.isSelected()){
                 String dateAsString[] = prod.getDateVen().getValue().split("-");
                 String datePiker1Split[] = datePiker1.getValue().toString().split("-");
                 String datePiker2Split[] = datePiker2.getValue().toString().split("-");
                 String mois = dateAsString[1];
                 System.out.println("mw: "+mois);
                 System.out.println(""+datePiker1Split[1]);
                 System.out.println(""+datePiker2Split[1]);
                 if(Integer.parseInt(mois)>= Integer.parseInt(datePiker1Split[1])
                         && Integer.parseInt(mois)<= Integer.parseInt(datePiker2Split[1]) ){
                     try {
                         
                        produitListVentCaissierFiltre.add(prod);
                    } catch (Exception e) {
                         System.out.println(""+e);
                    }
                     
                 }
                }
            
        }
        int i =0;
         lTmp.clear();
           lTmp.setAll(produitListVentCaissierFiltre);
           //Comparable <ProduitR> cp;
        List<ProduitR> lTmp2 = null;
        lTmp2.add(lTmp.get(0));
        boolean existe;
        //int tle= lTmp.size();
        for(int j=0; j<lTmp.size();j++){
            for(int k =0;k<lTmp2.size();k++ ){
                if(!(lTmp.get(j).getIdProd().intValue() == lTmp2.get(k).getIdProd().intValue())){
                    lTmp2.add(lTmp.get(j));
                }
            }
            
        }
        System.out.println("ltmp2 "+lTmp2);
        //System.out.println("tmp2 "+lTmp2);
        //lTmp.addAll(produitListVentCaissierFiltre.)
        //System.out.println("ltmp "+lTmp);
        produitListVentCaissierFiltre.clear();
        int qte = 0;
        /*for(ProduitR pdt: lTmp){
            //System.out.println("pdt bn: "+pdt.getIdProd());
            for(ProduitR pdt2: lTmp){
                qte =0;
                //System.out.println("pdt1 "+pdt.getIdProd()+ " pdt2 "+pdt2.getIdProd() );
                if(pdt.getIdProd().getValue().intValue() == pdt2.getIdProd().getValue().intValue() ){
                    qte = qte+pdt.getQteProdCom().intValue()+pdt2.getQteProdCom().intValue();
                    //pdt.setQteProdCom(new SimpleIntegerProperty(pdt.getQteProdCom().intValue()+ pdt2.getQteProdCom().intValue() ));
                    System.out.println("pdt bn: "+pdt.getLibProd()+" qte "+pdt.getQteProdCom().intValue());
                    //produitListVentCaissierFiltre.add(pdt);
                }
            }
            pdt.setQteProdCom(new SimpleIntegerProperty(qte));
            produitListVentCaissierFiltre.add(pdt);
        }
        System.out.println("fltr: "+produitListVentCaissierFiltre);
    }
    
    
    private void loadListDetailVente() {
        
         
//        ObservableList<VenteR> vtList = CaissierVenteTable.getItems();
//        for(VenteR vt : vtList){
            
    //        loadVenteCaissierDetailList(vt);
            //tableDetailCaissier.getItems().clear();
   //     }
        
        //for(Produit pdt)
        
    }*/
    
    private FileChooser fileChooser;
    private File file;
    private Stage stge;
    // Excel work book
    private HSSFWorkbook wb;

    // Fonts
    private HSSFFont headerFont;
    private HSSFFont contentFont;

    // Styles
    private HSSFCellStyle headerStyle;
    private HSSFCellStyle oddRowStyle;
    private HSSFCellStyle evenRowStyle;
    
    
    
    
   @FXML
    private void exportBilan(ActionEvent ev){
        boolean ok = false;
        TrayNotification notification = new TrayNotification();
                        
        
       //javax.swing.JOptionPane.showMessageDialog(null,"exec de exportBilan"); 
        wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Bilan ");
       
        headerFont  = createFont(HSSFColor.WHITE.index, (short)15, true);
	contentFont = createFont(HSSFColor.BLACK.index, (short)12, false);
        
        headerStyle  = createStyle(headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.BLUE_GREY.index,       true, HSSFColor.WHITE.index);
        oddRowStyle  = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.GREY_25_PERCENT.index, true, HSSFColor.GREY_80_PERCENT.index);
	evenRowStyle = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.GREY_40_PERCENT.index, true, HSSFColor.GREY_80_PERCENT.index);
		
//        venteList = CaissierVenteTable.getItems();
        int indexCaiVte = 2;
        int rowIndex = 0;
        HSSFRow header = null;
        HSSFRow row = null;
        HSSFCell headerCell = null;
        
        sheet.setColumnWidth(0,450 * 25);
        header = sheet.createRow(0);
        headerCell = header.createCell(0);
        String periode = "";
        if(rbMoisCours.isSelected()){
             SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(); 
            df.format( new Date(date.getTime()));
            LocalDate today = LocalDateTime.now().toLocalDate();
            periode = today.getMonth()+"";
            
            headerCell.setCellValue("Periode: Du 1er au "+df.format( new Date(date.getTime())));
        }
        if(rbMois.isSelected()){
          //System.out.println("Periode: "+moisCombo.getValue());
          headerCell.setCellValue("Periode: "+moisCombo.getValue());
          periode = moisCombo.getValue()+"";
        }
        if(rbDeuxDate.isSelected()){
             headerCell.setCellValue("Periode: "+datePiker1.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
                + " au "+datePiker2.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            periode = datePiker1.getValue()+"_"+datePiker2.getValue();
        }
       headerCell.setCellStyle(headerStyle);
               
       
        header = sheet.createRow(1);
        headerCell = header.createCell(0);
        headerCell.setCellValue("CAISSIER: ");
        headerCell.setCellStyle(headerStyle);
        
        sheet.setColumnWidth(1,300 * 25);
        headerCell =header.createCell(1);
        headerCell.setCellValue("TOTAL: ");
        headerCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(2, 10 * 25);
        sheet.setColumnWidth(3, 400 * 25);
        headerCell = header.createCell(3);
        headerCell.setCellValue("PRODUITS VENDUS: ");
        headerCell.setCellStyle(headerStyle);
        
         
        for(ResultatsReq  vt : listVParCaissier){
            row = sheet.createRow(indexCaiVte);//row 48
            headerCell=row.createCell(0);
            headerCell.setCellValue(vt.getResultString().getValue()+"");
            headerCell.setCellStyle( rowIndex % 2 == 0 ? oddRowStyle : evenRowStyle );
            headerCell= row.createCell(1);
            headerCell.setCellValue(Integer.parseInt(vt.getResultInt().getValue()+""));
            headerCell.setCellStyle( rowIndex % 2 == 0 ? oddRowStyle : evenRowStyle );
            headerCell= row.createCell(3);
            headerCell.setCellValue(Integer.parseInt(vt.getMtVte().getValue()+""));
            headerCell.setCellStyle( rowIndex % 2 == 0 ? oddRowStyle : evenRowStyle );
            rowIndex++;
            indexCaiVte++;
        }
        indexCaiVte+=2;
        
        //sheet.setColumnWidth(0, 256 * 25);
        header = sheet.createRow(indexCaiVte);//row53
        
        // header = sheet.createRow(indexCaiVte);//row53
        headerCell=header.createCell(0);
        headerCell.setCellValue("Total Vendu: ");
        headerCell.setCellStyle(headerStyle);
        
         headerCell=header.createCell(1);
        headerCell.setCellValue(total);
        headerCell.setCellStyle(headerStyle);
        
        indexCaiVte+=2;
        
        header = sheet.createRow(indexCaiVte);
        headerCell=header.createCell(0);
        headerCell.setCellValue("Produits en rupture de stock: ");
        headerCell.setCellStyle(headerStyle);
                
            
        header = sheet.createRow(indexCaiVte+1);//row 55
        headerCell = header.createCell(0);
        headerCell.setCellValue("Produit ");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(1);
        headerCell.setCellValue("Quantité ");
        headerCell.setCellStyle(headerStyle);
            
        indexCaiVte = indexCaiVte + 2; //48
            
            for(XYChart.Data dt : listProdEnFin){
                row = sheet.createRow(indexCaiVte);//row 48
                headerCell=row.createCell(0);
                headerCell.setCellValue(dt.getXValue()+"");
                headerCell.setCellStyle( rowIndex % 2 == 0 ? oddRowStyle : evenRowStyle );
                headerCell= row.createCell(1);
                headerCell.setCellValue(Integer.parseInt(dt.getYValue()+""));
                headerCell.setCellStyle( rowIndex % 2 == 0 ? oddRowStyle : evenRowStyle );
                rowIndex++;
               indexCaiVte++;
            }
    
        indexCaiVte+=2;
        
        header = sheet.createRow(indexCaiVte);//row68
        headerCell = header.createCell(0);
        headerCell.setCellValue("Produits les mieux vendus: ");
        headerCell.setCellStyle(headerStyle);
          
        header = sheet.createRow(indexCaiVte+1);//row 55
        headerCell = header.createCell(0);
        headerCell.setCellValue("Produit ");
        headerCell.setCellStyle(headerStyle);
        
        headerCell = header.createCell(1);
        headerCell.setCellValue("Quantité Vendue");
        headerCell.setCellStyle(headerStyle);
            
        indexCaiVte = indexCaiVte + 2; //48
        //contenirVenteService.
        List<Object []> lVteDet = null;
        List<Object []> lVteQteDet = null;
        produitListVentCaissierFiltre.clear();
          if (rbMoisCours.isSelected()) {
            
             LocalDate today = LocalDateTime.now().toLocalDate();
             LocalDate firstDay = LocalDateTime.of(today.getYear(), today.getMonthValue(), 1, 0, 0).toLocalDate();
             
             long todayLong = today.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             long firstDayLong = firstDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             
           java.sql.Date dt1 =  new java.sql.Date(new Timestamp( firstDayLong *1000  ).getTime());
           java.sql.Date dt2 =  new java.sql.Date(new Timestamp(todayLong*1000).getTime());
            
            lVteDet = contenirVenteService.listMieuxVenByDate(dt1,dt2);
            //lVteQteDet = contenirVenteService.findTotQteVendueByTwoPeriode(dt1, dt2, rowIndex)
            
        } else if(rbMois.isSelected()){
            
             LocalDate today = LocalDateTime.now().toLocalDate();
             LocalDate firstDay = LocalDateTime.of(today.getYear(), moisCombo.getSelectionModel().getSelectedIndex()+1, 1, 0, 0).toLocalDate();
             LocalDate secondDay = LocalDateTime.of(today.getYear(), moisCombo.getSelectionModel().getSelectedIndex()+1, firstDay.lengthOfMonth() , 0, 0).toLocalDate();
            // System.out.println("firstDay.lengthOfMonth() "+firstDay.lengthOfMonth());
             long todayLong = firstDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             long firstDayLong = secondDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
             
            java.sql.Date dt1 =  new java.sql.Date(new Timestamp( todayLong *1000  ).getTime());
            java.sql.Date dt2 =  new java.sql.Date(new Timestamp(firstDayLong*1000).getTime());
            //System.out.println("");
            lVteDet = contenirVenteService.listMieuxVenByDate(dt1,dt2);
          } else if (rbDeuxDate.isSelected()) {
             
            LocalDate d3 = datePiker1.getValue();
            LocalDate firstDay = LocalDateTime.of(d3.getYear(),d3.getMonthValue(),d3.getDayOfMonth(),0,0).toLocalDate();
            
             d3 = datePiker2.getValue();
            LocalDate secondDay = LocalDateTime.of(d3.getYear(),d3.getMonthValue(),d3.getDayOfMonth(),0,0).toLocalDate();
            long todayLong = firstDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
            long firstDayLong = secondDay.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
            java.sql.Date dt1 = new java.sql.Date(new Timestamp( todayLong *1000   ).getTime());
            java.sql.Date dt2 = new java.sql.Date(new Timestamp(  firstDayLong*1000  ).getTime());
            
            lVteDet = contenirVenteService.listMieuxVenByDate(dt1 ,  dt2);
        }
         int i = 0;
        if(lVteDet!=null){
            for(Object[] o : lVteDet){
               if(i<20){
                    produitListVentCaissierFiltre.add(new ResultatsReq(
                           new SimpleStringProperty(o[0]+""),
                           new SimpleStringProperty( o[1]+"")
                   ));  
                    i++;
               }
                
            }
        }else{
            
            produitListVentCaissierFiltre.add(new ResultatsReq( new SimpleStringProperty(""),
                    new SimpleStringProperty( "")));
         
        }
        for(ResultatsReq dt : produitListVentCaissierFiltre ){
            row = sheet.createRow(indexCaiVte);//row 48
            headerCell=row.createCell(0);
            headerCell.setCellValue(dt.getResultString().getValue()+"");
            headerCell.setCellStyle( rowIndex % 2 == 0 ? oddRowStyle : evenRowStyle );
            headerCell= row.createCell(1);
            headerCell.setCellValue(Double.parseDouble(dt.getResultInt().getValue()+""));
            headerCell.setCellStyle( rowIndex % 2 == 0 ? oddRowStyle : evenRowStyle );
            rowIndex++;
           indexCaiVte++;
        }
          
        try {
           // stge = (Stage) stage.getScene().getWindow();
            file = new File("Ressources/bilan.xls");
            OutputStream fos = new FileOutputStream(file.getAbsolutePath());
            
            wb.write(fos);
            
            fos.close();
            
            try { 
                    file = new File("Ressources/Cam.vbs");
                    InputStream fis = new FileInputStream(file);
                    
                    Process rt = Runtime.getRuntime().exec(new String[] {
                                        "wscript.exe", file.getAbsolutePath()
                                        });
                    //bool yem = true;
                    boolean endScr = rt.isAlive();
                  
                     int j =0;
                     
                    while(rt.isAlive()){
                      
                      j++;
                    }
                    
                   //stage2.hide();
                    
                   //loadingPr(rt);
                    
                    File src = new File("Ressources/bilan.xls");
                    File dst;// = new File("src/bilan.xls");
                   try {
                    stge  = (Stage) anchorPane.getScene().getWindow();
                    fileChooser.setInitialFileName("bilan_"+periode+".xls");
                    dst = fileChooser.showSaveDialog(stge);
                    Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    } catch (FileNotFoundException ex) {
                        System.out.println("ex: "+ex);
                        //Logger.getLogger(MainPrincipalController.class
                        //        .getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex) {
                        System.out.println("ex: "+ex);
                        //Logger.getLogger(MainPrincipalController.class
                         //       .getName()).log(Level.SEVERE, null, ex);
                    }
                    
                     
                     endScr = rt.isAlive();
                    // System.out.println("alii"+endScr);
                    if(endScr == false){
                        //scriptIndicator.setVisible(false);
                        btnExport.setDisable(false);
                        CaissierVenteTable.getItems().clear();
                        tableDetailCaissier.getItems().clear();
                        System.out.println("finished");
                         notification = new TrayNotification();
                        notification.setAnimationType(AnimationType.POPUP);
                        notification.setTray("MyShop", "Exportation terminée", NotificationType.SUCCESS);
                        notification.showAndDismiss(Duration.seconds(1.5));
                    }
                     

                } catch (IOException e) {
                    //System.out.println(e);
                    javax.swing.JOptionPane.showMessageDialog(null,e); 
                    //System.exit(0);
                }   
             
           
        } catch (FileNotFoundException ex) {
            System.out.println("erreur: "+ex);
            javax.swing.JOptionPane.showMessageDialog(null,ex); 

        } catch (IOException ex) {
             System.out.println("erreur2: "+ex);
            javax.swing.JOptionPane.showMessageDialog(null,ex); 
        }
 
//        venteList = CaissierVenteTable.getItems();
        //produitListVentCaissier = tableDetailCaissier.getItems();
    }
    
   
    
    private HSSFFont createFont(short fontColor, short fontHeight, boolean fontBold) {

        HSSFFont font = wb.createFont();
        font.setBold(fontBold);
        font.setColor(fontColor);
        font.setFontName("Arial");
        font.setFontHeightInPoints(fontHeight);

        return font;
    }
    
    private HSSFCellStyle createStyle(HSSFFont font, short cellAlign, short cellColor, boolean cellBorder, short cellBorderColor) {

        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setAlignment(cellAlign);
        style.setFillForegroundColor(cellColor);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        if (cellBorder) {
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

            style.setTopBorderColor(cellBorderColor);
            style.setLeftBorderColor(cellBorderColor);
            style.setRightBorderColor(cellBorderColor);
            style.setBottomBorderColor(cellBorderColor);
        }

        return style;
    }

}
