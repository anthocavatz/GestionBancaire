package fr.moff.gestionbanquaire.Ado;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fr.moff.gestionbanquaire.classes.Client;
import fr.moff.gestionbanquaire.classes.Compte;
import fr.moff.gestionbanquaire.tools.DatabaseManager;

/**
 * Created by Julian on 18/01/2018.
 */

public class CompteADO {
    private Compte compte;

    public CompteADO(){
        compte = new Compte();
    }

    public static String createTable(){
        return "CREATE TABLE " + Compte.TABLE  + "("
                + Compte.KEY_CompteID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Compte.KEY_Num + " TEXT, "
                + Compte.KEY_Montant + " REAL, "
                + Compte.KEY_ClientID   + " INTEGER )";
    }
    public int insert(Compte compte) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Compte.KEY_Num, compte.getNum());
        values.put(Compte.KEY_Montant, compte.getMontant());
        values.put(Compte.KEY_ClientID, compte.getClientId());
        // Inserting Row
        long id = db.insert(compte.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return (int)id;
    }

    public void update(Compte compte) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Compte.KEY_Num, compte.getNum());
        values.put(Compte.KEY_Montant, compte.getMontant());
        values.put(Compte.KEY_ClientID, compte.getClientId());
        // Inserting Row
        db.update(compte.TABLE, values, Compte.KEY_CompteID+" = "+ compte.getId(), null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Compte.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public ArrayList<Compte> getAllComptes() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Compte.TABLE, new String[] {}, null, null, null, null, Compte.KEY_CompteID);
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        ArrayList<Compte> compteList = new
                ArrayList<Compte> ();
        while (c.moveToNext()) {
            Compte compte = new Compte();
            compte.setId(c.getInt(0));
            compte.setNum(c.getString(1));
            compte.setMontant(c.getDouble(2));
            compte.setClientId(c.getInt(3));
            compteList.add(compte);
        }
        c.close();
        return compteList;
    }
    public ArrayList<Compte> getClientComptes(Client client) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Compte.TABLE, new String[] {}, Compte.KEY_ClientID + " LIKE \"" + client.getId() + "\"",
                null, null, null, Compte.KEY_ClientID);
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        ArrayList<Compte> compteList = new
                ArrayList<Compte> ();
        while (c.moveToNext()) {
            Compte compte = new Compte();
            compte.setId(c.getInt(0));
            compte.setNum(c.getString(1));
            compte.setMontant(c.getDouble(2));
            compte.setClientId(c.getInt(3));
            compteList.add(compte);
        }
        c.close();
        return compteList;
    }

    public Compte getOneCompte(String name) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Compte.TABLE, new String[]{Compte.KEY_CompteID,Compte.KEY_Num,Compte.KEY_Montant, Compte.KEY_ClientID}, Compte.KEY_ClientID + " LIKE \""+ name +"\"",null,null,null, Compte.KEY_Num );
        Compte compte = new Compte();
        while (c.moveToNext()) {
            compte.setId(c.getInt(0));
            compte.setNum(c.getString(1));
            compte.setMontant(c.getDouble(2));
            compte.setClientId(c.getInt(3));


            Log.e("Liste compte", compte.getNum());
        }
        //return cursorToClient(c);
        return compte;
    }
}
