package be.hers.info.inscriptionexamens;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import be.hers.info.inscriptionexamens.custom.AdapterListView_Examen;
import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.model.Examen;
import be.hers.info.inscriptionexamens.model.Utilisateur;

public class Prof_ListeExamens extends AppCompatActivity
{

    private AdapterListView_Examen customList;

    /**
     * Initialise la listView d'examens
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initList()
    {
        //Récupère le matricule de l'utilisateur connecté
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Activity.MODE_PRIVATE);
        final String matricule = preferences.getString("MATRICULE", "VIDE");

        final ExamDB db = new ExamDB(this);
        Utilisateur prof = db.getUtilisateur(matricule);

        for (int id_exam : db.getAllRefExamInscritByUser(prof.getId()))
        {
            System.out.println("ID EXAMEN "+id_exam);
            customList.add(db.getExamen(id_exam));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
