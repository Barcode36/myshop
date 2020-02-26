/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utils.Constants;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXCheckBox;
import static controller.DetailsVenteController.MergePages;
import static controller.DetailsVenteController.print;
import entites.Client;
import entites.Compte;
import entites.ContenirVente;
import entites.HistoriqueVente;
import entites.Vente;
import entites.Produit;
import entites.Stock;
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
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.ClientR;
import modele.ProduitR;
import modele.StockR;
import service.IClientService;
import service.ICompteService;
import service.IContenirVente;
import service.IHistoriqueVente;
import service.IProduitService;
import service.IStockService;
import service.IVenteService;
import service.imp.CompteService;
import service.imp.ContenirVenteService;
import service.imp.HistoriqueVenteService;
import service.imp.ProduitService;
import service.imp.StockService;
import service.imp.VenteService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class CaisseConfirmationController extends Traitement implements Initializable {

    @FXML
    private TableView<ProduitR> produitCaisseTable;
    @FXML
    private TableColumn<ProduitR, String> libProdColCaisse;
    @FXML
    private TableColumn<ProduitR, Integer> qteColCaisse;
    @FXML
    private TableColumn<ProduitR, String> prixColCaisse;
    @FXML
    private TableColumn<ProduitR, String> totalColCaisse;
    @FXML
    private TextField txtMontCllt;
    @FXML
    private Label lblTot;
    @FXML
    private TextField txtRemise;
    @FXML
    private TextField txtPtActu;
    @FXML
    private TextField txtCoeff;
    @FXML
    private Button btnClose;
    @FXML
    private JFXCheckBox printCkBx;

    ObservableList<ProduitR> produitListVent = FXCollections.observableArrayList();
    ObservableList<StockR> stockList = FXCollections.observableArrayList();
    
    IVenteService venteService = new VenteService();
    ICompteService compteService = new CompteService();
    IContenirVente contenirVenteService = new ContenirVenteService();
    IProduitService produitService = new ProduitService();
    IStockService stockService = new StockService();
    IHistoriqueVente historiqueVenteService = new HistoriqueVenteService();

    Compte compteActif = new Compte();
    ClientR clientR = new ClientR();
    private boolean corr = true;
    @FXML
    private Label Client;
  
    private List<String> shopInfos ; 

     private File file;
     
     //Integer idVente;
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Client.setText("Vente au client: " + clientR.getNomClt().getValue().toUpperCase()
                        + " (" + clientR.getNumClt().getValue() + ")");
                //txtPtActu.setText(clientR.getNbPoints().getValue()+" Points");
                
                txtMontCllt.setFocusTraversable(true);
                txtMontCllt.requestFocus();
                txtCoeff.setVisible(false);
                
                txtPtActu.setVisible(false);
            }
        });
        shopInfos = new ArrayList<String>();
        try{
            shopInfos = ReglagePaneController.lireFichier("myshopInfos");
        } catch (Exception e) { 
            System.out.println(""+e);
           // e.printStackTrace(); 
        } 
        
        
      
    }

    @FXML
    private void SupprimerProdVent(ActionEvent event) {
    }

    public void setListProd(ObservableList<ProduitR> list, Compte c, ClientR cr) {
        int tot = 0;
        for (ProduitR pr : list) {
            pr.setQteProdCom(new SimpleIntegerProperty(Integer.parseInt(pr.getQteCom().getText())));
            int t = Integer.parseInt(pr.getQteCom().getText()) * Integer.parseInt(pr.getPrixUniProd().getValue());
            pr.setTotal(new SimpleStringProperty(String.valueOf(t)));
            produitListVent.add(pr);
            int totprod = Integer.parseInt(pr.getPrixUniProd().getValue()) * Integer.parseInt(pr.getQteCom().getText());
            tot += totprod;
        }
        lblTot.setText(String.valueOf(tot));
        libProdColCaisse.setCellValueFactory(cellData -> cellData.getValue().getLibProd());
        prixColCaisse.setCellValueFactory(cellData -> cellData.getValue().getPrixUniProd());
        qteColCaisse.setCellValueFactory(cellData -> cellData.getValue().getQteProdCom().asObject());
        totalColCaisse.setCellValueFactory(cellData -> cellData.getValue().getTotal());
        produitCaisseTable.setItems(produitListVent);
        compteActif = c;
        clientR = cr;
        
        if(produitListVent != null && produitListVent.size() !=0){
            for(ProduitR pr : produitListVent){
                
            }
        }
        
        
        
        

    }

    private IClientService clientService = MainViewController.clientService;
    
    @FXML
    private void Valider(ActionEvent event) {
        
        StockR [][] tabStock = null;
        
        Boolean txtNumber = textFieldTypeNumber(txtMontCllt);
        if (txtNumber) {
            if (corr == true) {
                Vente vente = new Vente();
                vente.setDateVen(new Date());
                vente.setIdClt(clientR.getIdClt().getValue());
               
                Client c = new Client(clientR.getIdClt().getValue());
                Client clt = clientService.findById(c);
                clt.setNbPoints(clt.getNbPoints()+ Double.parseDouble(lblTot.getText()) *  10/100 );
                clientService.modifier(clt);
                vente.setIdComp(compteActif.getIdComp());
                venteService.ajouter(vente);
                
                for (ProduitR pr : produitListVent) {
                    ContenirVente contenirVente = new ContenirVente();
                    
                    contenirVente.setQteVen(pr.getQteProdCom().getValue());
                    contenirVente.setIdVen(vente.getIdVen());
                    contenirVente.setIdProd(pr.getIdProd().getValue());
                    contenirVente.setPrixProd(Integer.parseInt(pr.getPrixUniProd().getValue()));
                    contenirVente.setMontVente( new Integer(lblTot.getText()));
                    contenirVente.setDtVente(new Date());
                    
                    contenirVenteService.ajouterContenirVente(contenirVente);
                    
                    HistoriqueVente histoVente = new HistoriqueVente();
                    histoVente.setIdCon(contenirVente.getIdCon());
                    histoVente.setSmeRecue(new Integer( txtMontCllt.getText()));
                    histoVente.setSmeRendue(new Integer(txtRemise.getText()));
                    
                    historiqueVenteService.ajouterHistoriqueVente(histoVente);
                    
                    Produit p = new Produit(pr.getIdProd().getValue());
                    Produit produit = produitService.findById(p);
                    produit.setQteIniProd(produit.getQteIniProd() - pr.getQteProdCom().getValue());

                    produitService.modifier(produit);
                    
                    /*List <Object[]> l = null;
                    l = stockService.findAll(pr.getIdProd().get()) ;
                    //int a,b = 0;
                   // System.out.println(l);
                    for (Object[] stk : l) {
                        
                        stockList.add(new StockR(
                            new SimpleStringProperty(stk[0]+""),
                            new SimpleDoubleProperty(Double.parseDouble(stk[1]+"")),
                            new SimpleIntegerProperty(Integer.parseInt(stk[2]+"")),
                            new SimpleIntegerProperty(Integer.parseInt(stk[3]+"")),
                            new SimpleStringProperty(stk[4]+"")
                        ));            
                    }
                    
                    for (int i=stockList.size()-1;i>=0;i--) {
                        
                        if(stockList.get(i).getQteActuStock().get() > 0 ) {
                            //stockList.get(i).setQteActuStock(
                            //   new SimpleIntegerProperty(stockList.get(i).getQteActuStock().get() - pr.getQteProdCom().getValue())
                           // );
                            
                            Stock st = new Stock(stockList.get(i).getIdStock().get());
                            Stock st2 = stockService.findById(st);
                            st2.setQteActuStock(st2.getQteActuStock() - pr.getQteProdCom().getValue());
                            stockService.modifier(st2);
                            
                        }       
                    }
                    
                    for(int i = 0;i<produitListVent.size();i++){
                            for(int j =0;j<l.size();j++){
                                tabStock[i][j] = stockList.get(i);
                            }
                    }*/
                }
                
                CaissePaneController.vente = true;
                
                //idVente = vente.getIdVen();
                //System.out.println("isSelll: "+printCkBx.isSelected());
                if(printCkBx.isSelected()){
                    createFacture(vente.getIdVen());
                } else {
                    System.out.println("isNtSelll");
                }
                
                
                TrayNotification notification = new TrayNotification();
                notification.setAnimationType(AnimationType.POPUP);
                notification.setTray("MyShop", "Vente Effectuée", NotificationType.SUCCESS);
                notification.showAndDismiss(Duration.seconds(1.5));
                Stage stage = (Stage) btnClose.getScene().getWindow();
                stage.close();

                switchPane(Constants.Caisse);
            } else {
                TrayNotification notification = new TrayNotification();
                notification.setAnimationType(AnimationType.POPUP);
                notification.setTray("MyShop", "Montant incorrect", NotificationType.ERROR);
                notification.showAndDismiss(Duration.seconds(1.5));
            }
        } else {
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Montant doit etre numerique", NotificationType.ERROR);
            notification.showAndDismiss(Duration.seconds(1.5));
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

    @FXML
    private void calculRemise(KeyEvent event) {
        int rem = 0;
        if (!txtMontCllt.getText().equals("")) {
            try {
                rem = Integer.parseInt(txtMontCllt.getText()) - Integer.parseInt(lblTot.getText());
                txtRemise.setText(String.valueOf(rem));
                
                
                if( Integer.parseInt( txtMontCllt.getText()) > Integer.parseInt(lblTot.getText()) ){
                     txtMontCllt.setStyle("Background-color: lightgreen;");
                }
                
            } catch (Exception e) {
            }

        } else {
            txtRemise.clear();
        }
        if (rem < 0 || txtRemise.getText().equals("") ) {
            corr = false;
            txtMontCllt.setStyle(" -fx-background-color: red; "); ;
        } else {
            corr = true;
            txtMontCllt.setStyle("-fx-background-color: lightgreen;");
        }
        
    }

      public void createFacture(Integer idVente){
        Rectangle pageSize =  new Rectangle(340, 380);
        
                Document doc = new Document(pageSize,30f,0,0,0);
                //doc.bottom(0);
                //105.897.257
                
                try{
                    //System.out.println(shopInfos.get(0));
                    PdfWriter.getInstance(doc, new FileOutputStream("facture.pdf"));
                    doc.open();
                    //document.add(new Paragraph(excerptsFromDavidCopperfield[0], new Font(Font.TIMES_ROMAN)));
                    com.itextpdf.text.Font titleFont=new com.itextpdf.text.Font(FontFamily.COURIER,19f);
                    //System.out.println("font "+ti.getSize());
                    Paragraph p = new Paragraph();
                    p.setFont(titleFont);
                    p.setTabSettings(new TabSettings(79f));
                    p.add(Chunk.TABBING);
                    p.add(new Chunk(""+shopInfos.get(0)));
                    doc.add(p);
                    
                    com.itextpdf.text.Font bodyFont=new com.itextpdf.text.Font(FontFamily.COURIER,10f);
                    p = new Paragraph();
                    p.setFont(bodyFont);
                    p.setTabSettings(new TabSettings(85f));
                    p.add(Chunk.TABBING);
                    if(shopInfos.get(1).equals("")){
                        p.add(new Chunk(""));
                    }else{
                        p.add(new Chunk("Tel:"+shopInfos.get(1)));
                    }
                    doc.add(p); 

                    p = new Paragraph();
                    p.setFont(bodyFont);
                    p.setTabSettings(new TabSettings(72f));
                    p.add(Chunk.TABBING);
                    p.add(new Chunk("******************"));
                    doc.add(p); 
                    
                    p = new Paragraph();
                     p.setFont(bodyFont);
                    p.setTabSettings(new TabSettings(82f));
                    p.add(Chunk.TABBING);
                    p.add(new Chunk("Vente N."+idVente));
                    p.setFont(bodyFont);
                    doc.add(p); 

                    p = new Paragraph();
                     p.setFont(bodyFont);
                    p.setTabSettings(new TabSettings(120f));
                    //p.add(Chunk.TABBING);
                    int caiPseudoLength = compteActif.getPseudoComp().length();
                    String recompCaiName = "";
                    if(caiPseudoLength > 7){
                        for(int i=0;i<7;i++){
                            recompCaiName = recompCaiName+compteActif.getPseudoComp().charAt(i);
                        }
                        recompCaiName = recompCaiName+".";
                    }else{
                        recompCaiName = compteActif.getPseudoComp();
                    }
                    
                    p.add(new Chunk("================================================ ")) ;
                    p.add(new Chunk("Cais: "+recompCaiName)) ;
                    p.add(Chunk.TABBING);
                    
                    p.add(new Chunk("Client: "+clientR.getNomClt().getValue()+"\n"));
                    doc.add(p);

                    p = new Paragraph("");
                    p.setFont(bodyFont);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Date date = new Date(); 
                    df.format( new Date(date.getTime()));
                    p.add(new Chunk("Date: "+df.format( new Date(date.getTime()))+" \n"));
                    p.add(new Chunk("================================================ "));
                    doc.add(p);

                    PdfPTable table = new PdfPTable(6);
                    table.setWidths(new float []{2f, 1f, 1f,1.5f,0.5f,1f});
                     table.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.setWidthPercentage(100);
                    PdfPCell cell = new PdfPCell();
                    //cell.setColspan(5);
                    table.getDefaultCell().setBorder(cell.NO_BORDER);
                    
                    
                    table.addCell( new Phrase("Produits ",bodyFont));
                    table.addCell(new Phrase("PU ",bodyFont));
                    table.addCell(new Phrase("Qte ",bodyFont));
                    table.addCell(new Phrase("Total ",bodyFont));
                    table.addCell(new Phrase(" ",bodyFont));
                    table.addCell(new Phrase(" ",bodyFont));
                    
                    ObservableList<ProduitR> pdt =  produitCaisseTable.getItems();
                    //System.out.println("length: "+pdt.size());
                    int tot = 0;
                    for( ProduitR prod : pdt ){
                        
                        String prodName = prod.getLibProd().getValue()+"";
                      
                        String tabNameSplitted[] = prodName.split(" ");
                        String recomposedName = "";
                        if( tabNameSplitted.length <= 1 ){
                            if(tabNameSplitted[0].length()>5){
                                recomposedName = recomposedName+tabNameSplitted[0].substring(0,5)+". ";
                            } else{
                                recomposedName = recomposedName+tabNameSplitted[0].substring(0,tabNameSplitted[0].length())+". ";
                            }
                            
                        } else{
                            for(int i=0;i<2;i++ ){
                            //System.out.println(""+tabNameSplitted[i]);
                            if(tabNameSplitted[i].length()>3){
                                 recomposedName = recomposedName+tabNameSplitted[i].substring(0,3)+". ";
                            }else {
                                if( !recomposedName.equalsIgnoreCase("") || !recomposedName.equalsIgnoreCase(" ")){
                                    recomposedName = recomposedName+tabNameSplitted[i].substring(0,tabNameSplitted[i].length())+". ";
                                }
                            }
                       
                        }
                        }
                        
                        //p.add(new Chunk(""+recomposedName));
                        table.addCell( new Phrase(""+recomposedName,bodyFont));
                        table.addCell( new Phrase(""+prod.getPrixUniProd().getValue(),bodyFont));
                        table.addCell(new Phrase(""+prod.getQteProdCom().getValue(),bodyFont));
                        table.addCell(new Phrase(""+prod.getTotal().getValue(),bodyFont));
                        table.addCell(new Phrase("F",bodyFont));
                        table.addCell(new Phrase(" ",bodyFont));
                        tot+= Integer.parseInt(prod.getTotal().getValue()) ;
                      
                    }
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    
                    table.addCell(new Phrase("Reg: Esp.",bodyFont));
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(new Phrase(CaisseConfirmationController.insertDot(tot+"")+" ",bodyFont) );
                    table.addCell(new Phrase("F",bodyFont));
                    table.addCell(" ");
                    
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    
                    table.addCell(new Phrase("Reçu: "+CaisseConfirmationController.insertDot(txtMontCllt.getText()),bodyFont) );
                    table.addCell(new Phrase("F",bodyFont));
                    table.addCell(new Phrase("Rendu: ",bodyFont) );
                    table.addCell(new Phrase(""+CaisseConfirmationController.insertDot(txtRemise.getText()),bodyFont));
                    table.addCell(new Phrase("F",bodyFont));
                    table.addCell(" ");
                    doc.add(table);
                    
                    p = new Paragraph();
                    p.setFont(bodyFont);
                    p.setTabSettings(new TabSettings(15f));
                    p.add(new Phrase("================================================ ",bodyFont));
                    //System.out.println("siiiiiize : "+shopInfos.size());
                    p.add(Chunk.TABBING);
                    if(shopInfos.size() == 4){
                        if(shopInfos.get(3).equalsIgnoreCase("")){
                            p.add(new Phrase(" Merci de votre visite et à Bientôt !!! ",bodyFont));
                        }else{
                            p.add(new Phrase(shopInfos.get(3)+" ",bodyFont));
                        }
                    } else {
                        p.add(new Phrase(" Merci de votre visite et à Bientôt !!! "));
                    }
                    
                    
                    p.add(new Phrase("================================================ ",bodyFont));
                    doc.add(p);
                    
                    /*com.itextpdf.text.Header
                    HeaderFooter footer = new HeaderFooter(footPh1, footPh2);
                    footer.setAlignment(Element.ALIGN_CENTER);
                    document.setFooter(footer); */
                    
                    p = new Paragraph();
                    com.itextpdf.text.Font footerFont=new com.itextpdf.text.Font(FontFamily.COURIER, 7f);
                    p.setFont(footerFont);
                    p.setTabSettings(new TabSettings(15f));
                    p.add(Chunk.TABBING);
                    p.add(new Chunk("MYSHOP, LOGICIEL DE GESTION EFFICACE DE VOTRE SHOP."));
                   
                    doc.add(p);
                    
                    p = new Paragraph();
                    p.setFont(footerFont);
                    p.setTabSettings(new TabSettings(70f));
                    p.add(Chunk.TABBING);
                    p.add(new Chunk("CONTACT:(00228)90628725",footerFont));
                    doc.add(p);
                    
                    doc.close();
                    
                    
                    
                    //fusion des pages du recu
                   String newFileName = MergePages("facture.pdf");
                    
                    //impression de la facture
                   print(newFileName);
                   
                   }catch(Exception e){
                       //javax.swing.JOptionPane.showMessageDialog(null,"MyShop Info trouveéééé \n "+e);
                       e.printStackTrace();
                    TrayNotification notification = new TrayNotification();
                    notification.setAnimationType(AnimationType.POPUP);
                    notification.setTray("MyShop", "Vente Non Effectuée: "+e, NotificationType.ERROR);
                    notification.showAndDismiss(Duration.seconds(3));
                    e.printStackTrace();
                }
    }
      
    public static String insertDot(String mt){
        String nmt = "";
        int tle = mt.length();
        //System.out.println("tle: "+tle);
       // System.out.println("");
       if(tle >= 3){
           for(int i=tle-1;i>=0;i--){
               //System.out.println("i: "+i);
               nmt = mt.charAt(i)+nmt;
               if( (tle-i) %3 == 0 && i>0 ){
                   nmt = "."+nmt;
               }
           }
           
       }
       return nmt;
    }

}
