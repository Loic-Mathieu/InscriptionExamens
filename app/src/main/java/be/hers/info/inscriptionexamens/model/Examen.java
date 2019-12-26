package be.hers.info.inscriptionexamens.model;

import java.util.Date;

public class Examen
{
    int id;
    int refCours;

    int annee = 0;
    int quadrimestre = 0;

    String typeExam;
    String description;

    Date date;
    int heure;
    int dureeMinute;

    public Examen(int refCours, int annee, String typeExam, String description, Date date, int dureeMinute, int heure) {
        this.refCours = refCours;
        this.annee = annee;
        this.typeExam = typeExam;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.dureeMinute = dureeMinute;
    }


    public int getCours()
    {
        return this.refCours;
    }

    public int getAnnee()
    {
        return annee;
    }

    public int getQuadrimestre()
    {
        return quadrimestre;
    }

    public String getTypeExam()
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

    public int getDureeMinute()
    {
        return dureeMinute;
    }
}
