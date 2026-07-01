package com.transport.n3;




public class Bagage {
    private String id;
    private String description;
    private double poids;
    private double prixSupplementaire;

    private static final double poidsInclu = 20.0;
    private static final double tarifParKilo = 500.0;

    public Bagage(String id, String description, double poids, double prixSupplementaire) {
        this.id = id;
        this.description = description;
        this.poids = poids;
        this.prixSupplementaire = calculSurcharge();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getPoids() {
        return poids;
    }

    public double getPrixSupplementaire() {
        return prixSupplementaire;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public void setPrixSupplementaire(double prixSupplementaire) {
        this.prixSupplementaire = prixSupplementaire;
    }

    public double calculSurcharge (){
        if (poids <= poidsInclu){
            return 0.0;
        }
        double excedent = poids -poidsInclu;
        return excedent * tarifParKilo;
    }

}
