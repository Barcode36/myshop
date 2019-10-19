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
import entites.Client;
import entites.Compte;
import entites.ContenirVente;
import entites.HistoriqueVente;
import entites.Vente;
import entites.Produit;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import modele.ClientR;
import modele.ProduitR;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.IClientService;
import service.ICompteService;
import service.IContenirVente;
import service.IHistoriqueVente;
import service.IProduitService;
import service.IVenteService;
import service.imp.CompteService;
import service.imp.ContenirVenteService;
import service.imp.HistoriqueVenteService;
import service.imp.ProduitService;
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

    ObservableList<ProduitR> produitListVent = FXCollections.observableArrayList();

    IVenteService venteService = new VenteService();
    ICompteService compteService = new CompteService();
    IContenirVente contenirVenteService = new ContenirVenteService();
    IProduitService produitService = new ProduitService();
    IHistoriqueVente historiqueVenteService = new HistoriqueVenteService();

    Compte compteActif = new Compte();
    ClientR clientR = new ClientR();
    private boolean corr = true;
    @FXML
    private Label Client;

     private File file;
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
        
        try{
               
               
                 file = new File("Ressources/coeff.xls");
                 
                if (file != null) {
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        XSSFWorkbook wb = new XSSFWorkbook(fis);
                        XSSFSheet sheet = wb.getSheetAt(0);
                        Row row;
                        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                            row = sheet.getRow(i);
                            System.out.println(""+row.getCell(0).getStringCellValue());
                           // txtCoeff.setText(row.getCell(0).getStringCellValue());
                        }
                        wb.close();
                        fis.close();
                    } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainPrincipalController.class
                            .getName()).log(Level.SEVERE, null, ex);

                    } 
                }
                
            } catch (IOException e) { 
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

    }

    private IClientService clientService = MainViewController.clientService;
    
    @FXML
    private void Valider(ActionEvent event) {
         
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
                    contenirVente.setMontVente(Double.parseDouble(lblTot.getText()));
                    contenirVente.setDtVente(new Date());
                    
                    contenirVenteService.ajouterContenirVente(contenirVente);
                    
                    HistoriqueVente histoVente = new HistoriqueVente();
                    histoVente.setIdCon(contenirVente.getIdCon());
                    histoVente.setSmeRecue(Double.valueOf( txtMontCllt.getText()));
                    histoVente.setSmeRendue(Double.valueOf(txtRemise.getText()));
                    
                    historiqueVenteService.ajouterHistoriqueVente(histoVente);
                    
                    Produit p = new Produit(pr.getIdProd().getValue());
                    Produit produit = produitService.findById(p);
                    produit.setQteIniProd(produit.getQteIniProd() - pr.getQteProdCom().getValue());

                    produitService.modifier(produit);
                }
                CaissePaneController.vente = true;
                
                Rectangle pageSize =  new Rectangle(320, 380);
        
                Document doc = new Document(pageSize);

                try{
                    PdfWriter.getInstance(doc, new FileOutputStream("facture.pdf"));
                    doc.open();
                    //document.add(new Paragraph(excerptsFromDavidCopperfield[0], new Font(Font.TIMES_ROMAN)));
                    com.itextpdf.text.Font font=new com.itextpdf.text.Font(FontFamily.COURIER);
                    
                    Paragraph p = new Paragraph();
                    p.setFont(font);
                    p.setTabSettings(new TabSettings(100f));
                    p.add(Chunk.TABBING);
                    
                    //com.itextpdf.text.Font font = new com.itextpdf.text.Font("Courrier Niew", 12);
                    p.add(new Chunk("MYSHOP"));
                   
                    doc.add(p);

                    

                    p = new Paragraph();
                    p.setFont(font);
                    p.setTabSettings(new TabSettings(72f));
                    p.add(Chunk.TABBING);
                    p.add(new Chunk("**************"));
                    doc.add(p); 
                    
                    p = new Paragraph();
                     p.setFont(font);
                    p.setTabSettings(new TabSettings(82f));
                    p.add(Chunk.TABBING);
                    p.add(new Chunk("Vente N."+vente.getIdVen()));
                    p.setFont(font);
                    doc.add(p); 

                    p = new Paragraph();
                     p.setFont(font);
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
                    
                    p.add(new Chunk("==================================")) ;
                    p.add(new Chunk("Cais: "+recompCaiName)) ;
                    p.add(Chunk.TABBING);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = new Date(); 
                    df.format( new Date(date.getTime()));
                    p.add(new Chunk("Date: "+df.format( new Date(date.getTime()))+" \n"));
                    doc.add(p);

                    p = new Paragraph("");
                     p.setFont(font);
                    p.add(new Chunk("Client: "+clientR.getNomClt().getValue()+" ("+clientR.getNumClt().getValue()+")\n"));
                    p.add(new Chunk("=================================="));
                    doc.add(p);

                    PdfPTable table = new PdfPTable(4);
                    table.setWidths(new float []{2f, 1f, 1f,1.5f});
                     table.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.setWidthPercentage(100);
                    PdfPCell cell = new PdfPCell();
                    //cell.setColspan(5);
                    table.getDefaultCell().setBorder(cell.NO_BORDER);
                    
                    
                    table.addCell( new Phrase("Produits ",font));
                    table.addCell(new Phrase("PU ",font));
                    table.addCell(new Phrase("Qte ",font));
                    table.addCell(new Phrase("Total ",font));
                    
                    ObservableList<ProduitR> pdt =  produitCaisseTable.getItems();
                    //System.out.println("length: "+pdt.size());
                    int tot = 0;
                    for( ProduitR prod : pdt ){
                        
                        String prodName = prod.getLibProd().getValue()+"";
                      
                        String tabNameSplitted[] = prodName.split(" ");
                        String recomposedName = "";
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
                        //p.add(new Chunk(""+recomposedName));
                        table.addCell( new Phrase(""+recomposedName,font));
                        table.addCell( new Phrase(""+prod.getPrixUniProd().getValue(),font));
                        table.addCell(new Phrase(""+prod.getQteProdCom().getValue(),font));
                        table.addCell(new Phrase(""+prod.getTotal().getValue(),font));
                        
                        tot+= Integer.parseInt(prod.getTotal().getValue()) ;
                      
                    }
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    
                    table.addCell(new Phrase("Reg: Espèce",font));
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(new Phrase(tot+" F",font) );
                    
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    table.addCell(" ");
                    
                    table.addCell(new Phrase("Reçu: "+txtMontCllt.getText(),font) );
                    table.addCell(" ");
                    table.addCell(new Phrase("Rend: ",font) );
                    table.addCell(new Phrase(""+txtRemise.getText(),font));
                    doc.add(table);
                    
                    p = new Paragraph();
                    p.setFont(font);
                    p.add(new Chunk("=================================="));
                    //p.setTabSettings(new TabSettings(20f));
                   // p.add(Chunk.TABBING);
                    p.add(new Chunk(" Merci de votre visite et à Bientôt "));
                    p.add(new Chunk("=================================="));
                    
                    doc.add(p);
                    
                    //impression de la facture
                    if (Desktop.isDesktopSupported()){  
                        if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.PRINT)){  
                            try {  
                                        java.awt.Desktop.getDesktop().print(new File("facture.pdf"));  
                                } catch (IOException ex) {  
                                    System.out.println("ex "+ex);
                                        //Traitement de l'exception  
                                }  
                        } else {  
                              System.out.println("La fonction n'est pas supportée par votre système d'exploitation");  //  
                        }  
                    } else {  
                        System.out.println("Desktop pas supporté par votre système d'exploitation ");
                            // 
                    }


                }catch(Exception e){
                    System.out.println(""+e);
                   // e.printStackTrace();
                }
                doc.close();

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
                
                
                if( Double.parseDouble( txtMontCllt.getText()) > Double.parseDouble(lblTot.getText()) ){
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

}
