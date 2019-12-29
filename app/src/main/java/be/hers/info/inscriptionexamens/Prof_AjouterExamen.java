package be.hers.info.inscriptionexamens;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;

import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.model.Cours;
import be.hers.info.inscriptionexamens.model.Examen;
import be.hers.info.inscriptionexamens.model.TypeExamen;
import be.hers.info.inscriptionexamens.model.Utilisateur;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Prof_AjouterExamen extends AppCompatActivity
{
    private LocalDate curDate;
    private LocalTime curTime;

    /**
     * initialise le spinner des types d'examens
     */
    private void initSpinnerTypeExam()
    {
        Spinner spinner = findViewById(R.id.iTypeExam);

        // Adapt
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item
        );
        spinner.setAdapter(adapter);

        // ajout des types d'examens
        for(TypeExamen t : TypeExamen.values())
        {
            adapter.add(t.toString());
        }
    }

    /**
     *
     */
    private void initSpinnerCours()
    {
        Spinner spinner = findViewById(R.id.iCours);

        // Adapt
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item
        );
        spinner.setAdapter(adapter);

        final ExamDB db = new ExamDB(this);

        ArrayList<Cours> listCours = db.getAllCours();

        // ajout des cours
        for(Cours c : listCours)
        {
            adapter.add(c.toString());
        }
    }


    /**
     * Créé un objet
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void creerExamen()
    {
        LocalDateTime date = LocalDateTime.of(curDate, curTime);

        EditText iDescription = findViewById(R.id.iDescription);
        String description = iDescription.getText().toString();

        EditText iDuree = findViewById(R.id.iDuree);
        String duree = iDuree.getText().toString();
        if(duree.length() <= 0)
            duree = "0";

        Spinner iType = findViewById(R.id.iTypeExam);
        String type = iType.getSelectedItem().toString();

        Examen exam1 = new Examen(
                1,
                TypeExamen.valueOf(type),
                description,
                date,
                Integer.parseInt(duree));

        // Ajout dans la Db
        final ExamDB db = new ExamDB(this);
        int refExam = db.addExamen(exam1);

        //Récupère le matricule de l'utilisateur connecté
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Activity.MODE_PRIVATE);
        final String matricule = preferences.getString("MATRICULE", "VIDE");

        //Inscrit l'exam au tableau de chasse du prof
        if(refExam>0){
            db.inscrireUtilisateurAExamen(matricule, refExam);
        }

        String output = "Examen Ajouté !"
                + refExam;

        Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_ajoutexamen);

        initSpinnerCours();
        initSpinnerTypeExam();

        // Changement de Date
        CalendarView calendarView = findViewById(R.id.iDate);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                // Le mois en attribut commence à 0 (pour Janvier)
                curDate = LocalDate.of(year, month+1, dayOfMonth);
                System.out.println("DATE" + curDate.toString());
            }
        });

        // Changement d'heure
        TimePicker timePicker = findViewById(R.id.iTime);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                curTime = LocalTime.of(hourOfDay, minute);
            }
        });

        // Press Button
        Button validation = findViewById(R.id.bValider);
        validation.setOnClickListener(
                new View.OnClickListener()
                {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v)
                    {
                        creerExamen();
                    }
                }
        );
    }
}
