/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

 
import Utils.Constants;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.stage.StageStyle;

/**
 *
 * @author Christ
 */
public class JavaFXApplication3 extends Application {
//    private double xOffset = 0;
//    private double yOffset = 0;

    private static final int SINGLE_INSTANCE_LISTENER_PORT = 9999;
    private static final String SINGLE_INSTANCE_FOCUS_MESSAGE = "focus";

    private static final String instanceId = UUID.randomUUID().toString();

    // We define a pause before focusing on an existing instance
    // because sometimes the command line or window launching the instance
    // might take focus back after the second instance execution complete
    // so we introduce a slight delay before focusing on the original window
    // so that the original window can retain focus.
    private static final int FOCUS_REQUEST_PAUSE_MILLIS = 500;

    private Stage stage;

    public void init() {
        CountDownLatch instanceCheckLatch = new CountDownLatch(1);

        Thread instanceListener = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(SINGLE_INSTANCE_LISTENER_PORT, 10)) {
                instanceCheckLatch.countDown();

                while (true) {
                    try (
                            Socket clientSocket = serverSocket.accept();
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(clientSocket.getInputStream()))) {
                        String input = in.readLine();
                        System.out.println("Received single instance listener message: " + input);
                        if (input.startsWith(SINGLE_INSTANCE_FOCUS_MESSAGE) && stage != null) {
                            Thread.sleep(FOCUS_REQUEST_PAUSE_MILLIS);
                            Platform.runLater(() -> {
                                System.out.println("To front " + instanceId);
                                stage.setIconified(false);
                                stage.show();
                                stage.toFront();
                            });
                        }
                    } catch (IOException e) {
                        System.out.println("Single instance listener unable to process focus message from client");
                        e.printStackTrace();
                    }
                }
            } catch (java.net.BindException b) {
                System.out.println("SingleInstanceApp already running");

                try (
                        Socket clientSocket = new Socket(InetAddress.getLocalHost(), SINGLE_INSTANCE_LISTENER_PORT);
                        PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
                    System.out.println("Requesting existing app to focus");
                    out.println(SINGLE_INSTANCE_FOCUS_MESSAGE + " requested by " + instanceId);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Aborting execution for instance " + instanceId);
                Platform.exit();
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                instanceCheckLatch.countDown();
            }
        }, "instance-listener");
        instanceListener.setDaemon(true);
        instanceListener.start();

        try {
            instanceCheckLatch.await();
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    public void stop() {
        System.out.println("Exiting instance " + instanceId);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
           
        
            Parent root = FXMLLoader.load(getClass().getResource("/views/essssss.fxml"));
//        
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(JavaFXApplication3.class.getResourceAsStream("/img/afnacos.ico")));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
            primaryStage.setTitle("MyShop");
            primaryStage.show();
            //primaryStage.setMaximized(true);
            //System.out.println("suis-je passé? oui ou non?");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       launch(args);
        System.out.println(" mainnn ");
    }
    
    

}
