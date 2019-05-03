/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Utils.Constants;
import com.jfoenix.controls.JFXProgressBar;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafxapplication3.JavaFXApplication3;
import javax.swing.JOptionPane;
import service.ITypeService;
import service.imp.TypeService;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class EssssssController implements Initializable {

    @FXML
    private ProgressBar progres;

    /**
     * Initializes the controller class.
     */
    private Task taskWorker(int seconde) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < seconde; i++) {
                    updateProgress(i + 1, seconde);
                    Thread.sleep(100);
                }
                return null;
            }

        };
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Task task = taskWorker(100);
        //progres.progressProperty().unbind();
        progres.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(e -> {
            try {
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource(Constants.MainView));
//        root.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                xOffset = event.getSceneX();
//                yOffset = event.getSceneY();
//            }
//
//        });
//
//        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {//
//                primaryStage.setX(event.getScreenX() - xOffset);
//                primaryStage.setY(event.getScreenY() - yOffset);
//            }
//
//        });
                Scene scene = new Scene(root);
              //  scene.getStylesheets().add(getClass().getResource("/css/MainPrincipalCss.css").toExternalForm());
                primaryStage.setScene(scene);
              //  primaryStage.getIcons().add(new Image(JavaFXApplication3.class.getResourceAsStream("/img/afnacos.ico")));
                primaryStage.setTitle("MyShop");
                primaryStage.setMaximized(true);
                primaryStage.show();

                primaryStage.setOnHiding(((event) -> {
                    System.exit(0);
                }));
                Stage st = (Stage) progres.getScene().getWindow();
                st.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });
        Thread th = new Thread(task);
        th.start();

    }

}
