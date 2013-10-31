package sample;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Thib'
 * Date: 30/10/13
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public class Champ {
    private String type;
    private String nom;
    private static ArrayList<Champ> listValeur;

    public Champ() {
        listValeur = new ArrayList<Champ>();
    }

    public Champ(String nom, String type) {
        this.nom = nom;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static ArrayList<Champ> getValeurTable() {
        return listValeur;
    }
    public void setValeurTable(ArrayList<Champ> list){
        listValeur = list;
    }
}
