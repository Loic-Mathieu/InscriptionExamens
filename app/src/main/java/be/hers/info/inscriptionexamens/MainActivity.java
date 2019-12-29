package be.hers.info.inscriptionexamens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import be.hers.info.inscriptionexamens.database.ExamDB;

public class MainActivity extends AppCompatActivity
{
    private boolean isUser(String matricule, String password)
    {
        // TODO : DB check
        return true;

    }

    private boolean isTeacher(String matricule)
    {
        // TODO : DB check
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On crée la DB
        final ExamDB db = new ExamDB(this);

        // Boutton connexion
        Button button = findViewById(R.id.bConnect);

        // Identifiants
        final EditText matricule = findViewById(R.id.iMatricule);
        final EditText password = findViewById(R.id.iPassword);

        button.setOnClickListener(
            new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    if(db.comparerMDP(matricule.getText().toString(), password.getText().toString()))
                    {
                        Intent intent;
                        if(db.verifierEstProf(matricule.getText().toString()))
                            intent = new Intent(getApplicationContext(), Prof_MainPage.class);
                        else
                            intent = new Intent(getApplicationContext(), Etud_MainPage.class);

                        // Mettre le matricule dans les préférences
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("MATRICULE", matricule.getText().toString());
                        editor.apply();

                        startActivity(intent);
                    }
                }
            }
        );
    }
}
