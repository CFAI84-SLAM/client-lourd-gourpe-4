/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientlourd;

/**
 *
 * @author olivi
 */
public class Commandes {
    private int id;
    private String etat;
    private String date;
    private String utilisateur;

    public Commandes(int id, String etat, String date, String utilisateur) {
        this.id = id;
        this.etat = etat;
        this.date = date;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public String getEtat() {
        return etat;
    }

    public String getDate() {
        return date;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    
}
