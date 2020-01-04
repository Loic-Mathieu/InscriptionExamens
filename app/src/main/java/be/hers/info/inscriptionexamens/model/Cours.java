package be.hers.info.inscriptionexamens.model;

public class Cours {
    int id;
    String nom;
    int annee;
    int quadrimestre;

    public Cours(){

    }

    public Cours(Cours c){
        id = c.id;
        nom = c.nom;
        quadrimestre = c.quadrimestre;
    }

    public Cours(int id, String nom, int annee, int quadrimestre) {
        this.id = id;
        this.nom = nom;
        this.annee = annee;
        this.quadrimestre = quadrimestre;
    }

    public Cours(String nom, int annee, int quadrimestre) {
        this.nom = nom;
        this.annee = annee;
        this.quadrimestre = quadrimestre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getQuadrimestre() {
        return quadrimestre;
    }

    public void setQuadrimestre(int quadrimestre) {
        this.quadrimestre = quadrimestre;
    }

    public String toString(){
        return ("Bloc " + annee + ", Q" + quadrimestre + " - " + nom);
    }
}
