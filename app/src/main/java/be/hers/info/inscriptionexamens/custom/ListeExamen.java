package be.hers.info.inscriptionexamens.custom;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import be.hers.info.inscriptionexamens.R;
import be.hers.info.inscriptionexamens.model.Examen;

public class ListeExamen extends ArrayAdapter<String>
{
    ArrayList<Examen> examens;

    public ListeExamen(Activity context, final ArrayList<Examen> examens)
    {
        super(context, R.layout.examen);

        this.examens = examens;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        // TODO
        return null;
    }
}
