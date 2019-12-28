package be.hers.info.inscriptionexamens.model;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Examen
{
    int id;
    int refCours;
    String typeExam;
    String description;
    Date date;
    int dureeMinute;

    public Examen(int refCours, String typeExam, String description, Date date, int dureeMinute) {
        this.refCours = refCours;
        this.typeExam = typeExam;
        this.description = description;
        this.date = date;
        this.dureeMinute = dureeMinute;
    }

    public Examen(int refCours, String typeExam, String description, int dureeMinute) {
        this.refCours = refCours;
        this.typeExam = typeExam;
        this.description = description;
        this.date = date;
        this.dureeMinute = dureeMinute;
    }

    public int getId() {
        return id;
    }


    public int getCours()
    {
        return this.refCours;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDureeMinute()
    {
        return dureeMinute;
    }
}
