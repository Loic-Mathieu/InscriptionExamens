package be.hers.info.inscriptionexamens;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainPage_prof extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toast.makeText(MainPage_prof.this, "Page Prof !", Toast.LENGTH_SHORT).show();
    }
}
