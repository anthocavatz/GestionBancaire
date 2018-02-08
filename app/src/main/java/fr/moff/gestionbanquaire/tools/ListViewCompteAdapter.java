package fr.moff.gestionbanquaire.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.moff.gestionbanquaire.R;
import fr.moff.gestionbanquaire.classes.Compte;

/**
 * Created by Julian on 06/02/2018.
 */

public class ListViewCompteAdapter extends ArrayAdapter<Compte>{
    public ListViewCompteAdapter(Context context, ArrayList<Compte> comptes){
        super(context,0,comptes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_compte,parent, false);
        }

        CompteViewHolder viewHolder = (CompteViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new CompteViewHolder();
            viewHolder.num = (TextView) convertView.findViewById(R.id.libelle_num_item);
            viewHolder.montant = (TextView) convertView.findViewById(R.id.libelle_montant_item);
            convertView.setTag(viewHolder);
        }

        Compte compte = getItem(position);
        viewHolder.num.setText(compte.getNum()+ " : ");
        viewHolder.montant.setText(String.valueOf(compte.getMontant()));
        return convertView;
    }
    private class CompteViewHolder{
        public TextView num;
        public TextView montant;
    }
}

