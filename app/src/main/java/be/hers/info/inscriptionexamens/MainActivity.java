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
import android.widget.Toast;

import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.model.Cours;
import be.hers.info.inscriptionexamens.model.Utilisateur;

public class MainActivity extends AppCompatActivity
{
    /**
     * Vérifie si la personne qui essaie de se connecter est un utilisateur
     * @param matricule matricule entré par l'utilisateur
     * @param password mot de passe entré par l'utilisateur
     * @return true si l'utilisateur existe dans la base de donnée
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isUser(String matricule, String password)
    {
        if(matricule.isEmpty() || password.isEmpty())
            return false;

        final ExamDB db = new ExamDB(this);
        if(db.getUtilisateur(matricule) == null)
            return false;

        return db.comparerMDP(matricule, password);
    }

    /**
     * Vérifie si la personne qui se connecte est un prof ou non
     * @param matricule matricule de l'utilisateur
     * @return true si l'utilisateur est un prof
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isTeacher(String matricule)
    {
        if(matricule.isEmpty())
            return false;

        final ExamDB db = new ExamDB(this);
        Utilisateur user = db.getUtilisateur(matricule);

        if(user == null)
            return false;

        return user.getEstProf();
    }

    /**
     * Vérifie si il existe des préférences d'années (ANNEES)
     * @return vrai si il existe des préférences, faux sinon
     */
    private boolean checkSharedPreferences()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        return sharedPreferences.contains("ANNEES");
    }

    /**
     * Initialise la DB si besoin
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initDB()
    {
        //On crée la DB
        final ExamDB db = new ExamDB(this);

        // Si la db existe
        if(db.checkDataBase())
            return;

        // Enseignants
        Utilisateur cedric = new Utilisateur("H111111","111111","Cédric","Peeters",true);
        db.addUtilisateur(cedric);
        Utilisateur joram = new Utilisateur("H222222","222222","Joram","Mushymiyimana",true);
        db.addUtilisateur(joram);

        // Eleves
        Utilisateur bob = new Utilisateur("E111111","111111","Bob","Lennon",false);
        db.addUtilisateur(bob);
        Utilisateur luke = new Utilisateur("E222222","222222","Luke","Skywalker",false);
        db.addUtilisateur(luke);

        // Cours
        Cours eco11 = new Cours("Eco", 1, 1);
        db.addCours(eco11);
        Cours eco12 = new Cours("Eco", 1, 2);
        db.addCours(eco12);
        Cours droit22 = new Cours("Droit", 2, 2);
        db.addCours(droit22);

        System.out.println("DB CREE NORMALEMENT");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise la DB si besoin
        initDB();

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
                    if(isUser(matricule.getText().toString(), password.getText().toString()))
                    {
                        Intent intent;
                        if(isTeacher(matricule.getText().toString()))
                            intent = new Intent(getApplicationContext(), Prof_MainPage.class);
                        else
                        {
                            if(checkSharedPreferences())
                                intent = new Intent(getApplicationContext(), Etud_MainPage.class);
                            else
                                intent = new Intent(getApplicationContext(), Etud_ChoixAnnee.class);
                        }

                        // Mettre le matricule dans les préférences
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("MATRICULE", matricule.getText().toString());
                        editor.apply();

                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(v.getContext(), "Identifiants incorrects !", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        );
    }
}
