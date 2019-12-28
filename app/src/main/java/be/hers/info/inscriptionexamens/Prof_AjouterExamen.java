package be.hers.info.inscriptionexamens;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import be.hers.info.inscriptionexamens.model.TypeExamen;

public class Prof_AjouterExamen extends AppCompatActivity
{
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
        // TODO
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_ajoutexamen);

        initSpinnerTypeExam();
    }
}
