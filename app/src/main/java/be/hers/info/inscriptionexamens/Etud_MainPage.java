package be.hers.info.inscriptionexamens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import be.hers.info.inscriptionexamens.custom.AdapterListVew_ExamProf;
import be.hers.info.inscriptionexamens.custom.AdapterListView_Examen;
import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.database.FonctionsUtiles;
import be.hers.info.inscriptionexamens.model.Examen;

public class Etud_MainPage extends AppCompatActivity
{

    private AdapterListView_Examen customList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etud_mainpage);
        ExamDB db = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            db = new ExamDB(this);
        }

        // Toast
        Toast.makeText(Etud_MainPage.this, ("Page Etud !"), Toast.LENGTH_SHORT).show();

        // Page d'inscription
        Button changePage_inscription = findViewById(R.id.bChange_pInscription);
        changePage_inscription.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), Etud_InscriptionExamen.class);
                        // intent.putExtra("name", val);
                        startActivity(intent);
                    }
                }
        );

        // Page de désinscription
        Button changePage_desinscription = findViewById(R.id.bChange_pDesinscription);
        changePage_desinscription.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), Etud_DesinscriptionExamen.class);
                        // intent.putExtra("name", val);
                        startActivity(intent);
                    }
                }
        );

        this.customList = new AdapterListView_Examen(this, new ArrayList<Examen>());
        ListView listView = findViewById(R.id.customListExamsModifies);
        customList.addAll(db.getExamModifies());

        listView.setAdapter(customList);

    }
}
