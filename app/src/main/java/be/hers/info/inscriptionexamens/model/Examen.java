package be.hers.info.inscriptionexamens.model;

import java.time.LocalDateTime;

public class Examen
{
    int id;
    public int refCours;

    public TypeExamen typeExam;
    public String description;
    public LocalDateTime date;
    public int dureeMinute;

    public Examen(int refCours, TypeExamen typeExam, String description, LocalDateTime date, int dureeMinute)
    {
        this.refCours = refCours;
        this.typeExam = typeExam;
        this.description = description;
        this.date = date;
        this.dureeMinute = dureeMinute;
    }

    public Examen(int refCours, TypeExamen typeExam, String description, int dureeMinute)
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
        this.typeExam = TypeExamen.EcritPc;
        this.description = "Description";
        this.dureeMinute = 50;
    }

    public void setId(int i){this.id = i;}

    public int getId()
    {
        return id;
    }

}
