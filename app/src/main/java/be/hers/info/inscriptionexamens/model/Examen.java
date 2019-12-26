package be.hers.info.inscriptionexamens.model;

import java.util.Date;

public class Examen
{
    public String cours = "PLACE_HOLDER";

    public int annee = 0;
    public int quadrimestre = 0;

    public TypeExam typeExam = TypeExam.ecrit;
    public String description = "place_holder";

    public Date date;
    public int heure;
    public int minute;
}
