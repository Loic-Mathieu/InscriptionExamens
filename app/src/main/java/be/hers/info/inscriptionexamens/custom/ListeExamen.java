package be.hers.info.inscriptionexamens.custom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import be.hers.info.inscriptionexamens.R;
import be.hers.info.inscriptionexamens.model.Examen;

public class ListeExamen extends ArrayAdapter<String>
{
    private ArrayList<Examen> examens;
    private final Activity context;

    public ListeExamen(Activity context, final ArrayList<Examen> examens)
    {
        super(context, R.layout.examen);

        this.examens = examens;
        this.context = context;
    }

    /*public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.examen, null, true);

        // Nom du cours
        TextView nomCours = rowView.findViewById(R.id.nomCours);
        nomCours.setText((examens.get(position)).getCours());

        // Quadrimestre
        TextView quadri = rowView.findViewById(R.id.quadri);
        quadri.setText((examens.get(position)).getCours().getQuadrimestre());

        return rowView;
    }*/
}
