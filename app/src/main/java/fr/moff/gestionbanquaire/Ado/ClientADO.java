package fr.moff.gestionbanquaire.Ado;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fr.moff.gestionbanquaire.classes.Client;
import fr.moff.gestionbanquaire.tools.DatabaseManager;

/**
 * Created by Julian on 18/01/2018.
 */

public class ClientADO {
    private Client client;
    public ClientADO(){
        client = new Client();
    }

    public static String createTable(){
        return "CREATE TABLE " + Client.TABLE  + "("
                + Client.KEY_ClientID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Client.KEY_Nom + " TEXT, "
                + Client.KEY_Prenom + " TEXT, "
                + Client.KEY_Login + " TEXT, "
                + Client.KEY_Mdp  + " TEXT )";
    }
    public int insert(Client client) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Client.KEY_Nom, client.getNom());
        values.put(Client.KEY_Prenom, client.getPrenom());
        values.put(Client.KEY_Login, client.getLogin());
        values.put(Client.KEY_Mdp, client.getMdp());
        // Inserting Row
        long id = db.insert(client.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return (int)id;
    }
    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Client.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public ArrayList<Client> getAllClients() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Client.TABLE, new String[] {}, null, null, null, null, Client.KEY_ClientID);
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        ArrayList<Client> clientList = new
                ArrayList<Client> ();
        while (c.moveToNext()) {
            Client client = new Client();
            client.setId(c.getInt(0));
            client.setPrenom((c.getString(1)));
            client.setNom((c.getString(2)));
            client.setLogin((c.getString(3)));
            client.setMdp((c.getString(4)));
            clientList.add(client);
        }
        c.close();
        return clientList;
    }
    public Client getOneClient(String name) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Client.TABLE, new String[]{Client.KEY_ClientID,Client.KEY_Nom,Client.KEY_Prenom, Client.KEY_Login,Client.KEY_Mdp }, Client.KEY_Login + " LIKE \""+ name +"\" ",null,null,null, Client.KEY_ClientID );
        Client client = new Client();
        while (c.moveToNext()) {
            client.setId(c.getInt(0));
            client.setNom(c.getString(1));
            client.setPrenom(c.getString(2));
            client.setLogin(c.getString(3));
            client.setMdp(c.getString(4));

        }
        //return cursorToClient(c);
        return client;
    }
}
