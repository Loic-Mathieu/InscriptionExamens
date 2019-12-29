package be.hers.info.inscriptionexamens.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import be.hers.info.inscriptionexamens.R;
import be.hers.info.inscriptionexamens.model.Examen;

public class AdapterListView_Examen extends ArrayAdapter<Examen>
{
    public AdapterListView_Examen(Context context, ArrayList<Examen> examens)
    {
        super(context, R.layout.examen, examens);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        // Element
        Examen examen = getItem(position);

        // vue à utiliser
        if(view == null)
        {
            view = LayoutInflater.from(getContext())
                                .inflate(R.layout.examen, parent,false);
        }

        // Nom du cours
        TextView nomCours = view.findViewById(R.id.oNomCours);
        nomCours.setText("" + examen.refCours);

        // Type d'examen
        TextView oQuadri = view.findViewById(R.id.oTypeExam);
        oQuadri.setText(examen.typeExam.label);
        TextView oDescription = view.findViewById(R.id.oDescription);
        oDescription.setText(examen.description);

        // Date
        TextView oDate = view.findViewById(R.id.oDate);
        if(examen.date != null)
            oDate.setText(""+examen.date.toString());
        else
            oDate.setText("Aucune date déterminée");

        // Duree
        TextView oDuree = view.findViewById(R.id.oDuree);
        oDuree.setText("" + examen.dureeMinute);

        return view;
    }
}
