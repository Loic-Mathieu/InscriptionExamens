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
}
