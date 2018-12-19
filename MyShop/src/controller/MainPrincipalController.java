/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class MainPrincipalController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private JFXButton btnInventaire;
    @FXML
    private GridPane gridInventaire;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private TextField txtCode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // TODO
            HBox root = FXMLLoader.load(getClass().getResource("/views/clavier.fxml"));
            drawer.setSidePane(root);
            drawer.toFront();
            for (Node node : root.getChildren()) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        txtCode.setText(txtCode.getText() + node.getAccessibleText());
                    });
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MainPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void selectForm(ActionEvent event) {
        if (event.getSource() == btnInventaire) {
            gridInventaire.toFront();
            drawer.toFront();
            gridInventaire.setVisible(true);
            grid.toBack();
            
        }
    }

    @FXML
    private void closeInventaire(MouseEvent event) {
        grid.toFront();
        gridInventaire.toBack();
        gridInventaire.setVisible(false);
    }

    @FXML
    private void clavier(MouseEvent event) {
        if (drawer.isShown()) {
            drawer.close();
        } else {
            drawer.open();
        }
    }

    @FXML
    private void clavierSor(MouseEvent event) {
        //  drawer.open();
    }

}
