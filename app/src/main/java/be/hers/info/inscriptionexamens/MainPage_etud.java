package be.hers.info.inscriptionexamens;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import be.hers.info.inscriptionexamens.custom.ListeExamen;
import be.hers.info.inscriptionexamens.model.Examen;

public class MainPage_etud extends AppCompatActivity
{
    ListView listView;
    ArrayList<Examen> examens;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // TODO : load from DB
        examens.add(new Examen());
        examens.add(new Examen());
        examens.add(new Examen());

        // Adapter la liste au modele custom
        ListeExamen customList = new ListeExamen(this, examens);
        this.listView = findViewById(R.id.listExams);
        listView.setAdapter(customList);
    }
}
