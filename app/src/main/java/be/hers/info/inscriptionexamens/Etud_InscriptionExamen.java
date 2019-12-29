package be.hers.info.inscriptionexamens;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SyncStats;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import be.hers.info.inscriptionexamens.custom.AdapterListView_Examen;
import be.hers.info.inscriptionexamens.database.ExamDB;
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
        Utilisateur prof = db.getUtilisateur(matricule);

        for (int id_exam : db.getAllRefExamInscritByUser(prof.getId()))
        {
            customList.add(db.getExamenByID(id_exam));
        }
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
                            if(db.inscrireUtilisateurAExamen(matricule, id_exam))
                                System.out.println("ONE ADDED");
                        }
                    }
                }
        );
    }
}
