package be.hers.info.inscriptionexamens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class Etud_ChoixAnnee extends AppCompatActivity
{
    private final String reference = "ANNEES";

    /**
     * Détermine les années aux quelles l'étudiant est inscrit
     * @param choix set d'années : 1, 2 ou 3
     */
    private void savePreferences(Set<String> choix)
    {
        SharedPreferences myPreferences = getSharedPreferences ("USER", Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putStringSet(reference, choix).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etud_choixannees);

        Button button = findViewById(R.id.bValider);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                HashSet<String> annes = new HashSet<>();

                // Check le choix
                CheckBox chk1 = findViewById(R.id.iChk1);
                if(chk1.isChecked())
                    annes.add(String.valueOf(1));

                CheckBox chk2 = findViewById(R.id.iChk2);
                if(chk2.isChecked())
                    annes.add(String.valueOf(2));

                CheckBox chk3 = findViewById(R.id.iChk3);
                if(chk3.isChecked())
                    annes.add(String.valueOf(3));

                savePreferences(annes);

                // Switch page
                Intent intent = new Intent(getApplicationContext(), Etud_MainPage.class);
                startActivity(intent);
            }
        });
    }
}
