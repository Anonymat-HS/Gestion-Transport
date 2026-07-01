package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



public abstract class Employee {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private Sexe sexe;
    private double salaire;

    public Employee(String id, String nom, String prenom, String telephone, Sexe sexe, double salaire) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.sexe = sexe;
        this.salaire = salaire;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }
}
