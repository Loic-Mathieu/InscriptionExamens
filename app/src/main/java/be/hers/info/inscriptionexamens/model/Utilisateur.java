package be.hers.info.inscriptionexamens.model;

public class Utilisateur {
    int id;
    String prenom;
    String nom;
    String matricule;
    String mdp;
    boolean estProf;

    public Utilisateur(){
        //cstr vide
    }

    public Utilisateur(String matricule, String mdp, String prenom, String nom, boolean estProf) {
        this.prenom = prenom;
        this.nom = nom;
        this.matricule = matricule;
        this.mdp = mdp;
        this.estProf = estProf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public boolean getEstProf() {
        return estProf;
    }

    public void setEstProf(boolean estProf) {
        this.estProf = estProf;
    }
}
