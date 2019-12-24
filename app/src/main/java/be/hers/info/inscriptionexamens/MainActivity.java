package be.hers.info.inscriptionexamens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                        if(isTeacher(matricule.getText().toString()))
                        {
                            Intent intent = new Intent(getApplicationContext(), MainPage_etud.class);
                            // intent.putExtra("name", val);
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
