package be.hers.info.inscriptionexamens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import be.hers.info.inscriptionexamens.custom.AdapterListView_Examen;
import be.hers.info.inscriptionexamens.model.Examen;

public class Prof_ListeExamens extends AppCompatActivity
{

    private AdapterListView_Examen customList;

    /**
     * Initialise la listView d'examens
     */
    private void initList()
    {
        // TODO from DB
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
        customList.add(new Examen());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_listeexamens);

        // Adapter la liste au model custom
        this.customList = new AdapterListView_Examen(this, new ArrayList<Examen>());
        ListView listView = findViewById(R.id.customListExams);
        listView.setAdapter(customList);

        // init liste
        initList();

        Button bInscription = findViewById(R.id.bSuppression);
        bInscription.setOnClickListener
        (
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        // TODO supprimer les cours
                    }
                }
        );
    }
}
