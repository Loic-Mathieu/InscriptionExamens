package be.hers.info.inscriptionexamens;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.model.Cours;
import be.hers.info.inscriptionexamens.model.Examen;
import be.hers.info.inscriptionexamens.model.TypeExamen;

public class Prof_ModifExamen extends AppCompatActivity
{
    private LocalDate curDate;
    private LocalTime curTime;

    private Examen examen = new Examen();
    private int id;

    public void getId()
    {
        Bundle extra = this.getIntent().getExtras();

        if(extra != null)
            this.id = extra.getInt("ID_EXAM");
        else
            this.id = -1;
    }

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

        // Type choisit
        int position = adapter.getPosition(examen.typeExam.toString());
        spinner.setSelection(position);
    }

    /**
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
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

        // Cours choisit
        Cours selected = db.getCours(examen.refCours);
        // int position = adapter.getPosition(selected.toString());
        // spinner.setSelection(position);
    }

    /**
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initChamps()
    {
        EditText iDescription = findViewById(R.id.iDescription);
        iDescription.setText(examen.description);

        EditText iDuree = findViewById(R.id.iDuree);
        iDuree.setText(""+examen.dureeMinute);

        /*
        // Calendar
        ZonedDateTime zonedDateTime = examen.date.atZone(ZoneId.of("France/Paris"));
        long l = zonedDateTime.toInstant().toEpochMilli();
        CalendarView calendarView = findViewById(R.id.iDate);
        calendarView.setDate(l, true, true);*/

        // Time
        TimePicker timePicker = findViewById(R.id.iTime);
        timePicker.setHour(examen.date.getHour());
        timePicker.setMinute(examen.date.getMinute());

        curDate = examen.date.toLocalDate();
        curTime = examen.date.toLocalTime();
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
        // db.addExamen(exam1); TODO PUT

        String output = "Examen Ajouté !"
                + exam1.date.toString() + " "
                + exam1.typeExam;

        Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_ajoutexamen);

        getId();
        if(id > 0)
        {
            // Load exam
            final ExamDB db = new ExamDB(this);
            examen = db.getExamen(id);
        }

        initChamps();

        // Spinners
        initSpinnerTypeExam();
        initSpinnerCours();

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
