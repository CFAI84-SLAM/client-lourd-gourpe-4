package clientlourd;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class MainGuiController implements Initializable {


    @FXML
    private TextField tfId;
    @FXML
    private TextField tfPhoto;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrixHT;
    @FXML
    private TextField tfCategorie;
    @FXML
    private TableView<Produits> tvProduits;
    @FXML
    private TableColumn<Produits, Integer> colId;
    @FXML
    private TableColumn<Produits, String> colPhoto;
    @FXML
    private TableColumn<Produits, String> colNom;
    @FXML
    private TableColumn<Produits, Double> colPrixHT;
    @FXML
    private TableColumn<Produits, String> colCategorie;
    @FXML
    private Button btnInserer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnMenu;
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
        showProduits();
    }    

   

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnInserer) {
            insertRecord();
        } else if (event.getSource() == btnModifier) {
            updateRecord();
        } else if (event.getSource() == btnSupprimer) {
            deleteRecord();
        } 
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
    
    
    public ObservableList<Produits> getProduitsList() {
        ObservableList<Produits> produitsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT p.Id_Produit, p.nom, p.prixHT, p.photo, c.type  FROM Produit p INNER JOIN Categorie c ON p.Id_Categorie=c.Id_Categorie";
                
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Produits produits;
            while (rs.next()) {
                produits = new Produits(rs.getInt("Id_Produit"), rs.getString("photo"), rs.getString("nom"), rs.getDouble("prixHT"), rs.getString("type"));
                produitsList.add(produits);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return produitsList;
    }
    
    
    public void showProduits() {
        ObservableList<Produits> list = getProduitsList();
        
        colId.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("id"));  
        colPhoto.setCellValueFactory(new PropertyValueFactory<Produits, String>("photo"));        
        colNom.setCellValueFactory(new PropertyValueFactory<Produits, String>("nom"));     
        colPrixHT.setCellValueFactory(new PropertyValueFactory<Produits, Double>("prixHT"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<Produits, String>("categorie"));
        
        tvProduits.setItems(list);
    }
    
    
    
    
    private void insertRecord() {
        int cat = 0;
       if (null != tfCategorie.getText()) switch (tfCategorie.getText()) {
            case "entrée":{
                 cat = 1;
                    break;
                }
            case "plat":{
                 cat = 2;
                    break;
                }
            case "dessert":{
                cat = 3;
                    break;
                }
            default:
                break;
        }
        
       if (tfId.getText() != null && tfPhoto.getText() != null && tfNom.getText() != null && tfPrixHT.getText() != null && tfCategorie.getText() != null) {
           
           String query = "INSERT INTO Produit VALUES(" + tfId.getText() + ",'" + tfNom.getText() + "'," + tfPrixHT.getText() + ",20,'" + tfPhoto.getText() + "'," + cat +")";
           
           executeQuery(query);
           showProduits();
       }                   
    }
    
    
    private void updateRecord() {
        int cat = 0;
       if (null != tfCategorie.getText()) switch (tfCategorie.getText()) {
            case "entrée":{
                 cat = 1;
                    break;
                }
            case "plat":{
                 cat = 2;
                    break;
                }
            case "dessert":{
                cat = 3;
                    break;
                }
            default:
                break;
        }
       
        String query = "UPDATE Produit SET nom = '" + tfNom.getText() + "', prixHT = " + tfPrixHT.getText() + ", photo = '" + tfPhoto.getText() + "', Id_Categorie = " + cat +" WHERE Id_Produit = " + tfId.getText() +"";
        executeQuery(query);
        showProduits();
    }
    
    private void deleteRecord() {
        String query = "DELETE from Produit WHERE Id_Produit = " + tfId.getText() +"";
        
        executeQuery(query);
        showProduits();
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
    private void handleMouseAction(MouseEvent event) {
       Produits produit = tvProduits.getSelectionModel().getSelectedItem();
       
       if (produit != null) {
            tfId.setText("" +produit.getId());
            tfNom.setText(produit.getNom());
            tfPrixHT.setText("" +produit.getPrixHT());
            tfPhoto.setText(produit.getPhoto());
            tfCategorie.setText(produit.getCategorie());
       }
       
    }
    
    public void switchToMenu(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }  
    
    
    public void disconnect(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("authentification.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }  
}
