/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientlourd;

/**
 *
 * @author olivi
 */
public class Produits {
    private int id;
    private String photo;
    private String nom;
    private double prixHT;
    private String categorie;

    public Produits(int id, String photo, String nom, double prixHT, String categorie) {
        this.id = id;
        this.photo = photo;
        this.nom = nom;
        this.prixHT = prixHT;
        this.categorie = categorie;
    }

    
    
    public int getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getNom() {
        return nom;
    }

    public double getPrixHT() {
        return prixHT;
    }

    public String getCategorie() {
        return categorie;
    }
    
    
    
}
