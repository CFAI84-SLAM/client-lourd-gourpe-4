/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clientlourd;

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
public class CommandesGuiController implements Initializable {

    @FXML
    private TableView<Commandes> tvCommandes;
    @FXML
    private TableColumn<Commandes, Integer> colId;
    @FXML
    private TableColumn<Commandes, String> colEtat;
    @FXML
    private TableColumn<Commandes, String> colDate;
    @FXML
    private TableColumn<Commandes, String> colUtilisateur;
    @FXML
    private Button btnValider;
    @FXML
    private TableView<Commandes> tvCommandesValidees;
    @FXML
    private TableColumn<Commandes, Integer> colIdv;
    @FXML
    private TableColumn<Commandes, String> colEtatv;
    @FXML
    private TableColumn<Commandes, String> colDatev;
    @FXML
    private TableColumn<Commandes, String> colUtilisateurv;
    @FXML
    private Button btnDeco;
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showCommandes();
        showCommandesValidees();
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
    
    
    public ObservableList<Commandes> getCommandesList() {
        ObservableList<Commandes> commandesList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT c.Id_Commandes, c.etat, c.date_de_commande, c.Id_Utilisateur, u.email  FROM Commandes c INNER JOIN Utilisateur u ON c.Id_Utilisateur=u.Id_Utilisateur WHERE c.etat='en préparation'";
                
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Commandes commandes;
            while (rs.next()) {
                commandes = new Commandes(rs.getInt("Id_Commandes"), rs.getString("etat"), rs.getString("date_de_commande"), rs.getInt("Id_Utilisateur"),rs.getString("email"));
                commandesList.add(commandes);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return commandesList;
    }
    
    public ObservableList<Commandes> getCommandesVList() {
        ObservableList<Commandes> commandesVList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT c.Id_Commandes, c.etat, c.date_de_commande, c.Id_Utilisateur, u.email  FROM Commandes c INNER JOIN Utilisateur u ON c.Id_Utilisateur=u.Id_Utilisateur WHERE c.etat='validée'";
                
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Commandes commandes;
            while (rs.next()) {
                commandes = new Commandes(rs.getInt("Id_Commandes"), rs.getString("etat"), rs.getString("date_de_commande"),rs.getInt("Id_Utilisateur"), rs.getString("email"));
                commandesVList.add(commandes);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return commandesVList;
    }
    
    
    public void showCommandes() {
        ObservableList<Commandes> list = getCommandesList();
        
        colId.setCellValueFactory(new PropertyValueFactory<Commandes, Integer>("id"));  
        colEtat.setCellValueFactory(new PropertyValueFactory<Commandes, String>("etat"));        
        colDate.setCellValueFactory(new PropertyValueFactory<Commandes, String>("date"));     
        colUtilisateur.setCellValueFactory(new PropertyValueFactory<Commandes, String>("utilisateur"));
    
        
        tvCommandes.setItems(list);
    }
    
    public void showCommandesValidees() {
        ObservableList<Commandes> list = getCommandesVList();
        
        colIdv.setCellValueFactory(new PropertyValueFactory<Commandes, Integer>("id"));  
        colEtatv.setCellValueFactory(new PropertyValueFactory<Commandes, String>("etat"));        
        colDatev.setCellValueFactory(new PropertyValueFactory<Commandes, String>("date"));     
        colUtilisateurv.setCellValueFactory(new PropertyValueFactory<Commandes, String>("utilisateur"));
    
        
        tvCommandesValidees.setItems(list);
    }
    
    @FXML
    private void handleMouseAction(MouseEvent event) {
    }
    
    @FXML
    private void updateCommande() throws Exception {
        Commandes commande = tvCommandes.getSelectionModel().getSelectedItem();
        
        if(commande != null) {
            Connection conn = getConnection();
            String query = "UPDATE Commandes SET etat = 'validée' WHERE Id_Commandes =" + commande.getId() + "";

            executeQuery(query);
            showCommandes();
            showCommandesValidees();
        }
        
    }
    
    
    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
            
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void disconnect(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("authentification.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }  
}
