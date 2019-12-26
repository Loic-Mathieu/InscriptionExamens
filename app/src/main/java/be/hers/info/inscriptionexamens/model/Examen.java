package be.hers.info.inscriptionexamens.model;

import java.util.Date;

public class Examen
{
    String cours = "PLACE_HOLDER";

    int annee = 0;
    int quadrimestre = 0;

    TypeExam typeExam = TypeExam.ecrit;
    String description = "place_holder";

    Date date;
    int heure;
    int minute;

    public String getCours()
    {
        return cours;
    }

    public int getAnnee()
    {
        return annee;
    }

    public int getQuadrimestre()
    {
        return quadrimestre;
    }

    public TypeExam getTypeExam()
    {
        return typeExam;
    }

    public String getDescription()
    {
        return description;
    }

    public Date getDate()
    {
        return date;
    }

    public int getHeure()
    {
        return heure;
    }

    public int getMinute()
    {
        return minute;
    }
}
