/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clientlourd;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author olivi
 */
public class ClientsGuiController implements Initializable {

    @FXML
    private TableView<Clients> tvClients;
    @FXML
    private TableColumn<Clients, Integer> colId;
    @FXML
    private TableColumn<Clients, String> colNom;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnDeco;
    @FXML
    private TableColumn<Clients, String> colPrenom;
    @FXML
    private TableColumn<Clients, Integer> colTel;
    @FXML
    private TableColumn<Clients, String> colEmail;
    @FXML
    private TableColumn<Clients, String> colAdresse;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableColumn<Clients, String> colDate;
    @FXML
    private Button btnHist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showClients();
    }    

    @FXML
    private void handleMouseAction(MouseEvent event) {
    }

    

    @FXML
    public void switchToMenu(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
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
    
    
    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://192.168.3.30:3306/slam4", "slam4", "LoKb5nDr!B");
            return conn;
        }catch(Exception e) {
            System.out.println("Error " + e.getMessage());
            return null;
        }
    }
    
    
    public ObservableList<Clients> getClientsList() {
        ObservableList<Clients> clientsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT Id_Utilisateur, nom, prenom, tel, email, adresse, dateNaissance  FROM Utilisateur";
                
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Clients clients;
            while (rs.next()) {
                clients = new Clients(rs.getInt("Id_Utilisateur"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("tel"), rs.getString("email"),  rs.getString("adresse"), rs.getString("dateNaissance"));
                clientsList.add(clients);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return clientsList;
    }
    
    
    public void showClients() {
        ObservableList<Clients> list = getClientsList();
        
        colId.setCellValueFactory(new PropertyValueFactory<Clients, Integer>("id"));  
        colNom.setCellValueFactory(new PropertyValueFactory<Clients, String>("nom"));        
        colPrenom.setCellValueFactory(new PropertyValueFactory<Clients, String>("prenom"));     
        colTel.setCellValueFactory(new PropertyValueFactory<Clients, Integer>("tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Clients, String>("email"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<Clients, String>("adresse"));
        colDate.setCellValueFactory(new PropertyValueFactory<Clients, String>("date"));
        
        tvClients.setItems(list);
    }
    
    
     @FXML
    private void commandesHistorique(ActionEvent event) throws Exception {
        Clients client = tvClients.getSelectionModel().getSelectedItem();
        
        if (client != null) {
            // root = FXMLLoader.load(getClass().getResource("commandesHist.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("commandesHist.fxml"));
            root = loader.load();


            CommandesHistController commandesHist = loader.getController();

            commandesHist.userId = client.getId();
            commandesHist.showCommandes();

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
     
        }
       
    }

 
  
}
