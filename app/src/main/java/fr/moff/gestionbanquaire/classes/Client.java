package fr.moff.gestionbanquaire.classes;

/**
 * Created by Julian on 18/01/2018.
 */

public class Client {
    public static final String TAG = Client.class.getSimpleName();
    public static final String TABLE = "Client";
    // Labels Table Columns names
    public static final String KEY_ClientID = "ClientId";
    public static final String KEY_Nom = "Nom";
    public static final String KEY_Prenom = "Prenom";
    public static final String KEY_Login = "Login";
    public static final String KEY_Mdp = "Mdp";


    private int id;
    private String nom;
    private String prenom;
    private String login;
    private String mdp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
