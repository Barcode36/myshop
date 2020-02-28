/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import static controller.CaissePaneController.clientNew;
import static controller.MainViewController.produitService;
import static controller.newStockController.lFour;
import entites.Produit;
import entites.Stock;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modele.ResultatsReq;
import modele.StockR;
import service.IContenirVente;
import service.IHistoriqueVente;
import service.IStockService;
import service.imp.StockService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class StockInfosController implements Initializable {

    @FXML
    private TableView<ResultatsReq> resultTable;
    @FXML
    private TableColumn<ResultatsReq, String> stockColumn;
    @FXML
    private TableColumn<ResultatsReq, String> resultColumn;
    @FXML
    private Button newStockBtn;
    @FXML
    private Button prodValidBtn;
    @FXML
    private Button stockValidBtn;
     @FXML
    private Button prodModifPrix;
    @FXML
    private TextField actualPriceField;
    @FXML
    private TextField benRechFieldProd;
    @FXML
    private TextField benRechFieldStock;
    @FXML
    private TextField previsionnelPrice;
    @FXML
    private ComboBox <String> actionCombo;
    @FXML
    private DatePicker triDtPicker;
    @FXML
    private DatePicker dtPickerFrom;
    @FXML
    private DatePicker dtPickerTo;
    @FXML
    private TitledPane productPane;
    @FXML
    private AnchorPane stockAncPane;
    @FXML
    private ScrollPane scPane;
    @FXML
    private VBox stockVBox;
    @FXML
    private Label totLabel;
    
    IContenirVente contenirVenteService = MainViewController.contenirVenteService;
    IHistoriqueVente historiqueVenteService = MainViewController.historiqueVenteService;

    ObservableList<ResultatsReq> detailsVenteList = FXCollections.observableArrayList();
    ObservableList<ResultatsReq> venteListImp = FXCollections.observableArrayList();
     ObservableList<StockR> stockList = FXCollections.observableArrayList();
     IStockService stockService = new StockService() ;
    
    int tot = 0;
    
    private List<String> shopInfos ;

    public List<Object[]> detailsVente(int idVente) {
        return contenirVenteService.findDetailsVte(idVente) ;
    }
    
    public List<Object[]> listStock(int idProd) {
        return stockService.findAll(idProd);
    }

   /* private void acceptTextFieldFormat(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                    
                }
            }
        });
        
    }
    
    //private boolean verifTextField(TextField)

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
        
        IStockService stockService = new StockService() ;
        List <Object[]> l = null;
        l = stockService.findAll(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getIdProd().get()) ;
        for (Object[] stk : l) {
           
            stockList.add(new StockR(
                    new SimpleStringProperty(stk[0]+""),
                    new SimpleIntegerProperty(Integer.parseInt(stk[1]+"")),
                    new SimpleIntegerProperty(Integer.parseInt(stk[2]+"")),
                     new SimpleIntegerProperty(Integer.parseInt(stk[3]+"")),
                    new SimpleStringProperty(stk[4]+"")
            ));
            
        }
        
        actualPriceField.setText(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getPrixUniProd().get());
        
        ObservableList<String> listAction = FXCollections.observableArrayList(); 
         /*SimpleStringProperty act0 = new SimpleStringProperty("0");
         SimpleStringProperty act0act = new SimpleStringProperty("Benefice actuel sur ce produit");
         SimpleStringProperty act1 = new SimpleStringProperty("1");
         SimpleStringProperty act1act = new SimpleStringProperty("Benefice total sur ce produit");
         SimpleStringProperty act2 = new SimpleStringProperty("2");
         SimpleStringProperty act2act = new SimpleStringProperty("Benefice moyen sur ce produit");
         
         
        //listAction.addAll( new ResultatsReq(act0,act0act),new ResultatsReq(act1,act1act),new ResultatsReq(act2,act2act));*/
        listAction.addAll("Benefice actuel sur ce produit","Benefice total sur ce produit","Benefice moyen sur ce produit");
                
        actionCombo.setItems(listAction);
       
         CheckBox[] cbs = new CheckBox[stockList.size()];
         TitledPane[] tps = new TitledPane[stockList.size()];
         
         int j = 0, smeQte = 0;
         double ly = 15;
         ArrayList <CheckBox> selectedBoxes = new ArrayList<CheckBox>();
         
         
        if(stockList!=null && stockList.size()>0){
           
            for(StockR s:stockList){
                
                AnchorPane p = new AnchorPane();
                Label a = new Label("\n");
                
		Label a1 = new Label("  Qt initiale ");
                Button bt1 = new Button(""+s.getQteIniSTock().get());
                bt1.setMnemonicParsing(false);
                bt1.setMouseTransparent(true);
                bt1.setMinSize(25, 20);
                bt1.setLayoutY(ly);
                bt1.setStyle("-fx-background-radius: 15px;-fx-background-color:#D473D4;-fx-text-fill:#ffff;-fx-font-size:10;");
                ly+=30;
                Label a11 = new Label("\n");
                
                
                Label a2 = new Label( " Valeur initiale du stock ");
                Button bt2 = new Button(""+s.getCoutAchatStock().get()*s.getQteIniSTock().get());
                bt2.setMnemonicParsing(false);
                bt2.setMouseTransparent(true);
                bt2.setMinSize(25, 20);
                bt2.setLayoutY(ly);
                bt2.setStyle("-fx-background-radius: 15px;-fx-background-color:#D473D4;-fx-text-fill:#ffff;-fx-font-size:10;");
                ly+=30;
                Label a12 = new Label("\n");
                
                Label a3 = new Label("  Qt vendue ");
                Button bt3 = new Button(""+(s.getQteIniSTock().get() - s.getQteActuStock().get()));
                bt3.setMnemonicParsing(false);
                bt3.setMouseTransparent(true);
                bt3.setMinSize(25, 20);
                bt3.setLayoutY(ly);
                bt3.setStyle("-fx-background-radius: 15px;-fx-background-color:#D473D4;-fx-text-fill:#ffff;-fx-font-size:10;");
                ly+=30;
                Label a13 = new Label("\n");
                
                Label a4 = new Label(" Valeur actuelle du stock ");
                Button bt4 = new Button(""+(s.getQteActuStock().get()*(Integer.parseInt(actualPriceField.getText()))));
                bt4.setMnemonicParsing(false);
                bt4.setMouseTransparent(true);
                bt4.setMinSize(25, 20);
                bt4.setLayoutY(ly);
                bt4.setStyle("-fx-background-radius: 15px;-fx-background-color:#D473D4;-fx-text-fill:#ffff;-fx-font-size:10;");
                ly+=30;
                Label a14 = new Label("\n");
                
                Label a5 = new Label("  Qt actuelle ");
                Button bt5 = new Button(""+s.getQteActuStock().get());
                bt5.setMnemonicParsing(false);
                bt5.setMouseTransparent(true);
                bt5.setMinSize(25, 20);
                bt5.setLayoutY(ly);
                bt5.setStyle("-fx-background-radius: 15px;-fx-background-color:#D473D4;-fx-text-fill:#ffff;-fx-font-size:10;");
                ly+=30;
                Label a15 = new Label("\n");
                
                Label a6 = new Label(" Prévision benefice du stock ");
                Button bt6 = new Button(""+ (Double.parseDouble(actualPriceField.getText())*s.getQteIniSTock().get() - s.getCoutAchatStock().get()*s.getQteIniSTock().get()));
                bt6.setMnemonicParsing(false);
                bt6.setMouseTransparent(true);
                bt6.setMinSize(25, 20);
                bt6.setLayoutY(ly);
                bt6.setStyle("-fx-background-radius: 15px;-fx-background-color:#D473D4;-fx-text-fill:#ffff;-fx-font-size:10;");
                ly+=30;
                Label a16 = new Label("\n");
                
                Label a7 = new Label("  Prix unitaire d'achat");
                Button bt7 = new Button(""+s.getCoutAchatStock().get());
                bt7.setMnemonicParsing(false);
                bt7.setMouseTransparent(true);
                bt7.setMinSize(25, 20);
                bt7.setLayoutY(ly);
                bt7.setStyle("-fx-background-radius: 15px;-fx-background-color:#D473D4;-fx-text-fill:#ffff;-fx-font-size:10;");
                Label a17 = new Label("\n");
                
                CheckBox cb = cbs[j] = new CheckBox();
                cb.setId(""+j);
             
                 VBox labBtnContainer = new VBox();
                 double sp = 15;
                 HBox labBtnMiniCont = new HBox();
                 labBtnMiniCont.getChildren().add(a);
                 HBox labBtnMiniCont1 = new HBox();
                 labBtnMiniCont1.setSpacing(sp);
                 labBtnMiniCont1.getChildren().addAll(a1,bt1,a2,bt2,a11,a12);
                 
                 HBox labBtnMiniCont2 = new HBox();
                 labBtnMiniCont2.setSpacing(sp);
                 labBtnMiniCont2.getChildren().addAll(a3,bt3,a4,bt4,a13,a14);
                 
                 HBox labBtnMiniCont3 = new HBox();
                 labBtnMiniCont3.setSpacing(sp);
                 labBtnMiniCont3.getChildren().addAll(a5,bt5,a6,bt6,a15,a16);
                 //labBtnContainer.getChildren().add(labBtnMiniCont3);
                 
                 HBox labBtnMiniCont4 = new HBox();
                 labBtnMiniCont4.setSpacing(sp);
                 labBtnMiniCont4.getChildren().addAll(a7,bt7,a17);
                 
                 labBtnContainer.getChildren().addAll(labBtnMiniCont,labBtnMiniCont1,labBtnMiniCont2,labBtnMiniCont3,
                         labBtnMiniCont4
                 );
                 
               p.getChildren().add(labBtnContainer);
                
                HBox c = new HBox();
                
                final TitledPane t = tps[j] = new TitledPane(""+s.getCodeStock().get()+" ("+s.getQteActuStock().get()+")", p);
                t.setExpanded(false);
             
                c.getChildren().addAll(cb, t);
             
                cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
                       if( new_val == true){
                           selectedBoxes.add(cb);
                           System.out.println("new_val "+new_val+" tle "+selectedBoxes.size());
                       }else {
                            selectedBoxes.remove(cb);
                            System.out.println("new_val "+new_val+" tle "+selectedBoxes.size());
                       }

                        benRechFieldStock.setDisable(false);
                        benRechFieldProd.setDisable(true);
                    }
                });
              
                stockVBox.getChildren().add(c);
              
                j++;
                
                scPane.setVvalue(stockVBox.getHeight());
                scPane.setHvalue(stockVBox.getWidth());
                
                smeQte = s.getQteActuStock().get()+smeQte;
                
            }
        }
        
        benRechFieldProd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    benRechFieldProd.setText(newValue.replaceAll("[^\\d]", ""));
                    
                }
            }
        });
        
        prodValidBtn.setOnAction( event -> {
            
            if(!benRechFieldProd.getText().equalsIgnoreCase("") ){
//                verifTextFieldFormat(actualPriceField, benRechFieldProd.getText());
                int ben = Integer.parseInt(benRechFieldProd.getText());
                Produit prod = new Produit();
                prod.setIdProd(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getIdProd().get());
                Produit produitModif = MainViewController.produitService.findById(prod);
                int qte =  produitModif.getQteIniProd();
                int prixAchat =0;
                
                for(StockR stck : stockList){
                    prixAchat+= stck.getCoutAchatStock().get();
                }
                previsionnelPrice.setText(""+Math.round(prixUniPrevisionnel(ben, qte, prixAchat/(stockList.size()))) );
                actualPriceField.setEditable(true);

            }

        });
        
        benRechFieldStock.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable1, String oldValue1, 
                String newValue1) {
                if (!newValue1.matches("\\d*")) {
                    benRechFieldProd.setText(newValue1.replaceAll("[^\\d]", ""));
                }else{
                    System.out.println("non non non ");
                }
            }
        });
        
        stockValidBtn.setOnAction( event -> {
            
            if(!benRechFieldStock.getText().equalsIgnoreCase("")){
                int ben = Integer.parseInt(benRechFieldStock.getText());
                int qte = 0;
                int previP = 0;
                for(CheckBox cbx: selectedBoxes){
                    qte += stockList.get(Integer.parseInt(cbx.getId())).getQteActuStock().get() ;
                    previP += Math.round(prixUniPrevisionnel(ben, qte, stockList.get(Integer.parseInt(cbx.getId())).getCoutAchatStock().get()));
                    System.out.println(previP+" previ "+Integer.parseInt(cbx.getId())+" qte "+qte);
                    
                }

                previsionnelPrice.setText(""+previP );
                actualPriceField.setEditable(true);
            }
                    
        });
                
         productPane.setText(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getLibProd().get()+" ("
                + smeQte+" - "+stockList.size()+" stocks)"
        );
         
        prodModifPrix.setOnAction( event -> {
            modifPrix(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getIdProd().get(), Integer.parseInt(actualPriceField.getText()) );
            Stage st = (Stage) newStockBtn.getScene().getWindow();
            st.close();
            switchPane(Constants.RechercheProd);
          }
        );
        
        actionCombo.setOnAction(eveent -> {
                if(  actionCombo.getSelectionModel().getSelectedItem().contains(" actuel ")  ){
                ObservableList<ResultatsReq> stList = FXCollections.observableArrayList();
                int benTot = 0;
                for(StockR stk : stockList){
                    int ben = beneficeActu(stk.getCoutAchatStock().get(),
                            Integer.parseInt(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getPrixUniProd().get()), 
                            stk.getQteIniSTock().get() - stk.getQteActuStock().get());

                    benTot += ben;

                    SimpleStringProperty sp = new SimpleStringProperty(ben+"");

                    stList.add(new ResultatsReq(stk.getCodeStock(),sp));

                }

                stockColumn.setCellValueFactory(cellData -> cellData.getValue().getResultString());
                resultColumn.setCellValueFactory(cellData -> cellData.getValue().getResultInt());
                resultTable.setItems(stList);

                totLabel.setText( "TOTAL: "+ benTot);
                totLabel.setStyle("-fx-background-color: lightgreen;");

            }
        });
        
        System.out.println("selected: "+actionCombo.getSelectionModel().getSelectedIndex());
        
        
        
//        scPane.vvalueProperty().bind(stockVBox.heightProperty().add(tps[j-1].getHeight()+50));
        //scPane.hvalueProperty().bind(stockVBox.widthProperty());
    };
    
    private int beneficeActu(int prixAchat, int prixVte, int qteVendue){
        return (prixVte - prixAchat) * qteVendue;
    }
    
    
    
    public static double prixUniPrevisionnel(int benefice, int qteActu, Integer prixAchatUni){
        
        return (prixAchatUni * qteActu + benefice)/qteActu;
    }
    
    
    private void modifPrix(int idProd,Integer prix){
        Produit p = new Produit(idProd);
        
        Produit produitModif = produitService.findById(p);
       
        produitModif.setPrixUniProd(prix+"");
        produitService.modifier(produitModif);
        
        TrayNotification notification = new TrayNotification();
        notification.setAnimationType(AnimationType.POPUP);
        notification.setTray("MyShop", "Modification effectuée", NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.seconds(1));
        switchPane(Constants.RechercheProd);
    }
   
   
 
    @FXML
    private void openNewStock(ActionEvent event) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/views/newStock.fxml").openStream());
            Stage stage = new Stage();

            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("/css/essai.css").toExternalForm());
            stage.getIcons().add(new Image(CaissePaneController.class.getResourceAsStream("/img/afnacos.ico")));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.show();
             Stage st = (Stage) newStockBtn.getScene().getWindow();
             st.close();

            /*stage.setOnHidden(ex -> {
                //  cltCombo.setValue(clientNew);
                try {
                    txtClt.setText(clientNew.getNomClt().getValue() + " (" + clientNew.getNumClt().getValue() + ")");
                } catch (Exception e) {
                }

            });*/

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private void switchPane(String pane) {
        try {
            MainViewController.temporaryPane.getChildren().clear();
            StackPane stackPane = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> elements = stackPane.getChildren();

            MainViewController.temporaryPane.getChildren().setAll(elements);

            MainViewController.drawerTmp.setVisible(true);
            // MainViewController.hamburgerTmp = new JFXHamburger();
        } catch (IOException ex) {
            Logger.getLogger(MenuLateraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
