package fr.moff.gestionbanquaire.classes;

/**
 * Created by Julian on 18/01/2018.
 */

public class Compte {
    public static final String TAG = Compte.class.getSimpleName();
    public static final String TABLE = "Compte";
    // Labels Table Columns names
    public static final String KEY_CompteID = "CompteId";
    public static final String KEY_Num = "Num";
    public static final String KEY_Montant = "Montant";
    public static final String KEY_ClientID = "FkClientId";

    private int id;
    private String num;
    private double montant;
    private int clientId;
    private String libelle;

    public Compte(){}

    public Compte(String libelle){ this.libelle = libelle; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
