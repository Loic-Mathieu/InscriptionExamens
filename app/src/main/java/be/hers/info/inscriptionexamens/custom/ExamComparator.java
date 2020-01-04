package be.hers.info.inscriptionexamens.custom;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Comparator;

import be.hers.info.inscriptionexamens.database.ExamDB;
import be.hers.info.inscriptionexamens.model.Cours;
import be.hers.info.inscriptionexamens.model.Examen;

public class ExamComparator implements Comparator<Examen>
{
    private final ExamDB db;

    public ExamComparator(ExamDB db) {this.db = db;}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compare(Examen e1, Examen e2)
    {
        if(e1.refCours == e2.refCours)
            return 0;

        Cours cours1 = db.getCours(e1.refCours);
        Cours cours2 = db.getCours(e2.refCours);

        return cours1.toString().compareTo(cours2.toString());
    }
}
