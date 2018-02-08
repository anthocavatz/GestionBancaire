package fr.moff.gestionbanquaire.tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import fr.moff.gestionbanquaire.R;
import fr.moff.gestionbanquaire.activities.CompteActivity;
import fr.moff.gestionbanquaire.activities.LoginActivity;
import fr.moff.gestionbanquaire.activities.OperationActivity;

/**
 * Created by Julian on 11/01/2018.
 */

public class ActivityWithToolbar extends AppCompatActivity {
    public Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState, int view) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
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
                intent = new Intent(getApplicationContext(), OperationActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
