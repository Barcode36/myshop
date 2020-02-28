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
    private ComboBox actionCombo;
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
    
    public static TitledPane pane;

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

    /*public void loadInventairegrid(int idVente) {
        detailsVenteList.clear();
        List<Object[]> lv = detailsVente(idVente);
        
        if(lv!=null){
            for (Object[] o : lv) {
                detailsVenteList.add(new ResultatsReq(
                        new SimpleStringProperty(o[0]+""),
                        new SimpleStringProperty(o[1]+""),
                        new SimpleStringProperty(o[2]+""),
                        new SimpleStringProperty(o[3]+"")
                    )
                );
                
                tot+= Double.valueOf(o[3]+"");
            }
            produitCol.setCellValueFactory(cellData -> cellData.getValue().getResultString());
            prixUniCol.setCellValueFactory(cellData -> cellData.getValue().getResultInt());
            qteVendueCol.setCellValueFactory(cellData -> cellData.getValue().getPrixProd());
            totalCol.setCellValueFactory(cellData -> cellData.getValue().getMtVte());
            
            venteDetailsTable.setItems(detailsVenteList);
            totLabel.setText("TOTAL: "+tot+" F");
        }        
        
    }*/

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
       // System.out.println(l);
        for (Object[] stk : l) {
           //System.out.println("AJOUT ");
           //idProd, codeStock, coutAchatStock, qteIniSTock, qteActuStock, dateRavi, dateExpi, Fournisseur)
           //produitList.get(produitList.size()-1).setExpiryDate(new SimpleStringProperty(""+new SimpleDateFormat("dd-MM-yyyy").format(new Date(produit.getExpiryDate().getTime()))) );
            //Date dt1 = (Date) stk[6]; 
            //Date dt2 = (Date) stk[7]; 
            stockList.add(new StockR(
                    new SimpleStringProperty(stk[0]+""),
                    new SimpleIntegerProperty(Integer.parseInt(stk[1]+"")),
                    new SimpleIntegerProperty(Integer.parseInt(stk[2]+"")),
                     new SimpleIntegerProperty(Integer.parseInt(stk[3]+"")),
                    new SimpleStringProperty(stk[4]+"")
                    //new SimpleIntegerProperty(Integer.parseInt(stk[2]+"")),
                   // new  SimpleStringProperty(""+new SimpleDateFormat("dd-MM-yyyy").format(dt1)),
                    //new SimpleStringProperty(""+new SimpleDateFormat("dd-MM-yyyy").format(dt2)),
                    //new SimpleStringProperty(stk[3]+"")
            ));
            
            
        }
        
       
        
        actualPriceField.setText(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getPrixUniProd().get());
        
        ObservableList<String> listAction = FXCollections.observableArrayList(); 
        listAction.addAll("Benefice actuel sur ce produit","Benefice total sur ce produit","Benefice moyen sur ce produit");
        actionCombo.setItems(listAction);
        
            /**/
           // ObservableList<StockR> stockList = FXCollections.observableArrayList();
           
        ObservableList <Integer> tabNodeIndice = FXCollections.observableArrayList();
        ObservableList <Integer> selIndice = FXCollections.observableArrayList();
        int i = 0;
         CheckBox[] cbs = new CheckBox[stockList.size()];
         TitledPane[] tps = new TitledPane[stockList.size()];
         
         int j = 0, smeQte = 0;
         double ly = 15;
        if(stockList!=null && stockList.size()>0){
            for(StockR s:stockList){
                
                AnchorPane p = new AnchorPane();
               
		Label a1 = new Label("Quantité initiale ");
                Button bt1 = new Button(""+s.getQteIniSTock().get());
                bt1.setMnemonicParsing(false);
                bt1.setMouseTransparent(true);
                bt1.setMinSize(37, 25);
                bt1.setLayoutY(ly);
                bt1.setStyle("-fx-background-radius: 25px;");
                ly+=30;
                Label a11 = new Label("\n");
                //bt1.setLayoutX(a1.getLayoutX()+5);
                
                Label a2 = new Label( "Valeur initiale du stock ");
                //a2.setLayoutX(a1.getLayoutX() + 20);
                Button bt2 = new Button(""+s.getCoutAchatStock().get()*s.getQteIniSTock().get());
                bt2.setMnemonicParsing(false);
                bt2.setMouseTransparent(true);
                bt2.setMinSize(37, 25);
                bt2.setLayoutY(ly);
                bt2.setStyle("-fx-background-radius: 25px;");
                ly+=30;
                Label a12 = new Label("\n");
                //bt2.setLayoutX(a2.getLayoutX()+5);
                
                Label a3 = new Label("Quantité vendue ");
                Button bt3 = new Button(""+(s.getQteIniSTock().get() - s.getQteActuStock().get()));
                bt3.setMnemonicParsing(false);
                bt3.setMouseTransparent(true);
                bt3.setMinSize(37, 25);
                bt3.setLayoutY(ly);
                bt3.setStyle("-fx-background-radius: 25px;");
                ly+=30;
                Label a13 = new Label("\n");
                //bt1.setLayoutX(a1.getLayoutX()+5);
                
                Label a4 = new Label("Valeur actuelle du stock ");
                //a4.setLayoutX(a1.getLayoutX() + 20);
                Button bt4 = new Button(""+(s.getQteActuStock().get()*s.getCoutAchatStock().get()));
                bt4.setMnemonicParsing(false);
                bt4.setMouseTransparent(true);
                bt4.setMinSize(37, 25);
                bt4.setLayoutY(ly);
                bt4.setStyle("-fx-background-radius: 25px;");
                ly+=30;
                Label a14 = new Label("\n");
                //bt2.setLayoutX(a2.getLayoutX()+5);
                
                Label a5 = new Label("Quantité actuelle ");
                //a4.setLayoutX(a1.getLayoutX() + 20);
                Button bt5 = new Button(""+s.getQteActuStock().get());
                bt5.setMnemonicParsing(false);
                bt5.setMouseTransparent(true);
                bt5.setMinSize(37, 25);
                bt5.setLayoutY(ly);
                bt5.setStyle("-fx-background-radius: 25px;");
                ly+=30;
                Label a15 = new Label("\n");
                //bt2.setLayoutX(a2.getLayoutX()+5);
                
                Label a6 = new Label("Prévision benefice du stock ");
                Button bt6 = new Button(""+ (Double.parseDouble(actualPriceField.getText())*s.getQteIniSTock().get() - s.getCoutAchatStock().get()*s.getQteIniSTock().get()));
                bt6.setMnemonicParsing(false);
                bt6.setMouseTransparent(true);
                bt6.setMinSize(37, 25);
                bt6.setLayoutY(ly);
                bt6.setStyle("-fx-background-radius: 25px;");
                ly+=30;
                  Label a16 = new Label("\n");
                
                Label a7 = new Label("Prix unitaire d'achat");
                Button bt7 = new Button(""+s.getCoutAchatStock().get());
                bt7.setMnemonicParsing(false);
                bt7.setMouseTransparent(true);
                bt7.setMinSize(37, 25);
                bt7.setLayoutY(ly);
                bt7.setStyle("-fx-background-radius: 25px;");
                  Label a17 = new Label("\n");
                //ly+=30;
                
                
                CheckBox cb = cbs[j] = new CheckBox();
                
               // a.setLayoutX(cb.getLayoutX() + 10);
                
               // Label b = new Label("Quantité actuelle: "+s.getQteActuStock().get()+"\n\n");
               // Label c = new Label("Quantité vendue: "+(s.getQteIniSTock().get() - s.getQteActuStock().get()+"\n\n"));
                
                 VBox labBtnContainer = new VBox();
                 //labBtnContainer.
                 double sp = 15;
                 HBox labBtnMiniCont1 = new HBox();
                 labBtnMiniCont1.setSpacing(sp);
                 labBtnMiniCont1.getChildren().addAll(a1,bt1,a2,bt2,a11,a12);
                 //labBtnContainer.getChildren().add(labBtnMiniCont1);
                 
                         
                 HBox labBtnMiniCont2 = new HBox();
                 labBtnMiniCont2.setSpacing(sp);
                 labBtnMiniCont2.getChildren().addAll(a3,bt3,a4,bt4,a13,a14);
               //  labBtnContainer.getChildren().add(labBtnMiniCont2);
                 
                 HBox labBtnMiniCont3 = new HBox();
                 labBtnMiniCont3.setSpacing(sp);
                 labBtnMiniCont3.getChildren().addAll(a5,bt5,a6,bt6,a15,a16);
                 //labBtnContainer.getChildren().add(labBtnMiniCont3);
                 
                 HBox labBtnMiniCont4 = new HBox();
                 labBtnMiniCont4.setSpacing(sp);
                 labBtnMiniCont4.getChildren().addAll(a7,bt7,a17);
                 //labBtnContainer.getChildren().add(labBtnMiniCont4);
                 
                  /*HBox labBtnMiniCont5 = new HBox();
                  labBtnMiniCont5.setSpacing(sp);
                 labBtnMiniCont5.getChildren().addAll(a5,bt5);
                // labBtnContainer.getChildren().add(labBtnMiniCont5);
                 
                  HBox labBtnMiniCont6 = new HBox();
                  labBtnMiniCont6.setSpacing(sp);
                 labBtnMiniCont6.getChildren().addAll(a6,bt6);
                // labBtnContainer.getChildren().add(labBtnMiniCont6);
                 
                  HBox labBtnMiniCont7 = new HBox();
                  labBtnMiniCont7.setSpacing(sp);
                 labBtnMiniCont7.getChildren().addAll(a7,bt7);
                 ,labBtnMiniCont5,labBtnMiniCont6,labBtnMiniCont7
                 */
                 
                 //labBtnContainer.setSpacing(15);
                 labBtnContainer.getChildren().addAll(labBtnMiniCont1,labBtnMiniCont2,labBtnMiniCont3,
                         labBtnMiniCont4);
                 
               p.getChildren().add(labBtnContainer);
                
                
                HBox c = new HBox();
                
                
                tabNodeIndice.add(i);
                tabNodeIndice.add(i++);
                final TitledPane t = tps[j] = new TitledPane(""+s.getCodeStock().get(), p);
               // t.setPrefSize(350, 250);
                t.setExpanded(false);
                
             
                c.getChildren().addAll(cb, t);
             //c.getChildren().get
             int sel = 0;
              selIndice.clear();
                cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
                   if( new_val == true){
                       selIndice.add(selIndice.size()+1);
                       System.out.println("new_val "+new_val+" tle "+selIndice.size());
                   }else if( new_val == false){
                        selIndice.remove(selIndice.size()-1);
                        System.out.println("new_val "+new_val+" tle "+selIndice.size());
                   }
                     
                      benRechFieldStock.setDisable(false);
                      benRechFieldProd.setDisable(true);
                }
            });
                
                
                
             
                //stockVBox.getChildren().add(p);
                stockVBox.getChildren().add(c);
                //p.setLayoutY(p.getLayoutY()+10);
                i+=2;
                j++;
                
                scPane.setVvalue(stockVBox.getHeight());
                scPane.setHvalue(stockVBox.getWidth());
                
                prodValidBtn.setOnAction( event -> {
                    if(!benRechFieldProd.getText().equalsIgnoreCase("")){
                        int ben = Integer.parseInt(benRechFieldProd.getText());
                        Produit prod = new Produit();
                        prod.setIdProd(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getIdProd().get());
                        Produit produitModif = MainViewController.produitService.findById(prod);
                        int qte =  produitModif.getQteIniProd();
                        int prixAchat =0;
                        for(int z =0;z < selIndice.size();z++){
                            prixAchat  = stockList.get(z).getCoutAchatStock().get() + prixAchat;
                        }
                        previsionnelPrice.setText(""+Math.round(prixUniPrevisionnel(ben, qte, prixAchat/(selIndice.size()-1))) );
                        actualPriceField.setEditable(true);

                        //System.out.println("pA "+prixAchat);

                    }

                }
                );
                
                stockValidBtn.setOnAction( event -> {
                    if(!benRechFieldStock.getText().equalsIgnoreCase("")){
                            int ben = Integer.parseInt(benRechFieldStock.getText());
                            /*Produit prod = new Produit();
                            prod.setIdProd(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getIdProd().get());
                            Produit produitModif = MainViewController.produitService.findById(prod);*/
                            int qte = 0;
                             for(int z =0;z < selIndice.size();z++){
                                qte  = stockList.get(z).getQteActuStock().get() + qte;
                                 System.out.println("qte"+qte);
                             }

                            previsionnelPrice.setText(""+Math.round(prixUniPrevisionnel(ben, qte, s.getCoutAchatStock().get())) );
                            actualPriceField.setEditable(true);
                    }
                            int ben = Integer.parseInt(benRechFieldProd.getText());
                            Produit prod = new Produit();
                            prod.setIdProd(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getIdProd().get());
                            Produit produitModif = MainViewController.produitService.findById(prod);
                            int qte =  produitModif.getQteIniProd();

                            //previsionnelPrice.setText(""+Math.round(prixUniPrevisionnel(ben, ben, ben)) );
                            actualPriceField.setEditable(true);

                        }
                    );
                
                smeQte = s.getQteActuStock().get()+smeQte;
                
            }
        }
         productPane.setText(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getLibProd().get()+" ("
                + smeQte+" - "+stockList.size()+" stocks)"
        );
         
          prodModifPrix.setOnAction( event -> {
               modifPrix(RechercheProduitPaneController.recTableProd.getSelectionModel().getSelectedItem().getIdProd().get(), Integer.parseInt(actualPriceField.getText()) );
               switchPane(Constants.RechercheProd);
          }
          );
        
        
//        loadInventairegrid(selectedVteId);
        pane = productPane;
       
    }
    
    private double prixUniPrevisionnel(int benefice, int qteActu, Integer prixAchatUni){
        
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
