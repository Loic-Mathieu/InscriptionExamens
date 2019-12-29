package be.hers.info.inscriptionexamens;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.model.Utilisateur;

public class Prof_ListeEtudiants extends AppCompatActivity
{
    ArrayList<String> listEtudiants = new ArrayList<>();

    /**
     *
     * @return
     */
    public int getId()
    {
        Bundle extra = this.getIntent().getExtras();

        if(extra != null)
            return extra.getInt("ID_EXAM");
        else
            return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_listeeleves);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listEtudiants
        );
        ListView listView = findViewById(R.id.oInscrits);
        listView.setAdapter(adapter);

        // Lister les utilisateurs
        final ExamDB db = new ExamDB(this);
        List<Integer> utilisateursId = db.listerInscrits(getId());
        for (int userId : utilisateursId)
        {
            Utilisateur user = db.getUtilisateurByID(userId);
            listEtudiants.add(user.toString());
        }
    }
}
