package fr.moff.gestionbanquaire.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import fr.moff.gestionbanquaire.Ado.ClientADO;
import fr.moff.gestionbanquaire.Ado.CompteADO;
import fr.moff.gestionbanquaire.Ado.OperationADO;
import fr.moff.gestionbanquaire.R;
import fr.moff.gestionbanquaire.classes.Client;
import fr.moff.gestionbanquaire.classes.Compte;
import fr.moff.gestionbanquaire.classes.Operation;
import fr.moff.gestionbanquaire.tools.DBHelper;
import fr.moff.gestionbanquaire.tools.DatabaseManager;

public class LoginActivity extends AppCompatActivity {
    public ArrayList<Client> lesClients;
    public static Client connected;
    private static Context context;
    private static DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        dbHelper = new DBHelper();
        DatabaseManager.initializeInstance(dbHelper);
        ClientADO clientADO = new ClientADO();

        //insertSimpleData();
        //insertSQLiteData();

        lesClients = new ArrayList<>();
        lesClients = clientADO.getAllClients();
        if(lesClients == null){
            insertSQLiteData();
            lesClients = clientADO.getAllClients();
        }

        if(connected != null){
            setContentView(R.layout.activity_logout);
            Button btnLogout = (Button) findViewById(R.id.btn_logout);
            TextView labelProfil = (TextView) findViewById(R.id.label_profil);
            labelProfil.setText(connected.getLogin() + " Voulez-vous vous déconnecter ?");
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    connected = null;
                    Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            });
        }else {
            setContentView(R.layout.activity_login);
            if (this.connected != null) {
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                if (toolbar != null) {
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                }
            }

            final EditText login = (EditText) findViewById(R.id.input_login);
            final EditText password = (EditText) findViewById(R.id.input_password);
            final Button btnConnexion = (Button) findViewById(R.id.btn_ajouter);
            final TextView error = (TextView) findViewById(R.id.error_auth);

            btnConnexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    error.setText("");
                    if (login.getText().toString().equals("") || password.getText().toString().equals("")) {
                        error.setText("Veuillez remplir tous les champs.");
                    } else {
                        ClientADO clientADO = new ClientADO();
                        String username = login.getText().toString();
                        String mdp = password.getText().toString();
                        Client c = new Client();
                        c = clientADO.getOneClient(username);
                        if(c.getId() != 0 && c.getMdp().equals(mdp)){
                            setClient(c);
                            Intent i = new Intent(LoginActivity.this, CompteActivity.class);
                            startActivity(i);
                        }else if(c.getId() == 0 ){
                            c.setLogin(username);
                            c.setMdp(mdp);
                            c.setId(clientADO.insert(c));
                            setClient(c);
                            Intent i = new Intent(LoginActivity.this, CompteActivity.class);
                            startActivity(i);
                        }else{
                            error.setText("Login ou mot de passe incorrect.");
                        }


                    }
                }
            });

        }
    }

    //appel de la toolbar
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_item, menu);
        return true;
    }

    //actions sur éléments toolbar
    public boolean onOptionsItemSelected(MenuItem item){
            int id = item.getItemId();
            Intent intent;
            switch (id){
                case R.id.item_login:
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    return  true;
                case R.id.item_comptes:
                    intent = new Intent(getApplicationContext(), CompteActivity.class);
                    startActivity(intent);
                    return  true;
                case R.id.item_operations:
                    Log.i("item","clic item opération");
                    return  true;
            }
        return super.onOptionsItemSelected(item);
    }

    public void setClient(Client cl) {
        this.connected = cl;
    }
    public static Context getContext(){
        return context;
    }


    public void insertSQLiteData(){
        ClientADO clientADO = new ClientADO();
        CompteADO compteADO = new CompteADO();
        OperationADO operationADO = new OperationADO();

        /*operationADO.delete();
        compteADO.delete();
        clientADO.delete();*/

        Client client = new Client();
        client.setLogin("test");
        client.setMdp("test");
        client.setId(clientADO.insert(client));

        Client client2 = new Client();
        client2.setLogin("admin");
        client2.setMdp("admin");
        client2.setId(clientADO.insert(client2));

        Client client3 = new Client();
        client3.setLogin("test2");
        client3.setMdp("test2");
        client3.setId(clientADO.insert(client3));

        Compte compte = new Compte();
        compte.setMontant(1523.26);
        compte.setNum("022134556166");
        compte.setClientId(client.getId());
        compte.setId(compteADO.insert(compte));
        Compte compte2 = new Compte();
        compte2.setMontant(1523.26);
        compte2.setNum("452134552366");
        compte2.setClientId(client.getId());
        compte2.setId(compteADO.insert(compte2));

        Compte compte3 = new Compte();
        compte3.setMontant(1523.26);
        compte3.setNum("112134556166");
        compte3.setClientId(client2.getId());
        compte3.setId(compteADO.insert(compte3));
        Compte compte4 = new Compte();
        compte4.setMontant(1523.26);
        compte4.setNum("882134556166");
        compte4.setClientId(client2.getId());
        compte4.setId(compteADO.insert(compte4));

        Operation operation = new Operation();
        operation.setLibelle("telephone");
        operation.setMontant(49.99);
        operation.setCompteId(compte.getId());
        operationADO.insert(operation);

        Operation operation2 = new Operation();
        operation2.setLibelle("Internet");
        operation2.setMontant(80.75);
        operation2.setCompteId(compte4.getId());
        operationADO.insert(operation2);

        Operation operation3 = new Operation();
        operation3.setLibelle("Eau");
        operation3.setMontant(84.25);
        operation3.setCompteId(compte4.getId());
        operationADO.insert(operation3);

        Operation operation4 = new Operation();
        operation4.setLibelle("Gaz");
        operation4.setMontant(25.30);
        operation4.setCompteId(compte.getId());
        operationADO.insert(operation4);

        Operation operation5 = new Operation();
        operation5.setLibelle("Piments");
        operation5.setMontant(49.99);
        operation5.setCompteId(compte2.getId());
        operationADO.insert(operation5);

        Operation operation6 = new Operation();
        operation6.setLibelle("Formation au combat");
        operation6.setMontant(149.99);
        operation6.setCompteId(compte2.getId());
        operationADO.insert(operation6);

        Operation operation7 = new Operation();
        operation7.setLibelle("Location voiture de luxe");
        operation7.setMontant(9800.00);
        operation7.setCompteId(compte2.getId());
        operationADO.insert(operation7);

        Operation operation8 = new Operation();
        operation8.setLibelle("Divers");
        operation8.setMontant(49.99);
        operation8.setCompteId(compte3.getId());
        operationADO.insert(operation8);
    }

}
