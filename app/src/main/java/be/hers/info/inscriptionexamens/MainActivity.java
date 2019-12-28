package be.hers.info.inscriptionexamens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.model.Cours;
import be.hers.info.inscriptionexamens.model.Examen;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On crée la DB
        final ExamDB db = new ExamDB(this);
        Utilisateur cedric = new Utilisateur("H111111","111111","Cédric","Peeters",true);
        db.addUtilisateur(cedric);
        Utilisateur joram = new Utilisateur("H222222","222222","Joram","Mushymiyimana",true);
        db.addUtilisateur(joram);
        Utilisateur bob = new Utilisateur("E111111","111111","Bob","Lennon",false);
        db.addUtilisateur(bob);
        Utilisateur luke = new Utilisateur("E222222","222222","Luke","Skywalker",false);
        db.addUtilisateur(luke);
        //System.out.println("Matricule : "+cedric.getMatricule() + "/ Nom : " + cedric.getNom());

        //J'ajoute des cours just pr tester
        Cours cSharp11 = new Cours(1 ,"C#", 1,1);
        Cours cSharp12 = new Cours(2 ,"C#", 1,2);
        Cours cSharp21 = new Cours(3 ,"C#", 2,1);
        Cours cSharp22 = new Cours(4 ,"C#", 2,2);
        Cours java11 = new Cours(5 ,"Java", 1,1);
        Cours java12 = new Cours(6 ,"Java", 1,2);



        SimpleDateFormat formateur = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        //J'ajoute des examens just pr tester
        //Date est déprécié, ok, mais l'API m'emm**** avec LocalDate et LocalDateTime car il prend pas le .of et le .ofpattern
        Date date = new Date();
        date.setYear(20);
        date.setMonth(12);
        date.setDate(27);
        date.setHours(12);
        date.setMinutes(30);
        formateur.format(date);
        System.out.println("DATE1 : "+date);
        Examen exam1 = new Examen(1,"écrit papier","blabla", date ,60);
        Date date2 = new Date();
        date2.setYear(2019);
        date2.setMonth(12);
        date2.setDate(28);
        date2.setHours(14);
        date2.setMinutes(25);
        formateur.format(date2);
        System.out.println("DATE2 : "+date2);
        Examen exam2 = new Examen(2,"oral","blabla2", date2 ,20);
        Date date3 = new Date();
        date3.setYear(2019);
        date3.setMonth(12);
        date3.setDate(27);
        date3.setHours(12);
        date3.setMinutes(30);
        formateur.format(date3);
        System.out.println("DATE3 : "+date3);
        Examen exam3 = new Examen(3,"écrit papier","blabla", date ,60);
        Date date4 = new Date();
        date4.setYear(2019);
        date4.setMonth(12);
        date4.setDate(27);
        date4.setHours(12);
        date4.setMinutes(30);
        formateur.format(date4);
        System.out.println("DATE4 : "+date4);
        Examen exam4 = new Examen(4,"écrit papier","blabla", date4 ,60);
        db.addExamen(exam1);
        db.addExamen(exam2);
        db.addExamen(exam3);
        db.addExamen(exam4);

        //PROBLEME ID.................
        System.out.println(
                "IDEXAMS : "
                        +db.getExamen(1).getId()+" "
                        +db.getExamen(1).getCours()
                +"Exam2 : "
                        +db.getExamen(2).getId()+" "
                        +db.getExamen(2).getCours());

        /*Utilisateur x = db.getUtilisateur("E111111");

        x.addToList(Integer.toString(exam1.getId()));
        x.addToList(Integer.toString(exam2.getId()));
        //-----
        //Je try récup
        System.out.println("LISTE EXAM : " +x.getListeExamens());
        System.out.println(db.getExamen(2));*/


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
                            Intent intent = new Intent(getApplicationContext(), MainPage_prof.class);
                            intent.putExtra("database", db );
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(getApplicationContext(), MainPage_etud.class);
                            // intent.putExtra("name", val);
                            startActivity(intent);
                        }
                    }
                }
            }
        );
    }
}
