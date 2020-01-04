package be.hers.info.inscriptionexamens.database;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
;
import be.hers.info.inscriptionexamens.model.Examen;

public class FonctionsUtiles {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<Examen> getAllExamAnterieurs(List<Examen> listeExams, String s)
    {
        ArrayList<Examen> listeRes = new ArrayList<>();

        try
        {
            //On récupère le présent
            LocalDateTime mtn = LocalDateTime.now();

            //Selon le deuxième argument, on renvoie une liste d'exams du futur, ou du passé.
            if(s.equals("POST")) {
                //Pour chaque élément de la liste d'examens
                for (int i = 0; i < listeExams.size(); i++) {
                    //On check si on est dans le futur (comme Trunk)
                    if (listeExams.get(i).date.compareTo(mtn) > 0) {
                        //Si oui, on ajoute à la liste à retourner
                        listeRes.add(listeExams.get(i));
                    }
                }
            }else if(s.equals("ANT")){
                System.out.println("MTN:"+mtn);
                //Pour chaque élément de la liste d'examens
                for (int i = 0; i < listeExams.size(); i++) {
                    //On check si on est dans le futur (comme Trunk)
                    if (listeExams.get(i).date.compareTo(mtn) < 1) {
                        //Si oui, on ajoute à la liste à retourner
                        listeRes.add(listeExams.get(i));
                    }
                }
            }
            //retourne la liste finale
            return listeRes;
        }
        catch(Exception e){ e.printStackTrace(); }
        finally { }

        return null;
    }

}
