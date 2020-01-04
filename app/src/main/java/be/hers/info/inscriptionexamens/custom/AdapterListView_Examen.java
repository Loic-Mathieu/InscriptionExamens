package be.hers.info.inscriptionexamens.custom;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import be.hers.info.inscriptionexamens.R;
import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.model.Examen;

public class AdapterListView_Examen extends ArrayAdapter<Examen>
{
    // Checkboxes
    private ArrayList<Integer> selectedIds = new ArrayList<>();

    public AdapterListView_Examen(Context context, ArrayList<Examen> examens)
    {
        super(context, R.layout.examen, examens);
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
                                .inflate(R.layout.examen, parent,false);
        }

        // Nom du cours
        TextView nomCours = view.findViewById(R.id.oNomCours);
        nomCours.setText("" + db.getCours(examen.refCours).toString());


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


        CheckBox checkBox = view.findViewById(R.id.iChoix);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    selectedIds.add(examen.getId());
                else
                    selectedIds.remove(examen.getId());
            }
        });

        return view;
    }

    /**
     * Accès à la liste des id des examens sélectionnés
     * @return Liste d'ID d'examens dans la DB
     */
    public ArrayList<Integer> getSelectedIds()
    {
        return selectedIds;
    }
}
