package be.hers.info.inscriptionexamens.model;

public class Cours {
    int id;
    String nom;
    String quadrimestre;

    public Cours(){

    }

    public Cours(Cours c){
        id = c.id;
        nom = c.nom;
        quadrimestre = c.quadrimestre;
    }

    public Cours(int id, String nom, String quadrimestre) {
        this.id = id;
        this.nom = nom;
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

    public String getQuadrimestre() {
        return quadrimestre;
    }

    public void setQuadrimestre(String quadrimestre) {
        this.quadrimestre = quadrimestre;
    }
}
