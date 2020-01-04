package be.hers.info.inscriptionexamens.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.Build;
import android.renderscript.ScriptIntrinsicYuvToRGB;


import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import be.hers.info.inscriptionexamens.model.Cours;
import be.hers.info.inscriptionexamens.model.Examen;
import be.hers.info.inscriptionexamens.model.TypeExamen;
import be.hers.info.inscriptionexamens.model.Utilisateur;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ExamDB extends SQLiteOpenHelper {

    //Info DB-------------------------------------------------------------------------------------
    private final static String dbName = "ExamDB";
    private final static int dbVersion = 1;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    // private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    //Constructeur--------------------------------------------------------------------------------
    public ExamDB(Context context) {
        super(context,dbName, null, dbVersion);
    }

    //Table Utilisateur----------------------------------------------------------------------------
    private static final String TABLE_UTILISATEUR = "utilisateur";
    private static final String UTILISATEUR_ID = "_id";  
    //"_id" obligatoire pour les PK sinon certaines fonctions d'android risquent de ne pas aller
    private static final String UTILISATEUR_MATRICULE = "matricule";
    private static final String UTILISATEUR_MDP = "mdp";
    private static final String UTILISATEUR_PRENOM = "prenom";
    private static final String UTILISATEUR_NOM = "nom";
    private static final String UTILISATEUR_ESTPROF = "estProf";
    private static final String UTILISATEUR_LISTEEXAMENS = "listeExamens";

    private static final String CREATE_TABLE_UTILISATEUR =  "create table " + TABLE_UTILISATEUR + " ("
            +UTILISATEUR_ID + " integer primary key autoincrement, "
            +UTILISATEUR_MATRICULE + " text not null, "
            +UTILISATEUR_MDP + " text not null, "
            +UTILISATEUR_PRENOM + " text not null, "
            +UTILISATEUR_NOM + " text not null, "
            +UTILISATEUR_ESTPROF +" boolean not null, "
            +UTILISATEUR_LISTEEXAMENS +" text not null);";

    //Table Examen----------------------------------------------------------------------------
    private static final String TABLE_EXAMEN = "examen";
    private static final String EXAMEN_ID = "_id";
    //"_id" obligatoire pour les PK sinon certaines fonctions d'android risquent de ne pas aller
    private static final String EXAMEN_COURS = "cours";
    private static final String EXAMEN_TYPE = "type";
    private static final String EXAMEN_DESCRIPTION = "description";
    private static final String EXAMEN_DATE = "date";
    private static final String EXAMEN_DUREE = "duree";

    private static final String CREATE_TABLE_EXAMEN =  "create table " + TABLE_EXAMEN + " ("
            +EXAMEN_ID + " integer primary key autoincrement, "
            +EXAMEN_COURS + " int not null, "
            +EXAMEN_TYPE + " text not null, "
            +EXAMEN_DESCRIPTION + " text not null, "
            +EXAMEN_DATE + " date not null, "
            +EXAMEN_DUREE +" int not null);";

    // Table Jointure----------------------------------------------------------------------------
    private static final String TABLE_UTIL_EXAM = "utilisateur_examen";
    private static final String UTIL_EXAM_REFEXAMEN = "refexamen";
    private static final String UTIL_EXAM_REFUTILISATEUR = "refutilisateur";

    private static final String CREATE_TABLE_UTILISATEUR_EXAM = "create table " + TABLE_UTIL_EXAM + " ("
            + "_id integer primary key autoincrement, "
            + UTIL_EXAM_REFUTILISATEUR + " integer not null, "
            + UTIL_EXAM_REFEXAMEN + " integer not null, "
            + "CONSTRAINT unqInscr UNIQUE ("+UTIL_EXAM_REFUTILISATEUR+", "+UTIL_EXAM_REFEXAMEN+")"
            + " );";

    //Table Cours----------------------------------------------------------------------------
    private static final String TABLE_COURS = "cours";
    private static final String COURS_ID = "_id";
    //"_id" obligatoire pour les PK sinon certaines fonctions d'android risquent de ne pas aller
    private static final String COURS_NOM = "nom";
    private static final String COURS_QUADRIMESTTRE = "quadrimestre";
    private static final String COURS_ANNEE = "annee";

    private static final String CREATE_TABLE_COURS =  "create table " + TABLE_COURS + " ("
            +COURS_ID + " integer primary key autoincrement, "
            +COURS_NOM + " String not null, "
            +COURS_ANNEE + " int not null, "
            +COURS_QUADRIMESTTRE +" int not null, "
            +"CONSTRAINT unqCours UNIQUE ("+COURS_NOM+", "+COURS_ANNEE+", "+COURS_QUADRIMESTTRE+") );";
    
    
    //Création de la DB---------------------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(CREATE_TABLE_UTILISATEUR);
            db.execSQL(CREATE_TABLE_EXAMEN);
            db.execSQL(CREATE_TABLE_COURS);
            db.execSQL(CREATE_TABLE_UTILISATEUR_EXAM);

        }
        catch(SQLException e) { e.printStackTrace(); }

        System.out.println("Created");
    }

    //Upgrade de la DB----------------------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_UTILISATEUR+";");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EXAMEN+";");
        onCreate(db);
    }

    /**
     * Check if the database exist and can be read.
     * @return true if it exists and can be read, false if it doesn't
     */
    public boolean checkDataBase()
    {
        SQLiteDatabase checkDB = getWritableDatabase();
        int n = -1;
        try
        {
            String count = "SELECT count(*) FROM " + TABLE_UTILISATEUR;
            Cursor cursor = checkDB.rawQuery(count, null);
            cursor.moveToFirst();

            n = cursor.getInt(0);
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            checkDB.close();
        }
        return (n > 0);
    }

    //********************************************************************************************
    //**************************** METHODES UTILISATEUR ******************************************
    //********************************************************************************************
    //Ajouter un utilisateur-----------------------------------------------------------------------
    public void addUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            ContentValues values = new ContentValues();
            StringBuilder str = new StringBuilder();
            ArrayList<String> listeExam = utilisateur.getListeExamens();

            for(int i=0; i<listeExam.size(); i++){
                str.append(listeExam.get(i) + "|");
            }

            String resListe = str.toString();

            values.put(UTILISATEUR_MATRICULE, utilisateur.getMatricule());
            values.put(UTILISATEUR_MDP, utilisateur.getMdp());
            values.put(UTILISATEUR_PRENOM, utilisateur.getPrenom());
            values.put(UTILISATEUR_NOM, utilisateur.getNom());
            values.put(UTILISATEUR_ESTPROF, utilisateur.getEstProf());
            values.put(UTILISATEUR_LISTEEXAMENS, resListe);

            db.insert(TABLE_UTILISATEUR, null, values);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }

    }

    //Récupérer un utilisateur--------------------------------------------------------------------
    public Utilisateur getUtilisateur(String matricule) {
        SQLiteDatabase db = this.getReadableDatabase();

        try{
            Cursor cursor = db.query(
                    TABLE_UTILISATEUR,
                    new String[] {
                            UTILISATEUR_ID,
                            UTILISATEUR_MATRICULE,
                            UTILISATEUR_MDP,
                            UTILISATEUR_PRENOM,
                            UTILISATEUR_NOM,
                            UTILISATEUR_ESTPROF,
                            UTILISATEUR_LISTEEXAMENS
                    },
                    UTILISATEUR_MATRICULE + "=?",
                    new String[] { String.valueOf(matricule) },
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null) {
                cursor.moveToFirst();
            }

            Utilisateur utilisateur = new Utilisateur(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5) != 0
            );

            String str = cursor.getString(6);
            String[] separation = str.split("|");
            ArrayList<String> listeExam = new ArrayList<>();

            for(int i=0; i < separation.length; i++){
                listeExam.add(separation[i]);
            }

            System.out.println("ID USER ICI : "+utilisateur.getId());
            utilisateur.setId(cursor.getInt(0));
            return utilisateur;

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return null;
    }

    //Récupérer un utilisateur--------------------------------------------------------------------
    public Utilisateur getUtilisateurByID(int refUtilisateur) {
        SQLiteDatabase db = this.getReadableDatabase();

        try{
            Cursor cursor = db.query(
                    TABLE_UTILISATEUR,
                    new String[] {
                            UTILISATEUR_ID,
                            UTILISATEUR_MATRICULE,
                            UTILISATEUR_MDP,
                            UTILISATEUR_PRENOM,
                            UTILISATEUR_NOM,
                            UTILISATEUR_ESTPROF,
                            UTILISATEUR_LISTEEXAMENS
                    },
                    UTILISATEUR_ID + "=?",
                    new String[] { String.valueOf(refUtilisateur) },
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null) {
                cursor.moveToFirst();
            }

            Utilisateur utilisateur = new Utilisateur(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5) != 0
            );

            String str = cursor.getString(6);
            String[] separation = str.split("|");
            ArrayList<String> listeExam = new ArrayList<>();

            for(int i=0; i < separation.length; i++){
                listeExam.add(separation[i]);
            }

            System.out.println("ID USER ICI : "+utilisateur.getId());
            utilisateur.setId(cursor.getInt(0));
            return utilisateur;

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return null;
    }

    //Update utilisateur
    public int updateUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            ContentValues values = new ContentValues();
            StringBuilder str = new StringBuilder();
            ArrayList<String> listeExam = utilisateur.getListeExamens();

            for(int i=0; i<listeExam.size(); i++){
                str.append(listeExam.get(i) + "|");
            }

            String resListe = str.toString();

            values.put(UTILISATEUR_MATRICULE, utilisateur.getMatricule());
            values.put(UTILISATEUR_MDP, utilisateur.getMdp());
            values.put(UTILISATEUR_PRENOM, utilisateur.getPrenom());
            values.put(UTILISATEUR_NOM, utilisateur.getNom());
            values.put(UTILISATEUR_ESTPROF, utilisateur.getEstProf());
            values.put(UTILISATEUR_LISTEEXAMENS, resListe);

            return db.update(
                    TABLE_UTILISATEUR, values, UTILISATEUR_ID + " = ?",
                    new String[] {
                            String.valueOf(utilisateur.getId())
                    }
            );
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            db.close();
        }
        return 0;
    }

    //Comparer MDP -------------------------------------------------------------------------------
    public Boolean comparerMDP(String matricule, String mdp){

        //System.out.println("USER : " + matricule + "/" + mdp);
        Utilisateur x = getUtilisateur(matricule);

        //System.out.println("X : " + x.getMatricule() + "/" + x.getMdp());
        if (mdp.equals(x.getMdp())) {
            return true;
        }
        return false;
    }

    //Check si prof ou eleve ---------------------------------------------------------------------
    public Boolean verifierEstProf(String matricule){
        Utilisateur x = getUtilisateur(matricule);
        if(x.getEstProf()){
            return true;
        }
        return false;
    }

    public Boolean inscrireUtilisateurAExamen(String matricule, int id_exam)
    {
        Utilisateur x = getUtilisateur(matricule);

        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            ContentValues values = new ContentValues();
            values.put(UTIL_EXAM_REFUTILISATEUR, x.getId());
            values.put(UTIL_EXAM_REFEXAMEN, id_exam);

            // TODO la contrainte ne lance ni erreur ni exception, alors que ça fait bient un print stack
            db.insert(TABLE_UTIL_EXAM, null, values);

            // Si aucune contraintes
            return true;
        }
        catch(Exception e){ e.printStackTrace(); }
        catch(Error error){ error.printStackTrace(); }
        finally{ db.close(); }

        return false;
    }

    public boolean desinscrireUtilisateurAExamen(String matricule, int id_exam) {

        Utilisateur x = getUtilisateur(matricule);

        SQLiteDatabase db = this.getWritableDatabase();

        try{

            Cursor cursor = db.query(
                    TABLE_UTIL_EXAM,
                    new String[]{
                            UTIL_EXAM_REFUTILISATEUR,
                            UTIL_EXAM_REFEXAMEN
                    },
                    UTIL_EXAM_REFUTILISATEUR + "=? AND " + UTIL_EXAM_REFEXAMEN + "=?",
                    new String[]{String.valueOf(x.getId()), String.valueOf(id_exam)},
                    null,
                    null,
                    null,
                    null
            );

            // Si le curseur existe
            if (cursor != null)
            {
                db.delete(
                        TABLE_UTIL_EXAM, UTIL_EXAM_REFUTILISATEUR + "=? AND " + UTIL_EXAM_REFEXAMEN + "=?",
                        new String[] {
                                String.valueOf(x.getId()), String.valueOf(id_exam),
                        }
                );
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return false;
    }

    public ArrayList<Integer> getAllRefExamInscritByUser(int refUtilisateur){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> listeExamens = new ArrayList<>();

        try
        {
            Cursor cursor = db.query(
                    TABLE_UTIL_EXAM,
                    new String[]{
                            UTIL_EXAM_REFUTILISATEUR,
                            UTIL_EXAM_REFEXAMEN,
                    },
                    UTIL_EXAM_REFUTILISATEUR + "=?",
                    new String[]{String.valueOf(refUtilisateur)},
                    null,
                    null,
                    null,
                    null
            );

            // Si le curseur existe
            if (cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    // Add les références des examens dont un utilisateur est inscrit
                    do {
                        listeExamens.add(cursor.getInt(1));
                    }
                    while (cursor.moveToNext());
                }
            }

            return listeExamens;
        }
        catch(Exception e){ e.printStackTrace(); }
        finally { db.close(); }

        return null;
    }

    //********************************************************************************************
    //**************************** METHODES EXAMEN ******************************************
    //********************************************************************************************
    //Ajouter un examen-----------------------------------------------------------------------
    public int addExamen(Examen exam)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int n = -1;

        try{

            ContentValues values = new ContentValues();
            values.put(EXAMEN_COURS, exam.refCours);
            values.put(EXAMEN_DATE, (exam.date).format(formatter));
            // values.put(EXAMEN_DATE, formatter.format(exam.date));
            values.put(EXAMEN_TYPE, exam.typeExam.toString());
            values.put(EXAMEN_DESCRIPTION, exam.description);
            values.put(EXAMEN_DUREE, exam.dureeMinute);

            db.insert(TABLE_EXAMEN, null, values);


            // Last id
            String lastExam = "SELECT MAX(_id) FROM " + TABLE_EXAMEN;
            Cursor cursor = db.rawQuery(lastExam, null);
            cursor.moveToFirst();

            n = cursor.getInt(0);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }

        // id
        return n;
    }

    //Récupérer un examen--------------------------------------------------------------------
    public Examen getExamenByID(int refExamen) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.query(
                    TABLE_EXAMEN,
                    new String[]{
                            EXAMEN_ID,
                            EXAMEN_COURS,
                            EXAMEN_TYPE,
                            EXAMEN_DESCRIPTION,
                            EXAMEN_DATE,
                            EXAMEN_DUREE,
                    },
                    EXAMEN_ID + "=?",
                    new String[]{String.valueOf(refExamen)},
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null)
                cursor.moveToFirst();

            Examen exam = new Examen(

                    cursor.getInt(1),
                    TypeExamen.valueOf(cursor.getString(2)), // String vers -> enum
                    cursor.getString(3),
                    cursor.getInt(5)
            );

            String str_d = cursor.getString(4);
            LocalDateTime date = LocalDateTime.parse(str_d, formatter);
            //LocalDateTime date = LocalDateTime.parse(str_d);
            exam.date = date;

            System.out.println("COMOESTA : "+cursor.getInt(0));

            exam.setId(cursor.getInt(0));
            return exam;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }
        return null;
    }

    /**
     * Récupères tous les examens de la db
     * @return liste d'examens enregistrés dans la DB
     */
    public List<Examen> getAllExamen()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Examen> listeExamens = new ArrayList<>();

        try
        {
            Cursor cursor = db.query(
                    TABLE_EXAMEN,
                    new String[]{
                            EXAMEN_ID,
                            EXAMEN_COURS,
                            EXAMEN_TYPE,
                            EXAMEN_DESCRIPTION,
                            EXAMEN_DATE,
                            EXAMEN_DUREE,
                    },
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // Si le curseur existe
            if (cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    // Add all examens
                    do {
                        Examen exam = new Examen
                        (
                            cursor.getInt(1),
                            TypeExamen.valueOf(cursor.getString(2)),
                            cursor.getString(3),
                            cursor.getInt(5)
                        );

                        String str_d = cursor.getString(4);
                        LocalDateTime date = LocalDateTime.parse(str_d, formatter);
                        // LocalDateTime date = LocalDateTime.parse(str_d);
                        exam.date = date;

                        exam.setId(cursor.getInt(0));
                        listeExamens.add(exam);
                    }
                    while (cursor.moveToNext());
                }
            }

            return listeExamens;
        }
        catch(Exception e){ e.printStackTrace(); }
        finally { db.close(); }

        return null;
    }



    /**
     * récupère les examens aux quels un utilisateur est inscrit
     * @param refUtilisateur id de l'utilisateur
     * @return liste d'examen lié à l'utilisateur
     */
    public List<Examen> getExamenByInscription(int refUtilisateur)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        LinkedList<Examen> listeExamens = new LinkedList<Examen>();

        try
        {
            // Inner join
            String rawQuery = "SELECT " + EXAMEN_ID + ", " + EXAMEN_COURS + ", " + EXAMEN_TYPE + ", "
                        + EXAMEN_DESCRIPTION + ", " + EXAMEN_DATE + ", " +EXAMEN_DUREE
                    + " FROM " + TABLE_EXAMEN + " INNER JOIN " + TABLE_UTIL_EXAM
                    + " ON " + EXAMEN_ID + " = " + UTIL_EXAM_REFEXAMEN
                    + " WHERE " + UTIL_EXAM_REFUTILISATEUR + " =? ";

            Cursor cursor = db.rawQuery(rawQuery, new String[]{String.valueOf(refUtilisateur)});

            // Si le curseur existe
            if (cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    // Add all examens
                    do {
                        Examen exam = new Examen
                                (
                                        cursor.getInt(1),
                                        TypeExamen.valueOf(cursor.getString(2)),
                                        cursor.getString(3),
                                        cursor.getInt(5)
                                );

                        String str_d = cursor.getString(4);
                        LocalDateTime date = LocalDateTime.parse(str_d, formatter);
                        // LocalDateTime date = LocalDateTime.parse(str_d);
                        exam.date = date;

                        exam.setId(cursor.getInt(0));
                        listeExamens.add(exam);
                    } while (cursor.moveToNext());
                }
            }

            return listeExamens;
        }
        catch (Exception e){e.printStackTrace();}
        finally
        {
            db.close();
        }



        return null;
    }


    public List<Examen> getAllExamenByListeAnnee(List<Integer> listeAnnee)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Examen> listeExamens;
        ArrayList<Examen> listeRes = new ArrayList<>();

        try
        {
            listeExamens = getAllExamen();
            //Pour chaque élément de la liste d'examens
            for(int i = 0; i < listeExamens.size() ; i++){
                //On récupère le cours
                Cours c = getCours(listeExamens.get(i).refCours);
                //Pour chaque élément de la liste d'ID
                for(int j = 0; j < listeAnnee.size(); j++){
                    //On check s'il est égal à l'année du cours
                    if(c.getAnnee() == listeAnnee.get(j)){
                        //Si oui, on ajoute l'examen à la liste finale
                        listeRes.add(getExamenByID(listeAnnee.get(i)));
                    }
                }
            }
            //retourne la liste finale
            return listeRes;
        }
        catch(Exception e){ e.printStackTrace(); }
        finally { db.close(); }

        return null;
    }



    /**
     * Récupères tous les examens de la db créés par le prof
     * @return liste d'examens enregistrés dans la DB
     */
    public List<Examen> getAllExamenProf(String matricule)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        try
        {
            Utilisateur prof = getUtilisateur(matricule);
            ArrayList<Examen> listeExamens = new ArrayList<>();

            Cursor cursor = db.query(
                    TABLE_UTIL_EXAM,
                    new String[]{
                            UTIL_EXAM_REFUTILISATEUR,
                            UTIL_EXAM_REFEXAMEN,
                    },
                    UTIL_EXAM_REFUTILISATEUR + "=?",
                    new String[]{String.valueOf(prof.getId())},
                    null,
                    null,
                    null,
                    null
            );

            // Si le curseur existe
            if (cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    // Add les références des examens dont un utilisateur est inscrit
                    do {
                        listeExamens.add(getExamenByID(cursor.getInt(1)));
                    }
                    while (cursor.moveToNext());
                }
            }

            return listeExamens;
        }
        catch(Exception e){ e.printStackTrace(); }
        finally { db.close(); }

        return null;
    }

    //Update Examen
    public int updateExamen(int id_exam, Examen exam) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            ContentValues values = new ContentValues();

            values.put(EXAMEN_COURS, exam.refCours);
            values.put(EXAMEN_TYPE, exam.typeExam.toString());
            values.put(EXAMEN_DESCRIPTION, exam.description);
            values.put(EXAMEN_DATE, (exam.date).format(formatter));
            values.put(EXAMEN_DUREE, exam.dureeMinute);

            int n = db.update(
                    TABLE_EXAMEN, values, EXAMEN_ID + " = ?",
                    new String[] {
                            String.valueOf(id_exam)
                    }
            );
            return n;

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            db.close();
        }
        return -1;
    }

    /**
     * Delete un examen de la db
     */
    public boolean deleteExam(int refExam) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            ArrayList<Integer> listeEtudiants = new ArrayList<>();

            Cursor cursor = db.query(
                    TABLE_UTIL_EXAM,
                    new String[]{
                            UTIL_EXAM_REFUTILISATEUR
                    },
                    UTIL_EXAM_REFEXAMEN + "=?",
                    new String[]{String.valueOf(refExam)},
                    null,
                    null,
                    null,
                    null
            );

            // Si le curseur existe
            if (cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    // Add les références des étudiants inscrits
                    do {
                        listeEtudiants.add(cursor.getInt(0));
                    }
                    while (cursor.moveToNext());
                }

                for(int i = 0; i < listeEtudiants.size(); i++){
                    Utilisateur etudiant = getUtilisateurByID(listeEtudiants.get(i));
                    ArrayList<String> listeExam = etudiant.getListeExamens();
                    for(String el : listeExam){
                        if(el.equals(refExam)){
                            listeExam.remove(el);
                        }
                    }
                    updateUtilisateur(etudiant);
                }
                db.delete(
                        TABLE_EXAMEN, EXAMEN_ID + " = ?",
                        new String[] {
                                String.valueOf(refExam)
                        }
                );
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return false;
    }

    /**
     * récupère une liste d'élèves inscrits à un examen
     * @return listeEleves
     */
    public ArrayList<Integer> listerInscrits(int refExam) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            //Utilisation de deux listes pour davantage de clarté
            ArrayList<Integer> listeUtilisateurs = new ArrayList<>();
            ArrayList<Integer> listeEtudiants = new ArrayList<>();

            Cursor cursor = db.query(
                    TABLE_UTIL_EXAM,
                    new String[]{
                            UTIL_EXAM_REFUTILISATEUR
                    },
                    UTIL_EXAM_REFEXAMEN + "=?",
                    new String[]{String.valueOf(refExam)},
                    null,
                    null,
                    null,
                    null
            );

            // Si le curseur existe
            if (cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    // Add les références des utilisateurs inscrits à cet examen
                    do {
                        listeUtilisateurs.add(cursor.getInt(0));
                    }
                    while (cursor.moveToNext());
                }

                for(int i = 0; i < listeUtilisateurs.size(); i++){
                    Utilisateur utilisateur = getUtilisateurByID(listeUtilisateurs.get(i));
                    if(!utilisateur.getEstProf()){
                        listeEtudiants.add(utilisateur.getId());
                    }
                }
            }
            return listeEtudiants;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return null;
    }

    //********************************************************************************************
    //**************************** METHODES COURS ******************************************
    //********************************************************************************************
    //Ajouter un cours-----------------------------------------------------------------------
    public void addCours(Cours cours) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            ContentValues values = new ContentValues();

            values.put(COURS_NOM, cours.getNom());
            values.put(COURS_ANNEE, cours.getAnnee());
            values.put(COURS_QUADRIMESTTRE, cours.getQuadrimestre());

            db.insert(TABLE_COURS, null, values);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }

    }

    //Récupérer un cours--------------------------------------------------------------------
    public Cours getCours(int refCours) {
        SQLiteDatabase db = this.getReadableDatabase();

        try{
            Cursor cursor = db.query(
                    TABLE_COURS,
                    new String[] {
                            COURS_ID,
                            COURS_NOM,
                            COURS_ANNEE,
                            COURS_QUADRIMESTTRE
                    },
                    COURS_ID + "=?",
                    new String[] { String.valueOf(refCours) },
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null) {
                cursor.moveToFirst();
            }

            Cours cours = new Cours(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3)
            );

            return cours;

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return null;
    }

    public ArrayList<Cours> getAllCours() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Cours> listeCours = new ArrayList<>();

        try{
            Cursor cursor = db.query(
                    TABLE_COURS,
                    new String[] {
                            COURS_ID,
                            COURS_NOM,
                            COURS_ANNEE,
                            COURS_QUADRIMESTTRE
                    },
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null) {
                cursor.moveToFirst();
            }
            if (cursor.moveToFirst()) {
                do {
                    Cours cours = new Cours(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3)
                    );
                    listeCours.add(cours);
                }
                while (cursor.moveToNext());
            }

            System.out.println("Liste des Cours : "+listeCours);
            return listeCours;

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return null;
    }
}
