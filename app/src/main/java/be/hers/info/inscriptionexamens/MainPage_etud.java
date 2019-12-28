package be.hers.info.inscriptionexamens;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import be.hers.info.inscriptionexamens.custom.ListeExamen;
import be.hers.info.inscriptionexamens.model.Examen;

public class MainPage_etud extends AppCompatActivity
{
    /*ListView listView;
    ArrayList<Examen> examens;

    private void initList()
    {
        this.examens = new ArrayList<>();

        // TODO : load from DB
        /*examens.add(new Examen());
        examens.add(new Examen());
        examens.add(new Examen());
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toast.makeText(MainPage_etud.this, "Page Etud !", Toast.LENGTH_SHORT).show();
/*
        // Adapter la liste au modele custom
        ListeExamen customList = new ListeExamen(this, examens);
        this.listView = findViewById(R.id.customListExams);
        listView.setAdapter(customList);*/
    }
}
