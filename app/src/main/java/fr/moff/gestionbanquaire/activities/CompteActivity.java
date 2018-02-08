package fr.moff.gestionbanquaire.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import fr.moff.gestionbanquaire.Ado.ClientADO;
import fr.moff.gestionbanquaire.Ado.CompteADO;
import fr.moff.gestionbanquaire.R;
import fr.moff.gestionbanquaire.classes.Compte;
import fr.moff.gestionbanquaire.tools.ActivityWithToolbar;
import fr.moff.gestionbanquaire.tools.ListViewCompteAdapter;

/**
 * Created by Julian on 10/01/2018.
 */

public class CompteActivity extends ActivityWithToolbar {

    public static Compte selectedCompte;
    public ArrayList<Compte> lesComptes;
    public TextView msgErreur;
    public Button ajouterCompte;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, (int) R.layout.activity_compte);
        final CompteADO compteADO = new CompteADO();
        msgErreur = (TextView) findViewById(R.id.msg_erreur);
        ajouterCompte = (Button) findViewById(R.id.ajouter_compte);
        ajouterCompte.setVisibility(View.INVISIBLE);

        if (LoginActivity.connected == null) {
            Intent i = new Intent(CompteActivity.this, LoginActivity.class);
            startActivity(i);
        } else {
            lesComptes = new ArrayList<>();
            Compte c = new Compte();
            lesComptes = compteADO.getClientComptes(LoginActivity.connected);
            if(lesComptes != null){
                ListView listComptes = (ListView) findViewById(R.id.list_comptes);
                ListViewCompteAdapter adapter = new ListViewCompteAdapter(CompteActivity.this,lesComptes);
                listComptes.setAdapter(adapter);

                listComptes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // Récupère l'élément de la liste
                        selectedCompte = lesComptes.get(i);

                        Intent intent = new Intent(getApplicationContext(), OperationActivity.class);
                        startActivity(intent);
                    }
                });
            }else{
                msgErreur.setText("Aucun compte associé");
                ajouterCompte.setVisibility(View.VISIBLE);
                ajouterCompte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Random randGen = new Random();
                        int max = 99999999 - 1 + 1;
                        int randNum = randGen.nextInt(max);
                        randNum += 1;
                        Compte c = new Compte();
                        c.setNum(String.valueOf(randNum));
                        c.setMontant(0.0);
                        c.setClientId(LoginActivity.connected.getId());
                        c.setId(compteADO.insert(c));

                        Intent intent = new Intent(getApplicationContext(), CompteActivity.class);
                        startActivity(intent);
                    }
                });
            }

        }

    }


}
