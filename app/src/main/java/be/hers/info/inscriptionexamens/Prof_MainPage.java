package be.hers.info.inscriptionexamens;

import android.content.Intent;
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

        // Page d'inscription
        Button changePage_listeExamens = findViewById(R.id.bChange_pListeExamens);
        changePage_listeExamens.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), Prof_ListeExamens.class);
                        // intent.putExtra("name", val);
                        startActivity(intent);
                    }
                }
        );

        // Page de désinscription
        Button changePage_ajouterExamen = findViewById(R.id.bChange_pAjouterExamen);
        changePage_ajouterExamen.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), Prof_AjouterExamen.class);
                        // intent.putExtra("name", val);
                        startActivity(intent);
                    }
                }
        );

        Button foo = findViewById(R.id.buttonTest);
        foo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Prof_ModifExamen.class);
                intent.putExtra("ID_EXAM", 1);
                startActivity(intent);
            }
        });
    }
}
