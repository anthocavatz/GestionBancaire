package fr.moff.gestionbanquaire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.moff.gestionbanquaire.Ado.CompteADO;
import fr.moff.gestionbanquaire.Ado.OperationADO;
import fr.moff.gestionbanquaire.R;
import fr.moff.gestionbanquaire.classes.Compte;
import fr.moff.gestionbanquaire.classes.Operation;

/**
 * Created by Julian on 11/01/2018.
 */

public class AjoutOperationActivity extends AppCompatActivity{
    private Button btnValiderOperation;
    private Button btnAnnulerOperation;
    private Button btnSupprimerOperation;
    private EditText inputLibelleOperation;
    private EditText inputMontantOperation;
    private TextView labelError;
    private TextView montantCompte;
    Operation operation = new Operation();
    Boolean modification = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_operation);


        btnValiderOperation = (Button) findViewById(R.id.btn_valider_operation);
        btnAnnulerOperation = (Button) findViewById(R.id.btn_annuler_operation);
        btnSupprimerOperation = (Button) findViewById(R.id.btn_supprimer_operation);
        btnSupprimerOperation.setVisibility(View.INVISIBLE);
        inputLibelleOperation = (EditText) findViewById(R.id.input_libelle_operation);
        inputMontantOperation = (EditText) findViewById(R.id.input_montant_operation);
        labelError = (TextView) findViewById(R.id.label_error);

        btnAnnulerOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OperationActivity.class);
                startActivity(intent);
            }
        });

        if(getIntent().getExtras() != null ){
            OperationADO op = new OperationADO();
            String extra = getIntent().getExtras().getSerializable("operation").toString();
            operation = op.getOneOperation(Integer.valueOf(extra));

            if(operation.getId() != 0){
                inputLibelleOperation.setText(operation.getLibelle());
                inputMontantOperation.setText(operation.getMontant().toString());
                btnSupprimerOperation.setVisibility(View.VISIBLE);
                modification = true;

            }

        }

        btnValiderOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputLibelleOperation.getText().toString().isEmpty()|| inputMontantOperation.getText().toString().isEmpty()){
                    labelError.setText("Veuillez remplir tous les champs");
                }else{
                    operation.setLibelle(inputLibelleOperation.getText().toString());
                    operation.setMontant(Double.valueOf(inputMontantOperation.getText().toString()));
                    operation.setCompteId(CompteActivity.selectedCompte.getId());
                    OperationADO op = new OperationADO();


                    if(modification){
                        op.update(operation);
                    }else{
                        op.insert(operation);
                    }


                    CompteADO compte = new CompteADO();
                    Compte c = new Compte();
                    c.setId(CompteActivity.selectedCompte.getId());
                    c.setMontant(CompteActivity.selectedCompte.getMontant() + (operation.getMontant()));
                    c.setLibelle(CompteActivity.selectedCompte.getLibelle());
                    c.setClientId(CompteActivity.selectedCompte.getClientId());
                    c.setNum(CompteActivity.selectedCompte.getNum());

                    compte.update(c);
                    Intent i = new Intent(getApplicationContext(), OperationActivity.class);
                    startActivity(i);
                }

            }
        });

        btnSupprimerOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OperationADO op = new OperationADO();
                double montant = operation.getMontant();
                op.deleteOne(operation.getId());

                CompteADO compte = new CompteADO();
                Compte c = new Compte();
                c.setId(CompteActivity.selectedCompte.getId());
                c.setMontant(CompteActivity.selectedCompte.getMontant() - ( montant));
                c.setLibelle(CompteActivity.selectedCompte.getLibelle());
                c.setClientId(CompteActivity.selectedCompte.getClientId());
                c.setNum(CompteActivity.selectedCompte.getNum());
                compte.update(c);
                Intent i = new Intent(getApplicationContext(), OperationActivity.class);
                startActivity(i);
            }
        });


    }
}