package be.hers.info.inscriptionexamens.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.text.SimpleDateFormat;
import java.util.Date;

import be.hers.info.inscriptionexamens.model.Cours;
import be.hers.info.inscriptionexamens.model.Examen;
import be.hers.info.inscriptionexamens.model.Utilisateur;

public class ExamDB extends SQLiteOpenHelper {

    //Info DB-------------------------------------------------------------------------------------
    private final static String dbName = "ExamDB";
    private final static int dbVersion = 1;
    
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

    private static final String CREATE_TABLE_UTILISATEUR =  "create table " + TABLE_UTILISATEUR + " ("
            +UTILISATEUR_ID + " integer primary key autoincrement, "
            +UTILISATEUR_MATRICULE + " text not null, "
            +UTILISATEUR_MDP + " text not null, "
            +UTILISATEUR_PRENOM + " text not null, "
            +UTILISATEUR_NOM + " text not null, "
            +UTILISATEUR_ESTPROF +" boolean not null);";

    //Table Utilisateur----------------------------------------------------------------------------
    private static final String TABLE_EXAMEN = "utilisateur";
    private static final String EXAMEN_ID = "_id";
    //"_id" obligatoire pour les PK sinon certaines fonctions d'android risquent de ne pas aller
    private static final String EXAMEN_COURS = "cours";
    private static final String EXAMEN_ANNEE = "annee";
    private static final String EXAMEN_TYPE = "type";
    private static final String EXAMEN_DESCRIPTION = "description";
    private static final String EXAMEN_DATE = "date";
    private static final String EXAMEN_DUREE = "duree";
    private static final String EXAMEN_HEURE = "heure";

    private static final String CREATE_TABLE_EXAMEN =  "create table " + TABLE_EXAMEN + " ("
            +EXAMEN_ID + " integer primary key autoincrement, "
            +EXAMEN_COURS + " int not null, "
            +EXAMEN_ANNEE + " int not null, "
            +EXAMEN_TYPE + " text not null, "
            +EXAMEN_DESCRIPTION + " text not null, "
            +EXAMEN_DATE + " date not null, "
            +EXAMEN_DUREE +" int not null, "
            +EXAMEN_HEURE +" date not null);";


    //Création de la DB---------------------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_EXAMEN);
        Utilisateur cedric = new Utilisateur("Cédric","Peeters","H111111","111111",true);
        addUtilisateur(cedric);
        Utilisateur bob = new Utilisateur("Bob","Lennon","E111111","111111",true);
        addUtilisateur(bob);
        Utilisateur joram = new Utilisateur("Joram","Mushymiyimana","H222222","222222",true);
        addUtilisateur(cedric);
        Utilisateur luke = new Utilisateur("Luke","Skywalker","E222222","222222",true);
        addUtilisateur(luke);
    }

    //Upgrade de la DB----------------------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_UTILISATEUR+";");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EXAMEN+";");
        onCreate(db);

    }

    //********************************************************************************************
    //**************************** METHODES UTILISATEUR ******************************************
    //********************************************************************************************
    //Ajouter un utilisateur-----------------------------------------------------------------------
    void addUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            ContentValues values = new ContentValues();
            values.put(UTILISATEUR_MATRICULE, utilisateur.getMatricule());
            values.put(UTILISATEUR_MDP, utilisateur.getMdp());
            values.put(UTILISATEUR_PRENOM, utilisateur.getPrenom());
            values.put(UTILISATEUR_NOM, utilisateur.getNom());
            values.put(UTILISATEUR_ESTPROF, utilisateur.getEstProf());

            db.insert(TABLE_UTILISATEUR, null, values);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }

    }

    //Récupérer un utilisateur--------------------------------------------------------------------
    Utilisateur getUtilisateur(String matricule) {
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
                            UTILISATEUR_ESTPROF
                    },
                    UTILISATEUR_MATRICULE + "=?",
                    new String[] { String.valueOf(matricule) },
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null)
                cursor.moveToFirst();

            Utilisateur utilisateur = new Utilisateur(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4) != 0
            );

            return utilisateur;

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return null;
    }

    //Comparer MDP -------------------------------------------------------------------------------
    Boolean comparerMDP(String matricule, String mdp){
        Utilisateur x = getUtilisateur(matricule);
        if (mdp.equals(x.getMdp())) {
            return true;
        }
        return false;
    }

    //Check si prof ou eleve ---------------------------------------------------------------------
    Boolean verifierEstProf(String matricule){
        Utilisateur x = getUtilisateur(matricule);
        if(x.getEstProf()){
            return true;
        }
        return false;
    }

    //********************************************************************************************
    //**************************** METHODES EXAMEN ******************************************
    //********************************************************************************************
    //Ajouter un utilisateur-----------------------------------------------------------------------
    void addExamen(Examen exam) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm:ss");

            ContentValues values = new ContentValues();
            values.put(EXAMEN_ANNEE, exam.getAnnee());
            values.put(EXAMEN_COURS, exam.getCours());
            values.put(EXAMEN_DATE, dateFormat.format(exam.getDate()));
            values.put(EXAMEN_TYPE, exam.getTypeExam());
            values.put(EXAMEN_DESCRIPTION, exam.getDescription());
            values.put(EXAMEN_DUREE, exam.getDureeMinute());
            values.put(EXAMEN_HEURE, heureFormat.format(exam.getDate()));

            db.insert(TABLE_EXAMEN, null, values);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }
    }

    //Récupérer un examen--------------------------------------------------------------------
    Examen getExamen(String cours) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.query(
                    TABLE_EXAMEN,
                    new String[]{
                            EXAMEN_ID,
                            EXAMEN_ANNEE,
                            EXAMEN_COURS,
                            EXAMEN_TYPE,
                            EXAMEN_DESCRIPTION,
                            EXAMEN_DATE,
                            EXAMEN_DUREE,
                            EXAMEN_HEURE,
                    },
                    EXAMEN_COURS + "=?",
                    new String[]{String.valueOf(cours)},
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null)
                cursor.moveToFirst();

            Examen exam = new Examen(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    new Date(cursor.getLong(4)),
                    cursor.getInt(5),
                    cursor.getInt(6)
            );

            return exam;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }
        return null;
    }
}
