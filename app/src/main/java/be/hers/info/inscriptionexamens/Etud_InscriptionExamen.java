package be.hers.info.inscriptionexamens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.hers.info.inscriptionexamens.custom.AdapterListView_Examen;
import be.hers.info.inscriptionexamens.custom.ExamComparator;
import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.database.FonctionsUtiles;
import be.hers.info.inscriptionexamens.model.Cours;
import be.hers.info.inscriptionexamens.model.Examen;
import be.hers.info.inscriptionexamens.model.Utilisateur;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Etud_InscriptionExamen extends AppCompatActivity
{
    private AdapterListView_Examen customList;
    private final ExamDB db = new ExamDB(this);

    /**
     * Initialise la listView d'examens
     */
    private void initList()
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Activity.MODE_PRIVATE);
        final String matricule = preferences.getString("MATRICULE", "VIDE");

        final ExamDB db = new ExamDB(this);
        Utilisateur user = db.getUtilisateur(matricule);

        final Set<String> annees = preferences.getStringSet("ANNEES", new HashSet<>(Arrays.asList("1", "2", "3")) );

        List<Examen> examens = db.getExamenNonInscrit(user.getId(), annees);
        ExamComparator comparator = new ExamComparator(db);
        examens.sort(comparator);
        customList.addAll(FonctionsUtiles.getAllExamAnterieurs(examens, "POST"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etud_inscription);

        // Adapter la liste au model custom
        this.customList = new AdapterListView_Examen(this, new ArrayList<Examen>());
        ListView listView = findViewById(R.id.customListExams);
        listView.setAdapter(customList);

        // init liste
        initList();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Activity.MODE_PRIVATE);
        final String matricule = preferences.getString("MATRICULE", "VIDE");

        Button bInscription = findViewById(R.id.bInscription);
        bInscription.setOnClickListener
        (
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        ArrayList<Integer> list = customList.getSelectedIds();
                        for (int id_exam : list)
                        {
                            db.inscrireUtilisateurAExamen(matricule, id_exam);
                        }
                        Intent intent = new Intent(getApplicationContext(), Etud_MainPage.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
