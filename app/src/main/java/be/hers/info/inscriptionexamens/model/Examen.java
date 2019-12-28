package be.hers.info.inscriptionexamens.model;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Examen
{
    public int id;
    public int refCours;
    public String typeExam;
    public String description;
    public Date date;
    public int dureeMinute;

    public Examen(int id, int refCours, String typeExam, String description, Date date, int dureeMinute)
    {
        this.id = id;
        this.refCours = refCours;
        this.typeExam = typeExam;
        this.description = description;
        this.date = date;
        this.dureeMinute = dureeMinute;
    }

    public Examen(int id, int refCours, String typeExam, String description, int dureeMinute)
    {
        this.refCours = refCours;
        this.typeExam = typeExam;
        this.description = description;
        this.dureeMinute = dureeMinute;
    }

    // TODO delete, test constructor
    public Examen()
    {
        this.refCours = 0;
        this.typeExam = "type";
        this.description = "Description";
        this.dureeMinute = 50;
    }
}
