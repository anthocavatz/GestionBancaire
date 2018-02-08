package fr.moff.gestionbanquaire.classes;

import java.io.Serializable;

/**
 * Created by Julian on 18/01/2018.
 */

public class Operation implements Serializable {
    public static final String TAG = Operation.class.getSimpleName();
    public static final String TABLE = "Operation";
    // Labels Table Columns names
    public static final String KEY_OperationID = "OperationId";
    public static final String KEY_Libelle = "Libelle";
    public static final String KEY_Montant = "Montant";
    public static final String KEY_CompteID = "FkCompteId";

    private int id;
    private String libelle;
    private Double montant;
    private int compteId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompteId() {
        return compteId;
    }

    public void setCompteId(int compteId) {
        this.compteId = compteId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }
}
