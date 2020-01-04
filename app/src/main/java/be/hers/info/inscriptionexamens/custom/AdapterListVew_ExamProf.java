package be.hers.info.inscriptionexamens.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import be.hers.info.inscriptionexamens.Prof_ListeEtudiants;
import be.hers.info.inscriptionexamens.Prof_ModifExamen;
import be.hers.info.inscriptionexamens.R;
import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.model.Examen;

public class AdapterListVew_ExamProf extends ArrayAdapter<Examen>
{
    public AdapterListVew_ExamProf(Context context, ArrayList<Examen> examens)
    {
        super(context, R.layout.examen_prof, examens);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        // Element
        final Examen examen = getItem(position);
        ExamDB db = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            db = new ExamDB(getContext());
        }


        // vue à utiliser
        if(view == null)
        {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.examen_prof, parent,false);
        }

        // Nom du cours
        TextView nomCours = view.findViewById(R.id.oNomCours);
        nomCours.setText("" + db.getCours(examen.refCours).toString() );


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

        // Page de modification
        Button bChange_pModif = view.findViewById(R.id.bModifier);
        bChange_pModif.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), Prof_ModifExamen.class);
                intent.putExtra("ID_EXAM", examen.getId());
                getContext().startActivity(intent);
            }
        });

        // Page lister étudiants
        Button bChange_pListeEtud = view.findViewById(R.id.bListeEtudiants);
        bChange_pListeEtud.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), Prof_ListeEtudiants.class);
                intent.putExtra("ID_EXAM", examen.getId());
                getContext().startActivity(intent);
            }
        });

        return view;
    }
}
