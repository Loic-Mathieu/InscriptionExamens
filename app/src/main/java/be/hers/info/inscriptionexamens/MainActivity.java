package be.hers.info.inscriptionexamens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.model.Examen;
import be.hers.info.inscriptionexamens.model.TypeExamen;
import be.hers.info.inscriptionexamens.model.Utilisateur;

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
                        //System.out.println("Comparaison MDP : "+db.comparerMDP(matricule.getText().toString(), password.getText().toString()));

                        if(db.verifierEstProf(matricule.getText().toString()))
                        {
                            Intent intent = new Intent(getApplicationContext(), Prof_MainPage.class);
                            // intent.putExtra("name", val);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(getApplicationContext(), Etud_MainPage.class);
                            // intent.putExtra("name", val);
                            startActivity(intent);
                        }
                    }
                }
            }
        );
    }
}
