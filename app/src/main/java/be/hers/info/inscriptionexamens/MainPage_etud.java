package be.hers.info.inscriptionexamens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import be.hers.info.inscriptionexamens.model.Examen;

public class MainPage_etud extends AppCompatActivity
{
    List<Examen> examens;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
