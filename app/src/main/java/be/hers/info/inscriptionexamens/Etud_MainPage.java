package be.hers.info.inscriptionexamens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Etud_MainPage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etud_mainpage);

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
    }
}
