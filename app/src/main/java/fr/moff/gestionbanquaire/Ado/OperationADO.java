package fr.moff.gestionbanquaire.Ado;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fr.moff.gestionbanquaire.classes.Compte;
import fr.moff.gestionbanquaire.classes.Operation;
import fr.moff.gestionbanquaire.tools.DatabaseManager;

/**
 * Created by Julian on 18/01/2018.
 */

public class OperationADO {
    private Operation operation;
    public OperationADO(){
        operation  = new Operation();
    }

    public static String createTable(){
        return "CREATE TABLE " + Operation.TABLE  + "("
                + Operation.KEY_OperationID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Operation.KEY_Libelle + " TEXT, "
                + Operation.KEY_Montant + " REAL, "
                + Operation.KEY_CompteID + " INTEGER )";
    }

    public void insert(Operation operation) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Operation.KEY_Libelle, operation.getLibelle());
        values.put(Operation.KEY_Montant, operation.getMontant());
        values.put(Operation.KEY_CompteID, operation.getCompteId());
        // Inserting Row
        db.insert(operation.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void update(Operation operation) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Operation.KEY_Libelle, operation.getLibelle());
        values.put(Operation.KEY_Montant, operation.getMontant());
        values.put(Operation.KEY_CompteID, operation.getCompteId());
        // Inserting Row
        db.update(operation.TABLE, values, Operation.KEY_OperationID+" = "+ operation.getId(), null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Operation.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void deleteOne(int id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Operation.TABLE, Operation.KEY_OperationID + " = " + id ,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public ArrayList<Operation> getAllOperation() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Operation.TABLE, new String[] {}, null, null, null, null, Operation.KEY_OperationID);
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        ArrayList<Operation> operationList = new
                ArrayList<Operation> ();
        while (c.moveToNext()) {
            Operation operation = new Operation();
            operation.setId(c.getInt(0));
            operation.setLibelle(c.getString(1));
            operation.setMontant(c.getDouble(2));
            operation.setCompteId(c.getInt(3));
            operationList.add(operation);
        }
        c.close();
        return operationList;
    }


    public ArrayList<Operation> getClientComptes(Compte compte) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Operation.TABLE, new String[] {}, Operation.KEY_CompteID + " = \"" + compte.getId() + "\"",
                null, null, null, Operation.KEY_CompteID);
        if (c.getCount() == 0) {
            c.close();
            return null;
        }

        ArrayList<Operation> compteList = new ArrayList<Operation> ();
        while (c.moveToNext()) {
            Operation operation = new Operation();
            operation.setId(c.getInt(0));
            operation.setLibelle(c.getString(1));
            operation.setMontant(c.getDouble(2));
            compteList.add(operation);
        }
        c.close();
        return compteList;
    }

    public Operation getOneOperation(int id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Operation.TABLE, new String[] {}, Operation.KEY_OperationID+ " = \"" + id + "\"",
                null, null, null, Operation.KEY_OperationID);
        Operation operation = new Operation();
        while (c.moveToNext()) {
            operation.setId(c.getInt(0));
            operation.setLibelle(c.getString(1));
            operation.setMontant(c.getDouble(2));
            operation.setCompteId(c.getInt(3));
        }
        return operation;
    }

    public double getSum(Compte compte){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.rawQuery("select sum(montant) from operation where fkcompteid =" + compte.getId(), new String[] {});

        double montant = 0;
        while (c.moveToNext()) {
            montant = c.getDouble(0);
        }

        return montant;
    }


}
