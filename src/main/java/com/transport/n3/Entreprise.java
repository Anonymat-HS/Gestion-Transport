package com.transport.n3;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Entreprise {
    private  String id;
    private String nom;
    private String email;
    private String telephone;
    private List<Voiture> listeVoiture;
    private List<Employee> listeEmployee;

    public Entreprise(String id, String nom, String email, String telephone, List<Voiture> listeVoiture, List<Employee> listeEmployee) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.listeVoiture = new ArrayList<>();
        this.listeEmployee = new ArrayList<>();
    }

    public double calculerBeneficeParMois(int mois, int annee) {

    }
}
