package be.hers.info.inscriptionexamens;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import be.hers.info.inscriptionexamens.custom.AdapterListVew_ExamProf;
import be.hers.info.inscriptionexamens.custom.ExamComparator;
import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.database.FonctionsUtiles;
import be.hers.info.inscriptionexamens.model.Examen;
import be.hers.info.inscriptionexamens.model.Utilisateur;

public class Prof_ListeExamens extends AppCompatActivity
{
    private AdapterListVew_ExamProf customList;

    /**
     *
     * @return
     */
    private String getExtra()
    {
        Bundle extra = this.getIntent().getExtras();

        if(extra != null)
            return extra.getString("FILTRE");
        else
            return "POST";
    }

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

        List<Examen> examens = db.getExamenByInscription(prof.getId());
        ExamComparator comparator = new ExamComparator(db);
        examens.sort(comparator);
        customList.addAll(FonctionsUtiles.getAllExamAnterieurs(examens, getExtra()));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_listeexamens);

        // Adapter la liste au model custom
        this.customList = new AdapterListVew_ExamProf(this, new ArrayList<Examen>());
        ListView listView = findViewById(R.id.customListExams);
        listView.setAdapter(customList);

        // init liste
        initList();
    }
}
