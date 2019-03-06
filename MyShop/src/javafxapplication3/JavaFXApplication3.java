/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import Utils.Constants;
import controller.MainViewController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import sun.plugin2.ipc.windows.WindowsEvent;

/**
 *
 * @author Christ
 */
public class JavaFXApplication3 extends Application {

//    private double xOffset = 0;
//    private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) {
        try {
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
// scene.getStylesheets().add(getClass().getResource("/css/MainPrincipalCss.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(JavaFXApplication3.class.getResourceAsStream("/img/icon.jpg")));
            primaryStage.setTitle("MyShop");
            primaryStage.setMaximized(true);
            primaryStage.show();
           
            primaryStage.setOnHiding(((event) -> {
                System.exit(0);
            }));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
