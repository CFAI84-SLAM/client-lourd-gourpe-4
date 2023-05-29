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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author olivi
 */
public class AuthentificationController extends Password implements Initializable {

    
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfMdp;
    @FXML
    private Label conLab;

  
    
    public void switchToMenu(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }    
    
    public void switchToCommandes(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("commandesGui.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conLab.setText("");
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
    
    public ObservableList<Staff> getStaff() {
        ObservableList<Staff> staffList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        
        String query = "SELECT Id_Utilisateur, email, password, roles FROM Utilisateur WHERE roles LIKE '["+ "ROLE_ADMIN" + "]' OR roles LIKE '["+ "ROLE_PREP" + "]' ";
                
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Staff staff;
            while (rs.next()) {
                staff = new Staff(rs.getInt("Id_Utilisateur"), rs.getString("email"), rs.getString("password"), rs.getString("roles"));
                staffList.add(staff);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return staffList;
    }
    
    
    @FXML
    public void connect(ActionEvent event) throws Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = null;
        PreparedStatement st = null;
        String username = tfEmail.getText();
        String password = tfMdp.getText() ;
        
    
      
        String sql = "SELECT roles FROM Utilisateur WHERE email= ? ";
        String query = "SELECT password FROM Utilisateur WHERE email= ? AND roles=  '[\"ROLE_ADMIN\"]'  OR roles= '[\"ROLE_PREP\"]'";
         try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String hash = rs.getString("password");
                
                 if (checkPassword(password, hash)) {
                     try {
                         st = conn.prepareStatement(sql);
                        st.setString(1, username);
                        ResultSet r = st.executeQuery();
                        if(r.next()) {
                            
                             if("[\"ROLE_ADMIN\"]".equals(r.getString("roles"))) {
                                switchToMenu(event);
                             } else {
                                 switchToCommandes(event);
                             }
                        }
                        
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     
                     
                     
                     
                 } else {
                     conLab.setText("Identifiants invalides");
                 }
                 
            }
            
         } catch(Exception e) {
             e.printStackTrace();
             
         }
        
    }
    
}
