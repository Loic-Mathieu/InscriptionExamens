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
        TextView nomCours = view.findViewById(R.id.nomCours);
        nomCours.setText("" + examen.refCours);

        // Type d'examen
        TextView quadri = view.findViewById(R.id.typeExam);
        quadri.setText(examen.typeExam.toString());
        TextView description = view.findViewById(R.id.description);
        quadri.setText(examen.description);

        return view;
    }
}
