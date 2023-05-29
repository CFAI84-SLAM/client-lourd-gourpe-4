/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clientlourd;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author olivi
 */
public class AdminMenuController implements Initializable {

    @FXML
    private Button btnProduits;
    @FXML
    private Button btnClients;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button btnDeco;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    
    @FXML
    public void switchToProduits(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("mainGui.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }  

    @FXML
    public void disconnect(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("authentification.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }  
    
    @FXML
    public void switchToClients(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("clientsGui.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }  
}
