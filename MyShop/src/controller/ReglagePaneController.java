/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entites.Produit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.SwingUtilities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import service.IProduitService;
import service.imp.CompteService;
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
public class ReglagePaneController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    private FileChooser fileChooser;
    private File file;
    private Stage stage;

    IProduitService produitService = MainViewController.produitService;
    @FXML
    private TitledPane cont;

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
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx"));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s = (Stage) anchorPane.getScene().getWindow();
                if (s.isMaximized()) {
                    MainViewController.temporaryPaneTot.setPrefWidth(s.getWidth());
                    System.out.println(s.getWidth());
                }
                anchorPane.setPrefWidth(MainViewController.temporaryPaneTot.getWidth());
                cont.setPrefWidth(MainViewController.temporaryPaneTot.getWidth() - 487);
            }
        });
        MainViewController.temporaryPaneTot.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                anchorPane.setPrefWidth(newValue.doubleValue());
                cont.setPrefWidth(newValue.doubleValue() - 159);
            }

        });
    }

    @FXML
    private void importInventaire(ActionEvent event) {
        stage = (Stage) anchorPane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        Produit produit = new Produit();
        if (file != null) {
            try {
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                XSSFSheet sheet = wb.getSheetAt(0);
                Row row;
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    Produit p = new Produit();
                    p.setCodeProd(row.getCell(0).getStringCellValue());
                    p.setLibProd(row.getCell(1).getStringCellValue());
                    p.setPrixUniProd(row.getCell(2).getStringCellValue());
                    p.setQteIniProd((int) row.getCell(3).getNumericCellValue());
                    p.setEtatProd("actif");
                    try {
                        produit = produitService.findByCode(p);
                        produit.setQteIniProd(p.getQteIniProd());
                        produitService.modifier(produit);
                    } catch (Exception e) {
                        produitService.ajouter(p);
                        //e.printStackTrace();
                    }
                }

                wb.close();
                fis.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainPrincipalController.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(MainPrincipalController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            TrayNotification notification = new TrayNotification();
            notification.setAnimationType(AnimationType.POPUP);
            notification.setTray("MyShop", "Importation terminée", NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.seconds(1.5));
        }
    }

    public List<Produit> listProduit() {
        return produitService.produitList();
    }

    @FXML
    private void exportInventaire(ActionEvent event) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Inventaire");
        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Code Produit");
        header.createCell(1).setCellValue("Produit");
        header.createCell(2).setCellValue("Prix Unitaire");
        header.createCell(3).setCellValue("Quantité");
        sheet.autoSizeColumn(0);
        sheet.setColumnWidth(0, 256 * 25);
        sheet.setColumnWidth(1, 256 * 25);
        sheet.setColumnWidth(2, 256 * 25);
        sheet.setColumnWidth(3, 256 * 25);

        int index = 1;
        for (Produit produit : listProduit()) {
            XSSFRow row = sheet.createRow(index);
            row.createCell(0).setCellValue(produit.getCodeProd());
            row.createCell(1).setCellValue(produit.getLibProd());
            row.createCell(2).setCellValue(produit.getPrixUniProd());
            row.createCell(3).setCellValue(produit.getQteIniProd());
            index++;
        }
        try {
            stage = (Stage) anchorPane.getScene().getWindow();
            file = fileChooser.showSaveDialog(stage);
            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath() + ".xlsx");
            wb.write(fos);
            fos.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        TrayNotification notification = new TrayNotification();
        notification.setAnimationType(AnimationType.POPUP);
        notification.setTray("MyShop", "Exportation terminée", NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.seconds(1.5));
    }

    @FXML
    private void catalogue(ActionEvent event) {
        try {
            stage = (Stage) anchorPane.getScene().getWindow();
            file = fileChooser.showSaveDialog(stage);
            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath() + ".xlsx");
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Inventaire");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("Code Produit");
            header.createCell(1).setCellValue("Produit");
            header.createCell(2).setCellValue("Prix Unitaire");
            header.createCell(3).setCellValue("Quantité");
            header.createCell(3).setCellValue("Code barre");
            sheet.autoSizeColumn(0);
            sheet.setColumnWidth(0, 256 * 25);
            sheet.setColumnWidth(1, 256 * 25);
            sheet.setColumnWidth(2, 256 * 25);
            sheet.setColumnWidth(3, 256 * 25);
            sheet.setColumnWidth(4, 256 * 25);

            int index = 1;
            int i = 1;
            for (Produit produit : listProduit()) {
                try {
                    XSSFRow row = sheet.createRow(index);

                    Code128Bean code128 = new Code128Bean();
                    code128.setHeight(15f);
                    code128.setModuleWidth(0.3);
                    code128.setQuietZone(10);
                    code128.doQuietZone(true);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 400, BufferedImage.TYPE_BYTE_BINARY, false, 0);
                    code128.generateBarcode(canvas, produit.getCodeProd());
                    canvas.finish();

                    try (//write to png file
                            FileOutputStream foss = new FileOutputStream("barcode.png")) {
                        foss.write(baos.toByteArray());
                        foss.flush();
                    }
                    row.createCell(0).setCellValue(produit.getCodeProd());
                    row.createCell(1).setCellValue(produit.getLibProd());
                    row.createCell(2).setCellValue(produit.getPrixUniProd());
                    //row.createCell(3).setCellValue(produit.getQteIniProd());
                    InputStream inputStream = new FileInputStream("barcode.png");
                    //Get the contents of an InputStream as a byte[].
                    byte[] bytes = IOUtils.toByteArray(inputStream);
                    //Adds a picture to the workbook
                    int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                    //close the input stream
                    inputStream.close();
                    //Returns an object that handles instantiating concrete classes
                    CreationHelper helper = wb.getCreationHelper();
                    //Creates the top-level drawing patriarch.
                    Drawing drawing = sheet.createDrawingPatriarch();

                    //Create an anchor that is attached to the worksheet
                    ClientAnchor anchor = helper.createClientAnchor();

                    //create an anchor with upper left cell _and_ bottom right cell
                    anchor.setCol1(3); //Column B
                    anchor.setRow1(i); //Row 3
                    anchor.setCol2(4); //Column C
                    anchor.setRow2(i + 1); //Row 4

                    //Creates a picture
                    Picture pict = drawing.createPicture(anchor, pictureIdx);

                    //Reset the image to the original size
                    //pict.resize(); //don't do that. Let the anchor resize the image!
                    //Create the Cell B3
                    index++;
                    i++;
                } catch (IOException ex) {
                    Logger.getLogger(ReglagePaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            wb.write(fos);
            fos.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        TrayNotification notification = new TrayNotification();
        notification.setAnimationType(AnimationType.POPUP);
        notification.setTray("MyShop", "Exportation terminée", NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.seconds(1.5));

    }

}
