package be.hers.info.inscriptionexamens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Prof_MainPage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_mainpage);

        // Toast
        Toast.makeText(Prof_MainPage.this, "Page Prof !", Toast.LENGTH_SHORT).show();

        // Page liste examens à venir
        Button changePage_listeExamens = findViewById(R.id.bChange_pListeExamens);
        changePage_listeExamens.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), Prof_ListeExamens.class);
                        intent.putExtra("FILTRE", "POST");
                        startActivity(intent);
                    }
                }
        );

        // Page liste examens passés
        Button changePage_listeExamensPassees = findViewById(R.id.bChange_pListeExamensPasees);
        changePage_listeExamensPassees.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), Prof_ListeExamens.class);
                        intent.putExtra("FILTRE", "ANT");
                        startActivity(intent);
                    }
                }
        );

        // Page de création
        Button changePage_ajouterExamen = findViewById(R.id.bChange_pAjouterExamen);
        changePage_ajouterExamen.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), Prof_AjouterExamen.class);
                        startActivity(intent);
                    }
                }
        );

        // Déconnexion
        Button changePage_deconnexionProf = findViewById(R.id.bChange_pDeconnexionProf);
        changePage_deconnexionProf.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        /*SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("MATRICULE");
                        editor.commit();*/

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
